package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.core.commandprocessor.*;
import activitystreamer.core.shared.Connection;
import activitystreamer.server.services.*;

public class LogoutCommandHandler implements ICommandHandler {

    @Override
    public boolean handleCommandIncoming(ICommand command, Connection conn) {
        if (command instanceof LogoutCommand) {
            LogoutCommand loginCommand = (LogoutCommand)command;
            conn.close();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean handleCommandOutgoing(ICommand command, Connection conn) {
        return false;
    }
}