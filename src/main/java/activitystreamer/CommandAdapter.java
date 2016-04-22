package activitystreamer;

import activitystreamer.core.command.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CommandAdapter implements JsonSerializer<ICommand>, JsonDeserializer<ICommand> {
    Map<String, Class<? extends ICommand>> commandTypeMap;
    Map<Class<? extends ICommand>, String> typeCommandMap;

    private static final String COMMAND_NAME_FIELD = "COMMAND";

    public CommandAdapter() {
        commandTypeMap = new HashMap<>();
        commandTypeMap.put("AUTHENTICATE", AuthenticateCommand.class);
        commandTypeMap.put("INVALID_MESSAGE", InvalidMessageCommand.class);
        commandTypeMap.put("AUTHENTICATION_FAIL", AuthenticationFailCommand.class);
        commandTypeMap.put("LOGIN", LoginCommand.class);
        commandTypeMap.put("LOGIN_SUCCESS", LoginSuccessCommand.class);
        commandTypeMap.put("REDIRECT", RedirectCommand.class);
        commandTypeMap.put("LOGIN_FAILED", LoginFailedCommand.class);
        commandTypeMap.put("LOGOUT", LogoutCommand.class);
        commandTypeMap.put("ACTIVITY_MESSAGE", ActivityMessageCommand.class);
        commandTypeMap.put("SERVER_ANNOUNCE", ServerAnnounceCommand.class);
        commandTypeMap.put("ACTIVITY_BROADCAST", ActivityBroadcastCommand.class);
        commandTypeMap.put("REGISTER", RegisterCommand.class);
        commandTypeMap.put("REGISTER_FAILED", RegisterFailedCommand.class);
        commandTypeMap.put("ACTIVITY_MESSAGE", ActivityMessageCommand.class);
        commandTypeMap.put("REGISTER_SUCCESS", RegisterSuccessCommand.class);
        commandTypeMap.put("LOCK_REQUEST", LockRequestCommand.class);
        commandTypeMap.put("LOCK_DENIED", LockDeniedCommand.class);
        commandTypeMap.put("LOCK_ALLOWED", LockAllowedCommand.class);

        typeCommandMap = new HashMap<>();
        for (Map.Entry<String, Class<? extends ICommand>> e: commandTypeMap.entrySet()) {
            typeCommandMap.put(e.getValue(), e.getKey());
        }
    }

    @Override
    public ICommand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject =  json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(COMMAND_NAME_FIELD);
        String className = prim.getAsString();

        if (!commandTypeMap.containsKey(className)) {
            throw new JsonParseException("Invalid command type.");
        }

        Class<? extends ICommand> commandType = commandTypeMap.get(className);
        return context.deserialize(jsonObject, commandType);
    }

    @Override
    public JsonElement serialize(ICommand src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, typeOfSrc);
    }
}
