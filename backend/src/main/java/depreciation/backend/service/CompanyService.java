package depreciation.backend.service;

import depreciation.backend.dao.CompanyDAO;
import depreciation.backend.dao.ContactDAO;
import depreciation.backend.dao.EquipmentDAO;
import depreciation.backend.exception.ApplicationException;
import depreciation.dto.CompanyDTO;
import depreciation.entity.Company;
import depreciation.entity.Contact;
import depreciation.entity.Equipment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Date;

public class CompanyService {
    private static final Logger logger = LogManager.getLogger(CompanyService.class);

    private CompanyDAO companyDAO;
    private ContactDAO contactDAO;
    private EquipmentDAO equipmentDAO;
    private EquipmentService equipmentService;


    public CompanyService() {
        this.companyDAO = new CompanyDAO();
        this.contactDAO = new ContactDAO();
        this.equipmentDAO = new EquipmentDAO();
        this.equipmentService = new EquipmentService();
    }

    public CompanyDTO getCompanyDTO(int companyId) throws ApplicationException {
        Company company = companyDAO.getById(companyId);
        CompanyDTO companyDTO = new CompanyDTO(company);
        for (Equipment equipment : company.getEquipmentList()) {
            companyDTO.addEquipment(equipmentService.calculateDepreciationByDate(new Date(), equipment));
        }
        return companyDTO;
    }

    public Company createCompany(Company company, Contact contact) throws ApplicationException {
        Company savedCompany = companyDAO.save(company, contact);

        for (Equipment equipment : company.getEquipmentList()) {
            if (equipment.getId() == 0) {
                equipmentDAO.save(equipment, savedCompany.getId());
            }
        }

        return companyDAO.getById(savedCompany.getId());
    }

    public Company updateCompany(Company company, Contact contact) throws ApplicationException {
        Company updatedCompany = companyDAO.update(company);

        for (Equipment equipment : company.getEquipmentList()) {
            if (equipment.getId() == 0) {
                equipmentDAO.save(equipment, updatedCompany.getId());
            }
        }
        return companyDAO.getById(updatedCompany.getId());
    }

}
