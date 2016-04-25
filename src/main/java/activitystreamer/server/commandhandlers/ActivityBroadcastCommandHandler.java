package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.core.commandprocessor.*;
import activitystreamer.core.shared.Connection;

public class ActivityBroadcastCommandHandler implements ICommandHandler {
    @Override
    public boolean handleCommand(ICommand command, Connection conn) {
        if (command instanceof ActivityBroadcastCommand) {
            ActivityBroadcastCommand activityBroadcast = (ActivityBroadcastCommand)command;
            conn.getCommandBroadcaster().broadcastToAll(activityBroadcast, conn);

            return true;
        } else {
            return false;
        }
    }
}
