package activitystreamer.core.commandprocessor;

import activitystreamer.core.command.ICommand;
import activitystreamer.server.Connection;

public interface ICommandProcessor {
    public void processCommand(Connection connection, ICommand command);
}