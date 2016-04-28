package activitystreamer.core.command;

public class RegisterFailedCommand implements ICommand {
    private final String command = "REGISTER_FAILED";
    private String info;

    public RegisterFailedCommand(String info) {
        this.info = info;
    }

    @Override
    public String filter() {
        if (info == null) {
            return "Register failed command should contain an info field";
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RegisterFailedCommand &&
            info.equals(((RegisterFailedCommand) obj).getInfo());
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
