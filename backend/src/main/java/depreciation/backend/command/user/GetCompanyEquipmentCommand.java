package depreciation.backend.command.user;

import depreciation.backend.command.ActionCommand;
import depreciation.backend.dao.CompanyDAO;
import depreciation.backend.dao.EquipmentDAO;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.util.JsonUtil;
import depreciation.dto.EquipmentListDTO;
import depreciation.entity.Company;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetCompanyEquipmentCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(GetCompanyEquipmentCommand.class);
    private static final String COMPANY_ID_PARAM = "companyId";

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        EquipmentDAO equipmentDAO = new EquipmentDAO();
        CompanyDAO companyDAO = new CompanyDAO();

        int companyId = 0;
        try {
            companyId = Integer.parseInt(request.getParameter(COMPANY_ID_PARAM));
        } catch (NumberFormatException e) {
            throw new ApplicationException("Invalid company id!", ResponseStatus.BAD_REQUEST);
        }
        Company company = companyDAO.getById(companyId);
        EquipmentListDTO equipmentListDTO = new EquipmentListDTO(equipmentDAO.getCompanyEquipment(company.getId()));


        return new CommandResponse(JsonUtil.serialize(equipmentListDTO), ResponseStatus.OK);
    }
}
