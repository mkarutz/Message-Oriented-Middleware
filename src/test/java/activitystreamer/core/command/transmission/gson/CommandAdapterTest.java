package activitystreamer.core.command.transmission.gson;

import activitystreamer.core.command.*;
import com.google.gson.*;
import org.junit.Test;

import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class CommandAdapterTest {
    @Test
    public void testUnexpectedFieldAreIgnored() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .registerTypeAdapter(JsonObject.class, new JsonObjectAdapter())
                .create();


        AuthenticateCommand expected = new AuthenticateCommand("hvsdjhabvjdhvadsas3");

        String msg = "{\"command\":\"AUTHENTICATE\",\"secret\":\"hvsdjhabvjdhvadsas3\",\"unexpected\":9000}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        // System.out.println(((ActivityBroadcastCommand)actual).getActivity().toString());

        assertEquals(expected, actual);
    }

    @Test
    public void testMissingFieldsAreNull() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .registerTypeAdapter(JsonObject.class, new JsonObjectAdapter())
                .create();

        String msg = "{\"command\":\"ACTIVITY_MESSAGE\"}";
        ActivityMessageCommand actual = (ActivityMessageCommand) gson.fromJson(msg, type);

        assertNull(actual.getActivity());
        assertNull(actual.getSecret());
        assertNull(actual.getUsername());
    }

    @Test
    public void testDeserializeActivityBroadcastCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .registerTypeAdapter(JsonObject.class, new JsonObjectAdapter())
                .create();

        String jsonActivity = "{\"foo\":\"bar\"}"; // Probably best to add an actual activity obj here
        JsonObject jsonActivityObj = new JsonParser().parse(jsonActivity).getAsJsonObject();
        ActivityBroadcastCommand expected = new ActivityBroadcastCommand(jsonActivityObj);

        String msg = "{\"command\":\"ACTIVITY_BROADCAST\",\"activity\":{\"foo\":\"bar\"}}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        System.out.println(((ActivityBroadcastCommand) actual).getActivity().toString());

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeActivityMessageCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .registerTypeAdapter(JsonObject.class, new JsonObjectAdapter())
                .create();

        String jsonActivity = "{\"foo\":\"bar\"}"; // Probably best to add an actual activity obj here
        JsonObject jsonActivityObj = new JsonParser().parse(jsonActivity).getAsJsonObject();
        ActivityMessageCommand expected = new ActivityMessageCommand("aaron", "fmnmpp3ai91qb3gc2bvs14g3ue", jsonActivityObj);

        String msg = "{\"command\":\"ACTIVITY_MESSAGE\",\"username\":\"aaron\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\",\"activity\":{\"foo\":\"bar\"}}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeAuthenticateCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        AuthenticateCommand expected = new AuthenticateCommand("fmnmpp3ai91qb3gc2bvs14g3ue");

        String msg = "{\"command\":\"AUTHENTICATE\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeAuthenticationFailCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        AuthenticationFailCommand expected = new AuthenticationFailCommand("the supplied secret is incorrect: fmnmpp3ai91qb3gc2bvs14g3ue");

        String msg = "{\"command\":\"AUTHENTICATION_FAIL\",\"info\":\"the supplied secret is incorrect: fmnmpp3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeInvalidMessageCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        InvalidMessageCommand expected = new InvalidMessageCommand("an error occured");

        String msg = "{\"command\":\"INVALID_MESSAGE\",\"info\":\"an error occured\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeLockAllowedCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LockAllowedCommand expected = new LockAllowedCommand("aaron", "fmnmpp3ai91qb3gc2bvs14g3ue", "p2qj82abyr7ut8losa1erhnqd2");

        String msg = "{\"command\":\"LOCK_ALLOWED\",\"username\":\"aaron\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\",\"server\":\"p2qj82abyr7ut8losa1erhnqd2\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeLockDeniedCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LockDeniedCommand expected = new LockDeniedCommand("aaron", "fmnmpp3ai91qb3gc2bvs14g3ue");

        String msg = "{\"command\":\"LOCK_DENIED\",\"username\":\"aaron\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeLockRequestCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LockRequestCommand expected = new LockRequestCommand("aaron", "fmnmpp3ai91qb3gc2bvs14g3ue","abtdhi3ai91qb3gc2bvs14g3ue");

        String msg = "{\"command\":\"LOCK_REQUEST\",\"username\":\"aaron\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\",\"id\":\"abtdhi3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeLoginCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LoginCommand expected = new LoginCommand("aaron", "fmnmpp3ai91qb3gc2bvs14g3ue");

        String msg = "{\"command\":\"LOGIN\",\"username\":\"aaron\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeLoginFailedCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LoginFailedCommand expected = new LoginFailedCommand("the secret was incorrect", "aaron", "harwood");

        String msg = "{\"command\":\"LOGIN_FAILED\",\"info\":\"the secret was incorrect\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeLoginSuccessCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LoginSuccessCommand expected = new LoginSuccessCommand("logged in as user aaron", "aaron", "harwood");

        String msg = "{\"command\":\"LOGIN_SUCCESS\",\"info\":\"logged in as user aaron\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testLogoutCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        LogoutCommand expected = new LogoutCommand();

        String msg = "{\"command\":\"LOGOUT\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeRedirectCommand() throws UnknownHostException {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        RedirectCommand expected = new RedirectCommand(InetAddress.getByName("123.222.221.1"), 1234);

        String msg = "{\"command\":\"REDIRECT\",\"hostname\":\"123.222.221.1\",\"port\":1234}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeRegisterCommand() {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        RegisterCommand expected = new RegisterCommand("aaron", "fmnmpp3ai91qb3gc2bvs14g3ue");

        String msg = "{\"command\":\"REGISTER\",\"username\":\"aaron\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }

