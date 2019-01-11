package depreciation.backend.service;

import depreciation.backend.dao.CompanyDAO;
import depreciation.backend.dao.ContactDAO;
import depreciation.backend.dao.EquipmentDAO;
import depreciation.backend.exception.ApplicationException;
import depreciation.entity.Company;
import depreciation.entity.Contact;
import depreciation.entity.Equipment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CompanyService {
    private static final Logger logger = LogManager.getLogger(CompanyService.class);

    private CompanyDAO companyDAO;
    private ContactDAO contactDAO;
    private EquipmentDAO equipmentDAO;


    public CompanyService() {
        this.companyDAO = new CompanyDAO();
        this.contactDAO = new ContactDAO();
        this.equipmentDAO = new EquipmentDAO();
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

}
