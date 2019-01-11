package depreciation.backend.command.user;

import depreciation.backend.command.ActionCommand;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.CompanyService;
import depreciation.backend.util.JsonUtil;
import depreciation.entity.Company;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CreateCompanyCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(CreateCompanyCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {

        CompanyService companyService = new CompanyService();

        Company company = JsonUtil.deserialize(request.getBody(), Company.class);

        Company savedCompany = companyService.createCompany(company, session.getVisitor().getContact());

        return new CommandResponse(JsonUtil.serialize(savedCompany), ResponseStatus.OK);
    }
}
