package activitystreamer.client;

import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class TextFrame extends JFrame implements ActionListener {
  private static final Logger log = LogManager.getLogger();

  private JTextArea inputText;
  private JTextArea outputText;
  private JButton sendButton;
  private JButton disconnectButton;
  private JsonParser parser = new JsonParser();
  private ClientSolution client;

  public TextFrame(ClientSolution client) {
    this.client = client;

    setTitle("ActivityStreamer Text I/O");
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(1, 2));
    JPanel inputPanel = new JPanel();
    JPanel outputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());
    outputPanel.setLayout(new BorderLayout());
    Border lineBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray), "JSON input, to send to server");
    inputPanel.setBorder(lineBorder);
    lineBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray), "JSON output, received from server");
    outputPanel.setBorder(lineBorder);
    outputPanel.setName("Text output");

    inputText = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(inputText);
    inputPanel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonGroup = new JPanel();
    sendButton = new JButton("Send");
    disconnectButton = new JButton("Disconnect");
    buttonGroup.add(sendButton);
    buttonGroup.add(disconnectButton);
    inputPanel.add(buttonGroup, BorderLayout.SOUTH);
    sendButton.addActionListener(this);
    disconnectButton.addActionListener(this);


    outputText = new JTextArea();
    scrollPane = new JScrollPane(outputText);
    outputPanel.add(scrollPane, BorderLayout.CENTER);

    mainPanel.add(inputPanel);
    mainPanel.add(outputPanel);
    add(mainPanel);

    setLocationRelativeTo(null);
    setSize(1280, 768);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void setOutputText(final JsonObject obj) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String prettyJsonString = gson.toJson(obj);
    outputText.setText(prettyJsonString);
    outputText.revalidate();
    outputText.repaint();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == sendButton) {
      //sendBadActivityObject is only for testing
      // client.sendBadActivityObject();
      String msg = inputText.getText().trim().replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "");
      try {
        JsonElement element = (JsonObject) parser.parse(msg);
        if (!element.isJsonObject()) {
          log.error("invalid JSON object entered into input text field, data not sent");
        } else {
          JsonObject obj = (JsonObject) element;
          client.sendActivityObject(obj);
        }
      } catch (JsonParseException e1) {
        log.error("invalid JSON object entered into input text field, data not sent");
      }
    } else if (e.getSource() == disconnectButton) {
      log.info("Disconnecting...");
      client.disconnect();
    }
  }
}
