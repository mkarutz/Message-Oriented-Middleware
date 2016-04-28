package activitystreamer.server.commandprocessors;

import activitystreamer.core.command.ICommandBroadcaster;
import activitystreamer.core.commandprocessor.*;
import activitystreamer.core.commandhandler.*;
import activitystreamer.server.commandhandlers.*;
import activitystreamer.server.services.*;

public class MainCommandProcessor extends CommandProcessor {
    public MainCommandProcessor(RemoteServerStateService rServerService,
                                UserAuthService rUserAuthService,
                                ServerAuthService rServerAuthService,
                                ConnectionStateService rConnectionStateService) {
        super();

        handlers.add(new ActivityBroadcastCommandHandler(rConnectionStateService));
        handlers.add(new ActivityMessageCommandHandler(rUserAuthService, rConnectionStateService));
        handlers.add(new AuthenticateCommandHandler(rServerService, rUserAuthService, rServerAuthService, rConnectionStateService));
        handlers.add(new AuthenticationFailCommandHandler());
        handlers.add(new LockAllowedCommandHandler(rUserAuthService, rConnectionStateService));
        handlers.add(new LockDeniedCommandHandler(rUserAuthService, rConnectionStateService));
        handlers.add(new LockRequestCommandHandler(rUserAuthService, rServerAuthService));
        handlers.add(new LoginCommandHandler(rUserAuthService, rServerService, rConnectionStateService));
        handlers.add(new RegisterCommandHandler(rUserAuthService));
        handlers.add(new ServerAnnounceCommandHandler(rServerService, rConnectionStateService));
        handlers.add(new LogoutCommandHandler());
    }
}