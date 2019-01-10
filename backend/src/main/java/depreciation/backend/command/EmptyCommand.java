package depreciation.backend.command;


import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.enums.ResponseStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class EmptyCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(EmptyCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) {
        logger.debug("Welcome to empty command");

        return new CommandResponse(CommandType.EMPTY.toString(), ResponseStatus.NOT_IMPLEMENTED);
    }
}
