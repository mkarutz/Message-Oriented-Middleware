package activitystreamer.server.commandhandlers;

import activitystreamer.core.command.*;
import activitystreamer.core.shared.Connection;
import activitystreamer.server.ServerState;
import activitystreamer.server.services.contracts.IUserAuthService;
import activitystreamer.server.services.impl.ConnectionStateService;
import activitystreamer.server.services.impl.RemoteServerStateService;
import activitystreamer.server.services.impl.UserAuthService;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class LoginCommandHandlerTest {
    @Test
    public void aUsernameMustBePreset() {
        IUserAuthService mockAuthService = mock(UserAuthService.class);
        RemoteServerStateService mockRemoteServerStateService = mock(RemoteServerStateService.class);

        ConnectionStateService mockConnectionStateService = mock(ConnectionStateService.class);

        LoginCommandHandler handler = new LoginCommandHandler(mockAuthService, mockRemoteServerStateService, mockConnectionStateService);

        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getUsername()).thenReturn(null);

        Connection conn = mock(Connection.class);
        handler.handleCommand(cmd, conn);

        verify(conn).pushCommand(isA(InvalidMessageCommand.class));
        verify(conn).close();
    }

    @Test
    public void ifTheUsernameIsAnonymousThenTheUsernameIsIgnored() {
        IUserAuthService mockAuthService = mock(UserAuthService.class);
        RemoteServerStateService mockRemoteServerStateService = mock(RemoteServerStateService.class);

        ConnectionStateService mockConnectionStateService = mock(ConnectionStateService.class);

        LoginCommandHandler handler = new LoginCommandHandler(mockAuthService, mockRemoteServerStateService, mockConnectionStateService);

        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getUsername()).thenReturn("anonymous");

        verify(cmd, never()).getSecret();
    }

    @Test
    public void ifTheUserIsRegisteredALoginSuccessCommandIsSent() {
        IUserAuthService authService = mock(UserAuthService.class);
        when(authService.isUserRegistered(anyString(), anyString())).thenReturn(true);
        when(authService.login(any(Connection.class), anyString(), anyString())).thenReturn(true);

        RemoteServerStateService serverStateService = mock(RemoteServerStateService.class);

        ConnectionStateService mockConnectionStateService = mock(ConnectionStateService.class);

        LoginCommandHandler handler = new LoginCommandHandler(authService, serverStateService, mockConnectionStateService);

        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getUsername()).thenReturn("username");
        when(cmd.getSecret()).thenReturn("password");

        Connection conn = mock(Connection.class);

        handler.handleCommand(cmd, conn);
        verify(conn).pushCommand(isA(LoginSuccessCommand.class));
    }

    @Test
    public void ifThereIsAServerToRedirectToThenARedirectCommandIsSent() {
        IUserAuthService authService = mock(UserAuthService.class);
        when(authService.isUserRegistered(anyString(), anyString())).thenReturn(true);
        when(authService.login(any(Connection.class), anyString(), anyString())).thenReturn(true);

        ServerState mockServer = mock(ServerState.class);

        RemoteServerStateService serverStateService = mock(RemoteServerStateService.class);
        when(serverStateService.getServerToRedirectTo()).thenReturn(mockServer);

        ConnectionStateService mockConnectionStateService = mock(ConnectionStateService.class);

        LoginCommandHandler handler = new LoginCommandHandler(authService, serverStateService, mockConnectionStateService);

        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getUsername()).thenReturn("username");
        when(cmd.getSecret()).thenReturn("password");

        Connection conn = mock(Connection.class);

        handler.handleCommand(cmd, conn);
        verify(conn).pushCommand(isA(LoginSuccessCommand.class));
        verify(conn).pushCommand(isA(RedirectCommand.class));
    }

    @Test
    public void ifTheUserIsNotRegisteredALoginFailedCommandIsSent() {
        IUserAuthService authService = mock(UserAuthService.class);
        when(authService.isUserRegistered(anyString(), anyString())).thenReturn(false);
        when(authService.login(any(Connection.class), anyString(), anyString())).thenReturn(false);

        RemoteServerStateService serverStateService = mock(RemoteServerStateService.class);

        ConnectionStateService mockConnectionStateService = mock(ConnectionStateService.class);

        LoginCommandHandler handler = new LoginCommandHandler(authService, serverStateService, mockConnectionStateService);

        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getUsername()).thenReturn("username");
        when(cmd.getSecret()).thenReturn("password");

        Connection conn = mock(Connection.class);

        handler.handleCommand(cmd, conn);
        verify(conn).pushCommand(isA(LoginFailedCommand.class));
    }
}