//    @Test
//    public void testDeserializeRegisterFailedCommand() {
//        Class<Command> type = Command.class;
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(type, new CommandAdapter())
//                .create();
//
//        RegisterFailedCommand expected = new RegisterFailedCommand("aaron is already registered with the system");
//
//        String msg = "{\"command\":\"REGISTER_FAILED\",\"info\":\"aaron is already registered with the system\"}";
//        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
//        Command actual = gson.fromJson(elem, type);
//
//        assertEquals(expected, actual);
//    }

//    @Test
//    public void testDeserializeRegisterSuccessCommand() {
//        Class<Command> type = Command.class;
//
//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(type, new CommandAdapter())
//                .create();
//
//        RegisterSuccessCommand expected = new RegisterSuccessCommand("register success for aaron");
//
//        String msg = "{\"command\":\"REGISTER_SUCCESS\",\"info\":\"register success for aaron\"}";
//        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
//        Command actual = gson.fromJson(elem, type);
//
//        assertEquals(expected, actual);
//    }

    @Test
    public void testDeserializeServerAnnounceCommand() throws UnknownHostException {
        Class<Command> type = Command.class;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, new CommandAdapter())
                .create();

        ServerAnnounceCommand expected = new ServerAnnounceCommand("fmnmpp3ai91qb3gc2bvs14g3ue", 5, InetAddress.getByName("128.250.13.46"), 5370);

        String msg = "{\"command\":\"SERVER_ANNOUNCE\",\"id\":\"fmnmpp3ai91qb3gc2bvs14g3ue\",\"load\":5,\"hostname\":\"128.250.13.46\",\"port\":5370}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        Command actual = gson.fromJson(elem, type);

        assertEquals(expected, actual);
    }


    @Test
    public void testAdaptorIsCalled() {
        Class<Command> type = Command.class;
        CommandAdapter adapter = spy(new CommandAdapter());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, adapter)
                .create();

        String msg = "{\"command\":\"AUTHENTICATE\",\"secret\":\"fmnmpp3ai91qb3gc2bvs14g3ue\"}";
        JsonObject elem = new JsonParser().parse(msg).getAsJsonObject();
        gson.fromJson(elem, type);

        verify(adapter).deserialize(any(JsonElement.class), any(Type.class), any(JsonDeserializationContext.class));
    }

    @Test
    public void testSerializeDeserializePreservesEquality() {
        Class<Command> type = Command.class;
        CommandAdapter adapter = new CommandAdapter();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, adapter)
                .create();

        AuthenticateCommand cmd = new AuthenticateCommand("foo bar");

        String json = gson.toJson(cmd, type);
        Command cmd2 = gson.fromJson(json, type);

        assertEquals(cmd, cmd2);
    }
}
