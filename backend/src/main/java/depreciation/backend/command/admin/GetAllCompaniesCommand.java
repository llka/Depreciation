package depreciation.backend.command.admin;

import depreciation.backend.command.ActionCommand;
import depreciation.backend.dao.CompanyDAO;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.util.JsonUtil;
import depreciation.dto.CompanyListDTO;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetAllCompaniesCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(GetAllCompaniesCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        CompanyDAO companyDAO = new CompanyDAO();

        CompanyListDTO companyListDTO = new CompanyListDTO(companyDAO.getAll());
        return new CommandResponse(JsonUtil.serialize(companyListDTO), ResponseStatus.OK);
    }
}
