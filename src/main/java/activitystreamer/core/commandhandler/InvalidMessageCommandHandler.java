package activitystreamer.core.commandhandler;

import activitystreamer.core.command.*;
import activitystreamer.core.shared.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvalidMessageCommandHandler implements ICommandHandler {
    private Logger log = LogManager.getLogger();

    @Override
    public boolean handleCommand(ICommand command, Connection conn) {
        if (command instanceof InvalidMessageCommand) {
            InvalidMessageCommand invalidMessageCommand = (InvalidMessageCommand)command;
            log.error("Invalid message was sent: " + invalidMessageCommand.getInfo());
            conn.close();
            return true;
            /* TODO: */
        } else {
            return false;
        }
    }
}
