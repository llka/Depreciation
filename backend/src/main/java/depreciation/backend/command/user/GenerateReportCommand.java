package depreciation.backend.command.user;


import depreciation.backend.command.ActionCommand;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.ContactService;
import depreciation.backend.util.ExcelUtil;
import depreciation.entity.Contact;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import depreciation.enums.RoleEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GenerateReportCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(GenerateReportCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactService = new ContactService();
        List<Contact> contacts = new ArrayList<>();
        if (RoleEnum.USER.equals(session.getVisitor().getRole())) {
            contacts.add(session.getVisitor().getContact());
        } else if (RoleEnum.ADMIN.equals(session.getVisitor().getRole())) {
            contacts.addAll(contactService.getAll());
        }
        ExcelUtil.generateExcelProjectsReport(contactService.getAll(), "Report", request.getParameter("path"));
        return new CommandResponse(ResponseStatus.OK);
    }
}
