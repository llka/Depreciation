package depreciation.backend.command.user;

import depreciation.backend.command.ActionCommand;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.CompanyService;
import depreciation.backend.util.JsonUtil;
import depreciation.dto.CompanyDTO;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetCompanyCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(GetCompanyCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        CompanyService companyService = new CompanyService();
        CompanyDTO companyDTO = companyService.getCompanyDTO(Integer.parseInt(request.getParameter("companyId")));
        return new CommandResponse(JsonUtil.serialize(companyDTO), ResponseStatus.OK);
    }
}

