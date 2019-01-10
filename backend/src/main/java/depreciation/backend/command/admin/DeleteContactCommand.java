package depreciation.backend.command.admin;


import depreciation.backend.command.ActionCommand;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.ContactService;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class DeleteContactCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(DeleteContactCommand.class);

    private static final String EMAIL_PARAM = "email";
    private static final String ID_PARAM = "id";

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactService = new ContactService();
        contactService.delete(request.getParameter(ID_PARAM), request.getParameter(EMAIL_PARAM));

        return new CommandResponse(ResponseStatus.OK);
    }
}
