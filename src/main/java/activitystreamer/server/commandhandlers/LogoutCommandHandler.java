package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.core.shared.Connection;
import activitystreamer.server.services.contracts.ConnectionManager;
import activitystreamer.server.services.contracts.UserAuthService;
import com.google.inject.Inject;

public class LogoutCommandHandler implements ICommandHandler {
    private final UserAuthService userAuthService;
    private final ConnectionManager connectionManager;

    @Inject
    public LogoutCommandHandler(UserAuthService userAuthService,
                                ConnectionManager connectionManager) {
        this.userAuthService = userAuthService;
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean handleCommand(Command command, Connection conn) {
        if (command instanceof LogoutCommand) {
            userAuthService.logout(conn);
            connectionManager.closeConnection(conn);
            return true;
        } else {
            return false;
        }
    }
}
