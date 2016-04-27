package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.core.commandprocessor.*;
import activitystreamer.core.shared.Connection;

public class BadActivityMessageCommandHandler implements ICommandHandler {
    @Override
    public boolean handleCommandIncoming(ICommand command,Connection conn) {
        if (command instanceof ActivityMessageCommand) {

            // TODO: Send an authentication fail and close connection
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean handleCommandOutgoing(ICommand command, Connection conn) {
        if (command instanceof ActivityMessageCommand) {
            conn.pushCommandDirect(command);

            return true;
        } else {
            return false;
        }
    }
}