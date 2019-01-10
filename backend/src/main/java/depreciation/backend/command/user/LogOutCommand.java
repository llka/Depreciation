package depreciation.backend.command.user;


import depreciation.backend.command.ActionCommand;
import depreciation.entity.technical.CommandRequest;
import depreciation.entity.technical.CommandResponse;
import depreciation.entity.technical.Session;
import depreciation.entity.technical.Visitor;
import depreciation.enums.ResponseStatus;
import depreciation.enums.RoleEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LogOutCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    @Override
    public CommandResponse execute(CommandRequest request, CommandResponse response, Session session) {

        logger.info("Visitor " + session.getVisitor().getContact().getEmail() + " Logged out.");
        session.setVisitor(new Visitor(RoleEnum.GUEST));
        session.getVisitor().setContact(null);

        return new CommandResponse(ResponseStatus.OK);
    }
}
