package me.deep.app.test;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("deprecation")
public class SimpleChatClient extends Applet {
  static JTextArea incoming;
  static JTextField outgoing;
  static BufferedReader reader;
  static PrintWriter writer;
  static Socket sock;
  static String USERNAME;
  static String whoSaid = "";
  static JFrame frame;
  static JFrame frame1;
  static JFrame frame2;
  static final String FILENAME2 = "users/ALLtheUSERS.txt";
  static JList list;
  static String firstFileOption;
  static String secondFileOption;
  static boolean shownAlready = false;
  static boolean publicChat;

  public void go() {
    firstFileOption = null;
    secondFileOption = null;
    shownAlready = false;
    frame = new JFrame();
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    JButton publicChatButton = new JButton("public chat");
    JButton privateChatButton = new JButton("private chat");
    publicChatButton.addActionListener(new publicChat());
    privateChatButton.addActionListener(new privateChat());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.add(privateChatButton);
    panel.add(publicChatButton);
    frame.add(panel);
    frame.setSize(600, 300);
    frame.setVisible(true);
  }

  private static String chatNormalForm(String fileMessage) {
    String[] chat = fileMessage.split("~");
    fileMessage = "";
    for (int i = 0; i < chat.length; i++) {
      System.out.println(chat[i]);
      fileMessage = fileMessage + "\n" + chat[i];
    }
    return fileMessage;
  }

  private static void chatPrivate() {
    publicChat = false;
    people();
    JPanel mainPanel = new JPanel();
    list = new JList(allPeople);
    JScrollPane scroller = new JScrollPane(list);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    mainPanel.add(scroller);
    list.setVisibleRowCount(4);
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.addListSelectionListener(new listChecker());
    frame = new JFrame("My Chat App ");
    incoming = new JTextArea(15, 50);
    incoming.setLineWrap(true);
    incoming.setWrapStyleWord(true);
    incoming.setEditable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane qScroller = new JScrollPane(incoming);
    qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    outgoing = new JTextField(20);
    JButton backButton = new JButton("back");
    backButton.addActionListener(new returnButtonListener());
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new SendButtonListener());
    mainPanel.add(qScroller);
    mainPanel.add(outgoing);
    mainPanel.add(sendButton);
    mainPanel.add(backButton);
    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
    setUpNetworkingPrivate();
    frame.setSize(650, 500);
    frame.setVisible(true);
    Thread readerThread = new Thread(new IncomingReader());
    readerThread.start();

  }

  static String selection;

  static class listChecker implements ListSelectionListener {
    private static final long serialVersionUID = 1L;

    public void valueChanged(ListSelectionEvent lse) {
      if (!lse.getValueIsAdjusting()) {
        setFileName();
      }
    }
  }

  private static void setFileName() {
    selection = (String) list.getSelectedValue();
    firstFileOption = selection + USERNAME;
    secondFileOption = USERNAME + selection;
    loadChat();
  }

  private static void chatPublic() {
    publicChat = true;
    firstFileOption = "public";
    secondFileOption = "public";
    frame = new JFrame("My Chat App ");
    JPanel mainPanel = new JPanel();
    incoming = new JTextArea(15, 50);
    incoming.setLineWrap(true);
    incoming.setWrapStyleWord(true);
    incoming.setEditable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane qScroller = new JScrollPane(incoming);
    qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    outgoing = new JTextField(20);
    JButton backButton = new JButton("back");
    backButton.addActionListener(new returnButtonListener());
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new SendButtonListener());
    mainPanel.add(qScroller);
    mainPanel.add(outgoing);
    mainPanel.add(sendButton);
    mainPanel.add(backButton);
    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
    setUpNetworking();
    loadChat();
    frame.setSize(650, 500);
    frame.setVisible(true);
    Thread readerThread = new Thread(new IncomingReader());
    readerThread.start();

  }

  static class privateChat implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      frame.setVisible(false);
      chatPrivate();
    }
  }

  static class publicChat implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      frame.setVisible(false);
      chatPublic();
    }
  }

  private static void setUpNetworkingPrivate() {
    try {
      sock = new Socket("127.0.0.1", 1234);
      InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
      reader = new BufferedReader(streamReader);
      writer = new PrintWriter(sock.getOutputStream());
      System.out.println("networking established");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private static void setUpNetworking() {
    try {
      sock = new Socket("127.0.0.1", 5000);
      InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
      reader = new BufferedReader(streamReader);
      writer = new PrintWriter(sock.getOutputStream());
      System.out.println("networking established");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  static class returnButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      frame.setVisible(false);
      new SimpleChatClient().go();
    }
  }

  static class SendButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      try {
        whoSaidVoid();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      outgoing.setText("");
      outgoing.requestFocus();
    }
  }

  private static void whoSaidVoid() {
    whoSaid = "<" + USERNAME + ">";
    if (!publicChat) {
      if (firstFileOption != null) {
        String outgoingString = whoSaid + outgoing.getText() + "~////~" + firstFileOption + "~////~" + secondFileOption;
        System.out.println(whoSaid + " " + outgoing.getText() + " " + firstFileOption + " " + secondFileOption + " "
                + outgoingString);
        writer.println(outgoingString);
        writer.flush();
      } else {
        JOptionPane.showMessageDialog(null, "Select a chat first");
      }
    } else {
      String outgoingString = whoSaid + outgoing.getText();
      writer.println(outgoingString);
      writer.flush();
    }
  }

  public static void main(String[] args) {
    new SimpleChatClient().go();
  }

  public void start(String userName) {
    USERNAME = userName;
    System.out.println(USERNAME);
    whoSaid = "<" + userName + ">";
    new SimpleChatClient().go();
  }

  static String[] allPeople;

  private static String[] people() {
    try {
      FileReader usersFileReader = new FileReader(FILENAME2);
      BufferedReader inputReader = new BufferedReader(usersFileReader);
      String input = inputReader.readLine();
      allPeople = input.split("~");
    } catch (Exception e) {
      // TODO: handle exception
    }
    return allPeople;
  }

  private static void updateChat(String message) {
    System.out.println("client read " + message);
    if (!publicChat) {

      String chat[] = message.split("~////~", 3);
      String firstFileOptionString = chat[1];
      String secondString = chat[2];
      if (firstFileOption.equalsIgnoreCase(firstFileOptionString)) {
        message = chatNormalForm(chat[0]);
        incoming.append(message);
      } else if (secondFileOption.equalsIgnoreCase(secondString)) {
        message = chatNormalForm(chat[0]);
        incoming.append(message);
      }
    } else {
      incoming.append(message);
    }
  }

  private static void loadChat() {
    try {
      if (!publicChat) {
        File firstFile = new File("chat/" + firstFileOption + ".txt");
        File secondFile = new File("chat/" + secondFileOption + ".txt");
        boolean firstFileExists = firstFile.exists();
        boolean secondFileExists = secondFile.exists();
        if (firstFileExists) {
          FileReader fileReader = new FileReader(firstFile);
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          String s = bufferedReader.readLine();
          s = chatNormalForm(s);
          incoming.setText(s);
        } else if (secondFileExists) {
          FileReader fileReader = new FileReader(firstFile);
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          String s = bufferedReader.readLine();
          s = chatNormalForm(s);
          incoming.setText(s);
        }
      } else {
        FileReader fileReader = new FileReader("chat/serverChat.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s = bufferedReader.readLine();
        s = chatNormalForm(s);
        incoming.setText(s);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  static class IncomingReader implements Runnable {
    public void run() {
      try {
        String message = " ";
        while ((message = reader.readLine()) != null) {
          updateChat(message);
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
}
