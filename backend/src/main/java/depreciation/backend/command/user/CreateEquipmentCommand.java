package depreciation.backend.command.user;

import depreciation.backend.command.ActionCommand;
import depreciation.backend.dao.CompanyDAO;
import depreciation.backend.dao.EquipmentDAO;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.util.JsonUtil;
import depreciation.entity.Equipment;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CreateEquipmentCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(CreateEquipmentCommand.class);
    private static final String COMPANY_ID_PARAM = "companyId";

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {

        EquipmentDAO equipmentDAO = new EquipmentDAO();
        CompanyDAO companyDAO = new CompanyDAO();

        Equipment equipment = JsonUtil.deserialize(request.getBody(), Equipment.class);
        int companyId = 0;
        try {
            companyId = Integer.parseInt(request.getParameter(COMPANY_ID_PARAM));
        } catch (NumberFormatException e) {
            throw new ApplicationException("Invalid company id!", ResponseStatus.BAD_REQUEST);
        }

        companyDAO.getById(companyId);

        equipmentDAO.save(equipment, companyId);

        return new CommandResponse(ResponseStatus.OK);
    }
}
