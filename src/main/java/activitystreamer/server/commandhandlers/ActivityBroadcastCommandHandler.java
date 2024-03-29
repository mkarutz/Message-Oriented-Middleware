package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.ActivityBroadcastCommand;
import activitystreamer.core.command.Command;
import activitystreamer.core.command.InvalidMessageCommand;
import activitystreamer.core.commandhandler.ICommandHandler;
import activitystreamer.core.shared.Connection;
import activitystreamer.server.services.contracts.BroadcastService;
import activitystreamer.server.services.contracts.ConnectionManager;
import activitystreamer.server.services.contracts.ServerAuthService;
import com.google.inject.Inject;

public class ActivityBroadcastCommandHandler implements ICommandHandler {

  private final ServerAuthService serverAuthService;
  private final BroadcastService broadcastService;
  private final ConnectionManager connectionManager;

  @Inject
  public ActivityBroadcastCommandHandler(ServerAuthService serverAuthService,
                                         BroadcastService broadcastService,
                                         ConnectionManager connectionManager) {
    this.serverAuthService = serverAuthService;
    this.broadcastService = broadcastService;
    this.connectionManager = connectionManager;
  }

  @Override
  public boolean handleCommand(Command command, Connection conn) {
    if (command instanceof ActivityBroadcastCommand) {
      ActivityBroadcastCommand cmd = (ActivityBroadcastCommand) command;

      if (!serverAuthService.isAuthenticated(conn)) {
        conn.pushCommand(new InvalidMessageCommand("Not authorised."));
        connectionManager.closeConnection(conn);
        return true;
      }

      if (!cmd.getActivity().has("authenticated_user")) {
        conn.pushCommand(new InvalidMessageCommand("Invalid activity object."));
        connectionManager.closeConnection(conn);
        return true;
      }

      broadcastService.broadcastToAll(cmd, conn);
      return true;
    } else {
      return false;
    }
  }
}
