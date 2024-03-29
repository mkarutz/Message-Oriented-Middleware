package activitystreamer.server.container;

import activitystreamer.core.command.transmission.CommandDeserializer;
import activitystreamer.core.command.transmission.CommandSerializer;
import activitystreamer.core.command.transmission.gson.GsonCommandSerializationAdaptor;
import activitystreamer.core.commandprocessor.CommandProcessor;
import activitystreamer.core.shared.DisconnectHandler;
import activitystreamer.server.Control;
import activitystreamer.server.IncomingConnectionHandler;
import activitystreamer.server.commandprocessors.MainCommandProcessor;
import activitystreamer.server.services.contracts.*;
import activitystreamer.server.services.impl.ConcreteRemoteServerStateService;
import activitystreamer.server.services.impl.ConcreteUserAuthService;
import activitystreamer.server.services.impl.NetworkManagerService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ServicesModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(BroadcastService.class).to(NetworkManagerService.class);
    bind(ConnectionManager.class).to(NetworkManagerService.class);
    bind(ServerAuthService.class).to(NetworkManagerService.class);
    bind(DisconnectHandler.class).to(NetworkManagerService.class);
    bind(NetworkManagerService.class).in(Singleton.class);

    bind(RemoteServerStateService.class).to(ConcreteRemoteServerStateService.class).in(Singleton.class);

    bind(UserAuthService.class).to(ConcreteUserAuthService.class).in(Singleton.class);
    bind(IncomingConnectionHandler.class).to(Control.class).in(Singleton.class);
    bind(CommandDeserializer.class).to(GsonCommandSerializationAdaptor.class).in(Singleton.class);
    bind(CommandSerializer.class).to(GsonCommandSerializationAdaptor.class).in(Singleton.class);
    bind(GsonCommandSerializationAdaptor.class).in(Singleton.class);
    bind(CommandProcessor.class).to(MainCommandProcessor.class).in(Singleton.class);
  }
}
