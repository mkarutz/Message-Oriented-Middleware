package activitystreamer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import activitystreamer.server.Control;
import activitystreamer.util.Settings;

public class Server {
    private static final Logger log = LogManager.getLogger();

    private static void help(Options options) {
        String header = "An ActivityStream Server for Unimelb COMP90015\n\n";
        String footer = "\ncontact aharwood@unimelb.edu.au for issues.";
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ActivityStreamer.Server", header, options, footer, true);
        System.exit(-1);
    }

    public static void main(String[] args) {
        log.info("reading command line options");

        Options options = new Options();
        options.addOption("lp", true, "local port number");
        options.addOption("rp", true, "remote port number");
        options.addOption("rh", true, "remote hostname");
        options.addOption("lh", true, "local hostname");
        options.addOption("a", true, "activity interval in milliseconds");
        options.addOption("s", true, "secret for the server to use");
        options.addOption("d", false, "debug mode, use static secret");

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e1) {
            help(options);
        }

        if (cmd.hasOption("d")) {
          log.info("DEBUG MODE ACTIVE: Using static secret");
          Settings.setSecret("abc123");
        }

        if (cmd.hasOption("lp")) {
            try {
                int port = Integer.parseInt(cmd.getOptionValue("lp"));
                Settings.setLocalPort(port);
                log.info("Setting local port to: " + port);
            } catch (NumberFormatException e) {
                log.info("-lp requires a port number, parsed: " + cmd.getOptionValue("lp"));
                help(options);
            }
        }

        if (cmd.hasOption("rh")) {
            Settings.setRemoteHostname(cmd.getOptionValue("rh"));
        } else {
            if (!cmd.hasOption("d")) {
                Settings.setSecret(Settings.nextSecret());
                log.info("Initial server, generating secret: " + Settings.getSecret());
            }
        }

        if (cmd.hasOption("rp")) {
            try {
                int port = Integer.parseInt(cmd.getOptionValue("rp"));
                Settings.setRemotePort(port);
            } catch (NumberFormatException e) {
                log.error("-rp requires a port number, parsed: " + cmd.getOptionValue("rp"));
                help(options);
            }
        }

        if (cmd.hasOption("a")) {
            try {
                int a = Integer.parseInt(cmd.getOptionValue("a"));
                Settings.setActivityInterval(a);
            } catch (NumberFormatException e) {
                log.error("-a requires a number in milliseconds, parsed: " + cmd.getOptionValue("a"));
                help(options);
            }
        }

        try {
            Settings.setLocalHostname(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            log.warn("failed to get localhost IP address");
        }

        if (cmd.hasOption("lh")) {
            Settings.setLocalHostname(cmd.getOptionValue("lh"));
        }

        if (cmd.hasOption("s")) {
            if (cmd.hasOption("rh")) {
                Settings.setSecret(cmd.getOptionValue("s"));
                log.info("Initializing server with secret " + Settings.getSecret());
            } else {
                log.warn("No remote host specified, makes no sense to set secret.");
            }
        }

        Settings.setId(Settings.nextSecret());
        log.info("starting server");

        Control c = new Control();
        new Thread(c).start();
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutDownHook(c)));
    }

    private static class ShutDownHook implements Runnable {
        private Control c;

        public ShutDownHook(Control c) {
            this.c = c;
        }

        @Override
        public void run() {
            c.setTerm(true);
        }
    }
}
