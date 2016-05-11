package activitystreamer.server;

import java.io.IOException;
// import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ssl.*;
import java.security.*;
import java.io.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SecureListener implements Runnable {
    private Logger log = LogManager.getLogger();
    private SSLServerSocket sslServerSocket = null;
    private boolean term = false;
    private int port;

	private IncomingConnectionHandler connectionHandler;

    public SecureListener(IncomingConnectionHandler connectionHandler, int port)
			throws IOException {
        this.port = port;
        try {
            // TODO: this is ugly too
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore privKeystore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream keystoreStream = getClass().getResourceAsStream("/myKey");
            privKeystore.load(keystoreStream, "foobar".toCharArray());

            keyManagerFactory.init(privKeystore, "foobar".toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            SSLContext ctx = SSLContext.getInstance("TLS"); // was SSL
            ctx.init(keyManagers, null, null);

            SSLServerSocketFactory sslserversocketfactory = ctx.getServerSocketFactory();

            // SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            sslServerSocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(port);
        } catch (Exception e) {
            log.error(e.getMessage());
            System.exit(-1);
        }

        this.connectionHandler = connectionHandler;
    }

    @Override
    public void run() {
        log.info("listening for new connections on " + port);
        while (!term) {
            Socket clientSocket;
            try {
                clientSocket = sslServerSocket.accept();
                connectionHandler.incomingConnection(clientSocket);
            } catch (IOException e) {
                log.info("received exception, shutting down");
                term = true;
            }
        }
    }

    public void setTerm(boolean term) {
        this.term = term;
    }
}
