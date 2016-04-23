package activitystreamer.server;

import java.io.*;
import java.net.Socket;

import activitystreamer.JsonObjectAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import activitystreamer.util.Settings;
import activitystreamer.CommandAdapter;

import activitystreamer.core.command.*;
import activitystreamer.core.commandprocessor.*;

import com.google.gson.*;

public class Connection implements Closeable, Runnable {
    private static final Logger log = LogManager.getLogger();
    private BufferedReader in;
    private PrintWriter out;
    private boolean open = false;
    private Socket socket;
    private boolean term = false;

    private CommandProcessor processor;
    private Gson gson;

    Connection(Socket socket, CommandProcessor processor) throws IOException {
        this.socket = socket;
        this.processor = processor;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(ICommand.class, new CommandAdapter())
                .registerTypeAdapter(JsonObject.class, new JsonObjectAdapter())
                .create();

        in = new BufferedReader(new InputStreamReader(new DataInputStream(socket.getInputStream())));
        out = new PrintWriter(new DataOutputStream(socket.getOutputStream()), true);
        open = true;
    }

    @Override
    public void run() {
        while (!term) {
            try {
                ICommand cmd = pullCommand();
                processor.processCommand(this, cmd);
            } catch (IOException e) {
                log.error("I/O exception. Closing connection");
                term = true;
            } catch (JsonParseException e) {
                log.error("Invalid message. Closing connection.");
                term = true;
            }
        }
    }

    public void pushCommand(ICommand cmd) {
        String json = this.gson.toJson(cmd, ICommand.class);
        System.out.println("SENDING JSON: " + json);
        this.writeLine(json);
    }

    private ICommand pullCommand() throws IOException, JsonParseException {
        String line = this.readLine();
        System.out.println("RECEIVING JSON: " + line);
        if (line == null) {
            term = true;
            return null;
        }
        return gson.fromJson(line, ICommand.class);
    }

    private String readLine() throws IOException {
        if (!open) {
            throw new IOException("Connection is closed.");
        }
        return in.readLine();
    }

    private boolean writeLine(String msg) {
        if (open) {
            out.println(msg);
            out.flush();
            return true;
        }
        return false;
    }

    @Override
    public synchronized void close() {
        if (open) {
            log.info("closing connection " + Settings.socketAddress(socket));
            try {
                open = false;
                term = true;
                in.close();
                out.close();
            } catch (IOException e) {
                log.error("received exception closing the connection " + Settings.socketAddress(socket) + ": " + e);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public synchronized boolean isOpen() {
        return open;
    }
}
