package depreciation.backend.command.user;


import depreciation.backend.command.ActionCommand;
import depreciation.backend.command.CommandType;
import depreciation.backend.exception.ApplicationException;
import depreciation.backend.service.ContactService;
import depreciation.backend.util.JsonUtil;
import depreciation.dto.ContactListDTO;
import depreciation.entity.Contact;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

public class GetContactsCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(GetContactsCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException {
        ContactService contactLogic = new ContactService();

        List<Contact> contacts = contactLogic.getAll();
        ContactListDTO contactListDTO = new ContactListDTO(contacts);
        return new CommandResponse(CommandType.GET_CONTACTS.toString(), JsonUtil.serialize(contactListDTO), ResponseStatus.OK);
    }
}