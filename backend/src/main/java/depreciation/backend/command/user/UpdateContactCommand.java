package depreciation.backend.command.user;

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

public class UpdateContactCommand implements ActionCommand {
    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactService = new ContactService();

        Contact contact = JsonUtil.deserialize(request.getBody(), Contact.class);
        contactService.update(contact);

        if (session.getVisitor().getContact().getId() == contact.getId()) {
            session.getVisitor().setContact(contact);
            session.getVisitor().setRole(contact.getRole());
        }

        return new CommandResponse(CommandType.UPDATE_CONTACT.toString(), JsonUtil.serialize(contact), ResponseStatus.OK);
    }
}
