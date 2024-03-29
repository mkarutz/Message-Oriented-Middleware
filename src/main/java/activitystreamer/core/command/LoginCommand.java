package activitystreamer.core.command;

import activitystreamer.core.command.transmission.gson.JsonRequired;

public class LoginCommand implements Command {
  private final String command = "LOGIN";
  @JsonRequired
  private String username;
  private String secret;

  public LoginCommand(String username, String secret) {
    this.setUsername(username);
    this.setSecret(secret);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof LoginCommand &&
        username.equals(((LoginCommand) obj).getUsername()) &&
        secret.equals(((LoginCommand) obj).getSecret());
  }

  public String getCommand() {
    return command;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }
}
