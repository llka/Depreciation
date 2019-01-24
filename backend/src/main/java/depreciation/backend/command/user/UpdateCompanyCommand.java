package depreciation.backend.command.user;

import depreciation.backend.command.ActionCommand;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.CompanyService;
import depreciation.backend.util.JsonUtil;
import depreciation.dto.CompanyDTO;
import depreciation.dto.EquipmentWithCurrentPriceDTO;
import depreciation.entity.Company;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class UpdateCompanyCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(CreateCompanyCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {

        CompanyService companyService = new CompanyService();

        CompanyDTO companyDTO = JsonUtil.deserialize(request.getBody(), CompanyDTO.class);

        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setTitle(companyDTO.getTitle());
        company.setBusinessScope(companyDTO.getBusinessScope());
        company.setFoundationDate(companyDTO.getFoundationDate());
        for (EquipmentWithCurrentPriceDTO dto : companyDTO.getEquipmentList()) {
            company.addEquipment(dto.getEquipment());
        }

        Company updatedCompany = companyService.updateCompany(company, session.getVisitor().getContact());

        return new CommandResponse(JsonUtil.serialize(updatedCompany), ResponseStatus.OK);
    }
}
