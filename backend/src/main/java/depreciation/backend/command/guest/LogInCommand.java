package depreciation.backend.command.guest;


import depreciation.backend.command.ActionCommand;
import depreciation.backend.command.CommandType;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.ContactService;
import depreciation.backend.util.JsonUtil;
import depreciation.entity.Contact;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class LogInCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(LogInCommand.class);

    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactService = new ContactService();

        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);

        Contact contact = contactService.login(email, password);

        session.getVisitor().setContact(contact);
        session.getVisitor().setRole(contact.getRole());
        return new CommandResponse(CommandType.LOGIN.toString(), JsonUtil.serialize(contact), ResponseStatus.OK);

    }
}
