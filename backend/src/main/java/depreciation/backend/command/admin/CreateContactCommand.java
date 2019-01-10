package depreciation.backend.command.admin;


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


public class CreateContactCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(CreateContactCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactService = new ContactService();

        Contact contact = JsonUtil.deserialize(request.getBody(), Contact.class);
        try {
            contactService.getByEmail(contact.getEmail());
            return new CommandResponse(CommandType.CREATE_CONTACT.toString(), "Contact with the same email " + contact.getEmail() + " already exists!", ResponseStatus.PARTIAL_CONTENT);
        } catch (ApplicationException e) {
            contact = contactService.register(contact);
            return new CommandResponse(CommandType.CREATE_CONTACT.toString(), JsonUtil.serialize(contact), ResponseStatus.OK);
        }
    }
}
