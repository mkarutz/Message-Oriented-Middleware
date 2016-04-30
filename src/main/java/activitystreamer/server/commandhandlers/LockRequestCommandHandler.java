package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.core.shared.Connection;
import activitystreamer.server.services.contracts.ConnectionManager;
import activitystreamer.server.services.contracts.ServerAuthService;
import activitystreamer.server.services.contracts.UserAuthService;
import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LockRequestCommandHandler implements ICommandHandler {
    private Logger log = LogManager.getLogger();

    private final UserAuthService userAuthService;
    private final ServerAuthService serverAuthService;
    private final ConnectionManager connectionManager;

    @Inject
    public LockRequestCommandHandler(UserAuthService userAuthService,
                                     ServerAuthService serverAuthService,
                                     ConnectionManager connectionManager) {
        this.userAuthService = userAuthService;
        this.serverAuthService = serverAuthService;
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean handleCommand(Command command, Connection conn) {
        if (command instanceof LockRequestCommand) {
            LockRequestCommand cmd = (LockRequestCommand) command;

            if (cmd.getUsername() == null) {
                conn.pushCommand(new InvalidMessageCommand("Username must be present."));
                connectionManager.closeConnection(conn);
                return true;
            }

            if (cmd.getSecret() == null) {
                conn.pushCommand(new InvalidMessageCommand("Secret must be present."));
                connectionManager.closeConnection(conn);
                return true;
            }

            if (!serverAuthService.isAuthenticated(conn)) {
                conn.pushCommand(new InvalidMessageCommand("Not authenticated."));
                connectionManager.closeConnection(conn);
                return true;
            }

            userAuthService.lockRequest(cmd.getUsername(), cmd.getSecret());
            return true;
        } else {
            return false;
        }
    }
}
