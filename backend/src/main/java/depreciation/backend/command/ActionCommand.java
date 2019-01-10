package depreciation.backend.command;


import depreciation.backend.exception.ApplicationException;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;

public interface ActionCommand {
    CommandResponse execute(CommandRequest request, CommandResponse response, Session session) throws ApplicationException;
}