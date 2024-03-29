package activitystreamer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.net.Socket;
import java.security.SecureRandom;

public class Settings {
  private static final Logger log = LogManager.getLogger();
  private static SecureRandom random = new SecureRandom();
  private static int localPort = 3780;
  private static int secureLocalPort = 3781;
  private static boolean isSecure = false;
  private static String localHostname = "localhost";
  private static String remoteHostname = null;
  private static boolean remoteIsSecure = false;
  private static int remotePort = 3780;
  private static int activityInterval = 5000; // milliseconds
  private static String secret = "";
  private static String username = "anonymous";
  private static String id = "";

  private static String privateKeyStore = "/server.jks";
  private static String privatePass = "foobar";
  private static String publicKeyStore = "/client.jks";
  private static String publicPass = "foobar";

  public static String getPrivateKeyStore() {
    return privateKeyStore;
  }

  //getters and setters for keys and passwords
  public static void setPrivateKeyStore(String privateKeyStore) {
    Settings.privateKeyStore = privateKeyStore;
  }

  public static String getPrivatePass() {
    return privatePass;
  }

  public static void setPrivatePass(String privatePass) {
    Settings.privatePass = privatePass;
  }

  public static String getPublicKeyStore() {
    return publicKeyStore;
  }

  public static void setPublicKeyStore(String publicKeyStore) {
    Settings.publicKeyStore = publicKeyStore;
  }

  public static String getPublicPass() {
    return publicPass;
  }

  public static void setPublicPass(String publicPass) {
    Settings.publicPass = publicPass;
  }
  // end of getters and setters for keys and passwords

  public static boolean getIsSecure() {
    return isSecure;
  }

  public static void setIsSecure(boolean isSecure) {
    Settings.isSecure = isSecure;
  }

  public static int getLocalPort() {
    return localPort;
  }

  public static void setLocalPort(int localPort) {
    if (localPort < 0 || localPort > 65535) {
      log.error("supplied port " + localPort + " is out of range, using " + getLocalPort());
    } else {
      Settings.localPort = localPort;
    }
  }

  public static int getSecureLocalPort() {
    return secureLocalPort;
  }

  public static void setSecureLocalPort(int secureLocalPort) {
    if ((secureLocalPort < 0 || secureLocalPort > 65535) && (secureLocalPort != getLocalPort())) {
      log.error("supplied port " + secureLocalPort + " is out of range, using " + getSecureLocalPort());
    } else {
      Settings.secureLocalPort = secureLocalPort;
    }
  }

  public static int getRemotePort() {
    return remotePort;
  }

  public static void setRemotePort(int remotePort) {
    if (remotePort < 0 || remotePort > 65535) {
      log.error("supplied port " + remotePort + " is out of range, using " + getRemotePort());
    } else {
      Settings.remotePort = remotePort;
    }
  }

  public static String getRemoteHostname() {
    return remoteHostname;
  }

  public static void setRemoteHostname(String remoteHostname) {
    Settings.remoteHostname = remoteHostname;
  }

  public static int getActivityInterval() {
    return activityInterval;
  }

  public static void setActivityInterval(int activityInterval) {
    Settings.activityInterval = activityInterval;
  }

  public static String getSecret() {
    return secret;
  }

  public static void setSecret(String s) {
    secret = s;
  }

  public static String getUsername() {
    return username;
  }

  public static void setUsername(String username) {
    Settings.username = username;
  }

  public static String getLocalHostname() {
    return localHostname;
  }

  public static void setLocalHostname(String localHostname) {
    Settings.localHostname = localHostname;
  }

  public static String getId() {
    return id;
  }

  public static void setId(String id) {
    Settings.id = id;
  }
  /*
     * some general helper functions
	 */

  public static String socketAddress(Socket socket) {
    return socket.getInetAddress() + ":" + socket.getPort();
  }

  public static String nextSecret() {
    return new BigInteger(130, random).toString(32);
  }


}
