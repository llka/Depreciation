package depreciation.backend.command.user;


import depreciation.backend.command.ActionCommand;
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

public class GetContactCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(GetContactCommand.class);

    private static final String EMAIL_PARAM = "email";
    private static final String ID_PARAM = "id";

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactLogic = new ContactService();
        String email = request.getParameter(EMAIL_PARAM);
        int id = -1;
        if (request.getParameter(ID_PARAM) != null) {
            id = Integer.parseInt(request.getParameter(ID_PARAM));
        }
        Contact contact = null;
        if (email != null && !email.isEmpty()) {
            try {
                contact = contactLogic.getByEmail(email);
            } catch (ApplicationException e) {
                if (id > 0) {
                    contact = contactLogic.getById(id);
                }
            }
        } else if (id > 0) {
            try {
                contact = contactLogic.getById(id);
            } catch (ApplicationException e) {
                contact = contactLogic.getByEmail(email);
            }
        }
        if (contact != null) {
            //logger.debug(contact);
            return new CommandResponse(JsonUtil.serialize(contact), ResponseStatus.OK);
        } else {
            return new CommandResponse("Contact not found", ResponseStatus.NOT_FOUND);
        }
    }
}