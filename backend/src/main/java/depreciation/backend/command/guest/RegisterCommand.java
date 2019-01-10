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
import depreciation.enums.RoleEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class RegisterCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactService = new ContactService();

        Contact contact = JsonUtil.deserialize(request.getBody(), Contact.class);
        if (contact.getFirstName() == null || contact.getFirstName().isEmpty()) {
            throw new ApplicationException("Invalid First name!", ResponseStatus.BAD_REQUEST);
        }
        if (contact.getLastName() == null || contact.getLastName().isEmpty()) {
            throw new ApplicationException("Invalid Last name!", ResponseStatus.BAD_REQUEST);
        }
        if (contact.getEmail() == null || contact.getEmail().isEmpty()) {
            throw new ApplicationException("Invalid Email!", ResponseStatus.BAD_REQUEST);
        }
        if (contact.getPassword() == null || contact.getPassword().isEmpty()) {
            throw new ApplicationException("Invalid Password!", ResponseStatus.BAD_REQUEST);
        }

        contact.setRole(RoleEnum.USER);
        contact = contactService.register(contact);

        session.getVisitor().setContact(contact);
        session.getVisitor().setRole(contact.getRole());
        return new CommandResponse(CommandType.REGISTER.toString(), JsonUtil.serialize(contact), ResponseStatus.OK);

    }
}