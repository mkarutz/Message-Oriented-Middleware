package activitystreamer.core.command;

public class RegisterCommand implements ICommand {
    private final String command = "REGISTER";
    private String username;
    private String secret;

    public RegisterCommand(String username, String secret) {
        this.setUsername(username);
        this.setSecret(secret);
    }

    @Override
    public String filter() {
        if (username == null) {
            return "Register command should contain a username field";
        }
        if (secret == null) {
            return "Register command should contain a secret field";
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RegisterCommand &&
            username.equals(((RegisterCommand) obj).getUsername()) &&
            secret.equals(((RegisterCommand) obj).getSecret());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUsername() {
        return this.username;
    }

    public String getSecret() {
        return this.secret;
    }
}
