package me.deep.app.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class MyGame implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String FILENAME = "AllMyGames.txt";
  private static String FILENAME1 = ".txt";
  private static String FILENAME2 = ".txt";
  private static String GAMENAME;
  private static String ALLMYGAMES;
  private static Integer FINALLEVEL = 1;
  private static Integer FINALMONEY;
  private static Integer FINALWEAPONLEVEL;
  private static String InGameName = " ";
  public static boolean saveProgress = false;
  static String username;
  static boolean didStoryLine = false;

  public MyGame(int m, int i) throws ClassNotFoundException, IOException {
    money = money + m;
    level = i;
    saveProgress = true;
    readTheFileForTheGame();
  }

  public MyGame() {

  }

  public static void main(String[] args) throws ClassNotFoundException, IOException {
    // startStoryLine();
    File tempFile = new File(FILENAME);
    boolean exists = tempFile.exists();
    if (exists == false) {
      MyGame myGame1 = new MyGame();
      MyGame.ALLMYGAMES = "Your games are:-";
      FileOutputStream file2 = new FileOutputStream(FILENAME);
      ObjectOutputStream out1 = new ObjectOutputStream(file2);

      out1.writeObject(myGame1);

      out1.close();
      file2.close();
    }
    openWindow();
  }

  static String cities[] = { "New York", "Lagos", "Bangalore", "Delhi", "San Francisco", "Los Angeles", "Las Vegas",
          "London", "Berlin", "Moscow", "Paris", "Sydney", "Auckland", "Bankok", "Cario", "Barsilla", "Tornoto", "Rome",
          "Warsaw", "Mecca", "Jerusalem", "Beijing", "Shangai", "Hong Kong", "Madrid", "Mexico City", "Singapur",
          "Tokoyo", "Mumbai", "Dubai", "Buenos Aires", "Kuala Lumpur", "Jakarta", "Seattle", "Dallas", "Washington D C",
          "Pune", "Chennai", "Melborne", "Colombo", "Zuric", "Mountain Veiw", "Boston", "Atlanta", "Maimi", "Taipai" };
  static int noOfCities = cities.length;
  static String objects[] = { "Sofa", "pillow", "potted plant", "lampost", "car", "platform", "train", "Dog", "cat",
          "human being", "newspaper", "gun", "football", "basketball", "beachball", "moblie phone", "telescope",
          "phone booth", "eagle", "peigon", "motorcycle", "man", "woman", "kid", "teenager" };
  static int noOfObjects = objects.length;
  static String buldings[] = { "shack", "mansion", "barber shop", "supermarket", "pizza shop", "cafe", "starrbucks",
          "church", "mosque", "temple", "diner", "office", "villa", "sub ubran house", "hut", "barn", "field", "fair",
          "mall", "school", "bank" };
  static int noOfBuildings = buldings.length;
  static Integer level;
  static Integer money;
  static Integer weaponLevel;
  static Integer buyPrice;

  private static void startGame() throws ClassNotFoundException, EOFException, IOException {
    saveProgress = true;
    readTheFileForTheGame();
  }

  private static void startStoryLine() throws ClassNotFoundException, EOFException, IOException {
    String message = "WELCOME \n \n \n " + InGameName;
    final JFrame frame = new JFrame(message);
    String manSpeak = "<man>";
    String secretarySpeak = "<UN Secretary>";
    String continuningSecretarySpeak = "<UN Secretary countinues>";
    JButton button = new JButton("skip");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        try {
          startGame();
        } catch (ClassNotFoundException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (EOFException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });

    JPanel mainPanel = new JPanel();
    JTextField voice;
    voice = new JTextField(message, 100);
    voice.setText(manSpeak + " Sir Natan's(aliens) are approching Solar System. ");
    voice.requestFocus();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane qScroller = new JScrollPane(voice);
    qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    mainPanel.add(qScroller);
    mainPanel.add(button);
    mainPanel.setSize(10000, 10000);

    frame.add(mainPanel);
    frame.pack();
    frame.setVisible(true);
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(secretarySpeak + " Are the speical guns for the army AlienSpecalist Ready?");
    voice.requestFocus();

    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(manSpeak + "Yes Ma'am");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(secretarySpeak + "Get me " + InGameName + " on vedio line. ");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(manSpeak + "Yes your wish is my command");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(secretarySpeak + " Do me a favor and keep this Top Secret");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(continuningSecretarySpeak
            + "If anyone do ask anything which I dont think they will just head them off. \n I will take the blame");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(manSpeak + "Okay.");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(
            "<You> Good Morning  Ma'am. There are Rumors running around the camp that Nathan's are coming to Earth. \n Is it true or is it fake?");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(secretarySpeak + "I am afraid the rumors are true.");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(continuningSecretarySpeak + "These are very powerful. \n They can teleport");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {
    }
    voice.setText(continuningSecretarySpeak + "They can hit you if they are in 2 block radius from you.  ");
    voice.requestFocus();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }
    voice.setText(continuningSecretarySpeak
            + "I am sending to you they only gun which can kill them. \n You will need to kill 100 of them. ");
    voice.requestFocus();
    try {
      Thread.sleep(10000);
    } catch (Exception e) {

    }
    didStoryLine = true;
    startGame();
  }

  private static void openWindow() throws IOException, ClassNotFoundException {

    String userInput = JOptionPane
            .showInputDialog("Enter 1 to create a game \n Enter 2 to load a game \n Enter 3 see all your games");
    Integer userInputInt = Integer.parseInt(userInput);
    if (userInputInt == 1) {
      store();
    } else if (userInputInt == 3) {
      SeeHistory();
    } else if (userInputInt == 2) {
      readTheFileForTheGame();
    } else {

    }
  }

  private static void completeStartGame() {
    saveProgress = false;
    String levelDisplay = "level no:- \n" + level;
    final JFrame frame = new JFrame();
    JLabel label = new JLabel(levelDisplay);
    JButton start = new JButton("click me to start");
    start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        TestGameIdea test = new TestGameIdea("lol this is of no use");
        TestGameIdea.open(level);
      }
    });
    frame.add(BorderLayout.EAST, start);
    frame.add(label);
    frame.setSize(480, 640);
    frame.setVisible(true);

  }

  private static void store() throws IOException, ClassNotFoundException {
    System.out.println(saveProgress);
    String userInput = " ";
    MyGame myGame = new MyGame();
    if (saveProgress == false) {
      userInput = JOptionPane.showInputDialog("Enter the world name");
      FILENAME1 = userInput + FILENAME1;
      String userInput1 = JOptionPane.showInputDialog("Enter your name");
      InGameName = "Commander " + userInput1;

      MyGame.FINALLEVEL = 1;
      MyGame.FINALMONEY = 0;
      MyGame.FINALWEAPONLEVEL = 1;
      MyGame.username = InGameName;
    } else {
      System.out.println("Working fine");
      MyGame.FINALLEVEL = level;
      MyGame.FINALMONEY = money;
      MyGame.FINALWEAPONLEVEL = 1;
    }
    FileOutputStream file = new FileOutputStream(FILENAME1);
    ObjectOutputStream out = new ObjectOutputStream(file);

    out.writeObject(myGame);

    out.close();
    file.close();
    if (saveProgress == false) {
      FileInputStream file1 = new FileInputStream(FILENAME);
      ObjectInputStream in = new ObjectInputStream(file1);
      MyGame myGame2 = (MyGame) in.readObject();

      in.close();
      file1.close();

      MyGame myGame1 = new MyGame();
      MyGame.ALLMYGAMES = myGame2.ALLMYGAMES + "\n" + userInput;
      FileOutputStream file2 = new FileOutputStream(FILENAME);
      ObjectOutputStream out1 = new ObjectOutputStream(file2);

      out1.writeObject(myGame1);

      out1.close();
      file2.close();
      startStoryLine();
    } else {
      completeStartGame();
    }
  }

  private static void readTheFileForTheGame() throws IOException, ClassNotFoundException, EOFException {
    FileInputStream file = new FileInputStream(FILENAME1);
    ObjectInputStream in = new ObjectInputStream(file);
    MyGame myGame = (MyGame) in.readObject();

    in.close();
    file.close();
    money = MyGame.FINALMONEY;
    level = MyGame.FINALLEVEL;
    weaponLevel = MyGame.FINALWEAPONLEVEL;
    InGameName = MyGame.username;
    completeStartGame();
  }

  private static void SeeHistory() throws IOException, ClassNotFoundException {
    FileInputStream file = new FileInputStream(FILENAME);
    ObjectInputStream in = new ObjectInputStream(file);
    MyGame myGame = (MyGame) in.readObject();

    in.close();
    file.close();
    JOptionPane.showMessageDialog(null, MyGame.ALLMYGAMES);
    openWindow();
  }

}
