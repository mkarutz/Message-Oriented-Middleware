package activitystreamer.core.command;

import java.net.InetAddress;

public class RedirectCommand implements ICommand {
    private final String command = "REDIRECT";
    private InetAddress hostname;
    private int port;

    public RedirectCommand(InetAddress hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RedirectCommand &&
            hostname.equals(((RedirectCommand) obj).getHostname()) &&
            port == (((RedirectCommand) obj).getPort());
    }

    public String getCommand() {
        return command;
    }

    public InetAddress getHostname() {
        return hostname;
    }

    public void setHostname(InetAddress hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
