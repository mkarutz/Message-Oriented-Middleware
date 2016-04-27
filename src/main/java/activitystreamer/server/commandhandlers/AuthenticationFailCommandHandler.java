package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.core.commandprocessor.*;
import activitystreamer.core.shared.Connection;
import activitystreamer.util.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthenticationFailCommandHandler implements ICommandHandler {
    private Logger log = LogManager.getLogger();

    @Override
    public boolean handleCommandIncoming(ICommand command, Connection conn) {
        if (command instanceof AuthenticationFailCommand) {
            AuthenticationFailCommand failCommand = (AuthenticationFailCommand)command;
            log.error("Authentication failed: " + failCommand.getInfo());
            conn.close();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean handleCommandOutgoing(ICommand command, Connection conn) {
        if (command instanceof AuthenticationFailCommand) {
            conn.pushCommandDirect(command);

            return true;
        } else {
            return false;
        }
    }
}