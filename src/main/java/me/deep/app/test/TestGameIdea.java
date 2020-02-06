package me.deep.app.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class TestGameIdea extends JFrame implements Runnable {
  transient private static final String FILENAME = "AllMyGames.txt";
  transient private static String FILENAME1 = ".txt";
  transient private static String FILENAME2 = ".txt";
  private static String GAMENAME;
  private static String ALLMYGAMES;
  static Integer FINALLEVEL;
  static Integer FINALMONEY;
  private static Integer FINALWEAPONLEVEL;
  private static String InGameName = " ";

  public TestGameIdea(int[][] m, boolean ma, int mo, boolean a, int t) {
    map = m;
    monsterAlive = ma;
    monsterHealth = mo;
    haveShootAlreadyInLast10Sec = a;
    timePassed = t;
  }

  public TestGameIdea(boolean ma, int mo, boolean a) {
    monsterAlive = ma;
    monsterHealth = mo;
    haveShootAlreadyInLast10Sec = a;
  }

  public static void increaseTime() {
    timePassed++;
  }

  public TestGameIdea(int t, boolean b) {
    timePassed = t;
    haveShootAlreadyInLast10Sec = b;
  }

  public TestGameIdea() {

  }

  public TestGameIdea(String h) {
    System.out.println(h);
  }

  public TestGameIdea(boolean b) {
    System.out.println("coming till here");
    panel = new JPanel();
    frame = new JFrame();
    coordinates = new JLabel();
    monsterHealthPB = new JProgressBar();
    playerHealthPB = new JProgressBar();
    gunCoolDownTimer = new JProgressBar();

    end = false;
    draw = false;
    monsterAlive = true;
    playerHealth = 100;
    monsterHealth = 100;
    try {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      int width = (int) screenSize.getWidth();
      int height = (int) screenSize.getHeight();
      String coordinatesString = "X pos:- \n 4 + \n Y pos:- \n 4 \n number of monsters left:- \n" + waveNoAndMobNumber;
      coordinates.setText(coordinatesString);
      monsterHealthPB.setString("monster health:-" + monsterHealth + "/" + mobMax);
      monsterHealthPB.setStringPainted(true);
      monsterHealthPB.setValue(100);
      monsterHealthPB.setMaximum(100);
      playerHealthPB.setString("your health:-" + playerHealth + "/" + mobMax);
      playerHealthPB.setStringPainted(true);
      playerHealthPB.setValue(100);
      playerHealthPB.setMaximum(100);
      gunCoolDownTimer.setStringPainted(true);
      gunCoolDownTimer.setString(" Gun Cool down timer:- CoolDown Complete");
      gunCoolDownTimer.setValue(10);
      gunCoolDownTimer.setMaximum(10);
      panel.add(gunCoolDownTimer);
      panel.add(coordinates);
      panel.add(monsterHealthPB);
      panel.add(playerHealthPB);
      frame.add(panel);
      //
      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      thread = new Thread(this);
      panel.setSize(screenSize);
      //
      pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
      textures = new ArrayList<Texture>();
      textures.add(Texture.wood);
      textures.add(Texture.brick);
      textures.add(Texture.bluestone);
      textures.add(Texture.stone);
      //
      camera = new Camera(4, 4, 1, 0, 0, -.66);
      screen = new Screen(mapWidth, mapHeight, textures, width, height);
      //
      frame.setSize(screenSize);
      frame.setResizable(false);
      frame.addKeyListener(camera);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setBackground(Color.black);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);

      //
      start();
    } catch (RuntimeException e) {
      e.printStackTrace();
      // TODO: handle exception
    }
  }

  private BufferedImage resetImage(BufferedImage sentBufferedImage) {
    BufferedImage newBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    sentBufferedImage = newBufferedImage;
    return sentBufferedImage;

  }

  public synchronized static void stop() {
    System.out.println(" stop Void working fine");
    running = false;
    //
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("working fine closing");

  }

  public void render() {
    bs = frame.getBufferStrategy();
    if (bs == null) {

      frame.createBufferStrategy(3);
      System.out.println("returning");
      return;
    }
    Graphics g1 = bs.getDrawGraphics();
    g1.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    bs.show();
  }

  public void run() {
    System.out.println(" Run Void working fine");
    long lastTime = System.nanoTime();
    final double ns = 1000000000.0 / 60.0;// 60 times per second
    double delta = 0;
    frame.requestFocus();
    //
    while (running) {
      long now = System.nanoTime();
      delta = delta + ((now - lastTime) / ns);
      lastTime = now;
      //
      while (delta >= 1)// Make sure update is only happening 60 times a second
      {
        try {
          update();
        } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        delta--;
      }
      render();// displays to the screen unrestricted time
    }
  }
  //
  //
  //

  public static void main(String[] args) throws IOException {
    File tempFile = new File(FILENAME);
    boolean exists = tempFile.exists();
    if (exists == false) {
      FileWriter writer2 = new FileWriter(FILENAME);
      writer2.write("Your games are:-");
      writer2.close();
    }
    try {
      openWindow();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private synchronized void start() {
    System.out.println(" Start Void working fine");
    running = true;
    thread.start();
  }

  public static void update() throws ClassNotFoundException, IOException {
    if (monsterHealth < 1) {
      waveNoAndMobNumber--;
      monsterHealth = 100;
    }
    //
    if (waveNoAndMobNumber < 1) {
      level++;
      moneyEarned = mobNumber * 20;
      endTheGame();
      //
    }
    //
    if (playerHealth < 1) {
      moneyEarned = mobNumber * 10;
      endTheGame();
    }
    mobUpdate();

    if (haveShootAlreadyInLast10Sec == true) {
      //
      if (timePassed < 599) {
        // System.out.println("working fine");
        increaseTime();
      }
    }
    //
    if (playerRegen < 599) {
      playerRegen++;
    } else {
      if (playerHealth < 100) {
        playerHealth = playerHealth + 10;
        if (playerHealth > 100) {
          playerHealth = 100;
        }
      }
      playerRegen = 0;
    }
    if (timePassed == 599) {
      timePassed = 0;
      haveShootAlreadyInLast10Sec = false;
    }
    // handles all of the logic restricted time
    monsterHealthPB.setVisible(false);
    monsterHealthPB.setVisible(true);
    playerHealthPB.setVisible(false);
    playerHealthPB.setVisible(true);
    coordinates.setVisible(false);
    coordinates.setVisible(true);
    gunCoolDownTimer.setVisible(false);
    gunCoolDownTimer.setVisible(true);
    screen.update(map, camera, pixels); //
    camera.update(map, camera, haveShootAlreadyInLast10Sec, monsterAlive, monsterHealth, timePassed);//
    updateProgress(playerHealth, monsterHealth, timePassed, haveShootAlreadyInLast10Sec, waveNoAndMobNumber);
  }

  private static void endTheGame() {
    frame.setVisible(false);
    if (!end) {
      end = true;
      running = false;
      money = moneyEarned + money;
      frame.setVisible(false);
      try {
        startGame();
      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (EOFException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      //
    }
  }

  private static void updateProgress(int p, int mo, int t, boolean b, int k) {
    DecimalFormat numberFormat = new DecimalFormat("#.00");
    String coordinatesString = "X pos:- \n" + numberFormat.format(camera.xPos) + "\n Y pos:- \n"
            + numberFormat.format(camera.yPos) + "\n number of monsters left:- \n" + k;
    int timePassedInSec = t / 60;

    coordinates.setText(coordinatesString);
    playerHealthPB.setString("your health:-" + p + "/" + mobMax);
    playerHealthPB.setValue(p);
    monsterHealthPB.setString("monster health:-" + mo + "/" + mobMax);
    monsterHealthPB.setValue(mo);

    if (b == true) {
      coordinates.setText(coordinatesString);
      gunCoolDownTimer.setString(" Gun Cool down timer:- " + timePassedInSec + "/ 10");
      gunCoolDownTimer.setValue(timePassedInSec);
    } else if (b == false) {
      coordinates.setText(coordinatesString);
      gunCoolDownTimer.setString(" Gun Cool down timer:- Cool Down Complete");
      gunCoolDownTimer.setValue(10);
    }
  }

  private static void teleportMonster() {

    int teleportXPos = 0;
    int teleportYPos = 0;
    boolean freeSpace = false;
    while (freeSpace == false) {
      teleportXPos = (int) (Math.random() * 15);
      while (teleportXPos < 0 || teleportXPos > 15) {
        teleportXPos = (int) (Math.random() * 15);
      }
      teleportYPos = (int) (Math.random() * 15);
      while (teleportYPos < 0 || teleportYPos > 15) {
        teleportYPos = (int) (Math.random() * 15);
      }
      if (map[teleportXPos][teleportYPos] == 0) {
        map[mobXPos][mobYPos] = 0;
        mobXPos = teleportXPos;
        mobYPos = teleportYPos;
        map[mobXPos][mobYPos] = 2;
        freeSpace = true;
      }
    }
  }

  private static void moveMonster() {
    //
    boolean spaceThereXPositive = true;
    boolean spaceThereXNegative = true;
    boolean spaceThereYPositive = true;
    boolean spaceThereYNegative = true;
    //
    int mobXPosPositive = mobXPos + 1;
    int mobXPosNegative = mobXPos - 1;
    //
    int mobYPosPositive = mobYPos + 1;
    int mobYPosNegative = mobYPos - 1;
    //
    if (mobXPosNegative < 0) {
      //
      spaceThereXNegative = false;
      mobXPosNegative = 3;// random to stop the error
      //
    }
    if (mobYPosNegative < 0) {
      //
      spaceThereYNegative = false;
      mobYPosNegative = 3;// random to stop the error
      //
    }
    if (mobXPosPositive > 15) {
      //
      mobXPosPositive = 3;// random to stop the error
      spaceThereXPositive = false;
    }
    if (mobYPosPositive > 15) {
      //
      mobYPosPositive = 3;// random to stop the error
      spaceThereYPositive = false;
    }
    //
    int random = (int) (Math.random() * 2);
    if (random == 0) {
      //

      if (map[mobXPosPositive][mobYPos] == 0) {
        if (spaceThereXPositive == true) {
          //

          map[mobXPos][mobYPos] = 0;
          mobXPos = mobXPosPositive;
          map[mobXPosPositive][mobYPos] = 2;
        } //
      } else if (map[mobXPosNegative][mobYPos] == 0) {
        if (spaceThereXNegative == true) {
          //

          map[mobXPos][mobYPos] = 0;
          mobXPos = mobXPosNegative;
          map[mobXPosNegative][mobYPos] = 2;
        } //
      }
    }
    if (random == 1) {
      //
      if (map[mobXPos][mobYPosPositive] == 0) {
        if (spaceThereYPositive == true) {
          //

          map[mobXPos][mobYPos] = 0;
          mobYPos = mobYPosPositive;
          map[mobXPos][mobYPos] = 2;
        } //
      } else if (map[mobXPos][mobYPosNegative] == 0) {
        if (spaceThereYNegative == true) {
          //

          map[mobXPos][mobYPos] = 0;
          mobYPos = mobYPosNegative;
          map[mobXPos][mobYPos] = 2;
          //
        }
      }
    }
  }

  private static boolean distanceFromMonster(boolean monsterShoot) {

    double distance = 0;
    //
    if (camera.xPos > mobXPos) {
      //
      double XDistance = camera.xPos - mobXPos;
      distance = distance + XDistance;
      //
    } else {
      //
      double XDistance = mobXPos - camera.xPos;
      distance = distance + XDistance;
      //
    }
    if (camera.yPos > mobYPos) {
      //
      double YDistance = camera.yPos - mobYPos;
      distance = distance + YDistance;
      //
    } else {
      //
      double YDistance = mobYPos - camera.yPos;
      distance = distance + YDistance;
      //
    }
    if (distance < 6) {
      //
      monsterShoot = true;
      int random = (int) (Math.random() * 120);
      //
      if (random == 0) {
        playerHealth = playerHealth - 20;
      }
      //
    }
    return monsterShoot;
  }

  private static void mobUpdate() {
    boolean monsterShoot = false;
    distanceFromMonster(monsterShoot);
    if (monsterShoot == false) {
      int random = (int) (Math.random() * 60);
      if (random == 0) {
        int moveRandom = (int) (Math.random() * 10);
        if (moveRandom == 0) {
          teleportMonster();
        } else {
          moveMonster();
        }
      }
    }
  }

  //
  //
  //
  private static void storeProgressAndStartGame() {

    try {
      FileWriter writer;
      writer = new FileWriter(FILENAME1);
      writer.write(money + "/" + level);
      writer.close();
      FileReader fileReader = new FileReader(FILENAME1);
      BufferedReader reader = new BufferedReader(fileReader);
      String data = reader.readLine();
      String[] splitData = data.split("/");
      level = Integer.parseInt(splitData[1]);
      money = Integer.parseInt(splitData[0]);
      waveNoAndMobNumber = level;
    } catch (Exception e) {

    }

    //
    //
    //
    //
    //

    System.out.println(money + " " + level + " " + waveNoAndMobNumber);
    String levelDisplay = "level no:- \n" + level;
    final JFrame frameStartGame = new JFrame();
    JLabel label = new JLabel(levelDisplay);
    JButton start = new JButton("click me to start");
    start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          frameStartGame.setVisible(false);
          new TestGameIdea(false);
        } catch (Exception e1) {

        }
      }
    });

    frameStartGame.add(BorderLayout.EAST, start);
    frameStartGame.add(label);
    frameStartGame.setSize(480, 640);
    frameStartGame.setVisible(true);
  }

  private static void startNewGameFirstTime() {
    String inputString = JOptionPane.showInputDialog("Enter game name");
    FILENAME1 = inputString + FILENAME1;
    try {
      FileReader fileReader = new FileReader(FILENAME1);
      BufferedReader reader = new BufferedReader(fileReader);
      String data = reader.readLine();
      String[] splitData = data.split("/");
      level = Integer.parseInt(splitData[1]);
      money = Integer.parseInt(splitData[0]);
      waveNoAndMobNumber = level;
    } catch (Exception e) {
      // TODO: handle exception
    }
    System.out.println(money + " " + level + " " + waveNoAndMobNumber);
    String levelDisplay = "level no:- \n" + level;
    final JFrame frameStartGame = new JFrame();
    JLabel label = new JLabel(levelDisplay);
    JButton start = new JButton("click me to start");
    start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          System.out.println("opening ");
          frameStartGame.setVisible(false);
          new TestGameIdea(false);
        } catch (Exception e1) {

        }
      }
    });

    frameStartGame.add(BorderLayout.EAST, start);
    frameStartGame.add(label);
    frameStartGame.setSize(480, 640);
    frameStartGame.setVisible(true);
  }

  private static void startGame() throws ClassNotFoundException, EOFException, IOException {
    System.out.println(" startGame Void working fine");
    storeProgressAndStartGame();
  }

  private static void startStoryLine() throws ClassNotFoundException, EOFException, IOException {
    System.out.println(" startStoryLine Void working fine");
    String message = "WELCOME \n \n \n " + InGameName;
    final JFrame frameVoice = new JFrame(message);
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
    frameVoice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JScrollPane qScroller = new JScrollPane(voice);
    qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    mainPanel.add(qScroller);
    mainPanel.add(button);
    mainPanel.setSize(10000, 10000);

    frameVoice.add(mainPanel);
    frameVoice.pack();
    frameVoice.setVisible(true);
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
    System.out.println(" openWindow Void working fine");
    String userInput = JOptionPane
            .showInputDialog("Enter 1 to create a game \n Enter 2 to load a game \n Enter 3 see all your games");
    Integer userInputInt = Integer.parseInt(userInput);
    if (userInputInt == 1) {
      store(false);
    } else if (userInputInt == 3) {
      SeeHistory();
    } else if (userInputInt == 2) {
      startNewGameFirstTime();
    } else {

    }
  }

  private static void completeStartGame() {
    System.out.println(" completeStartGame Void working fine");
    String levelDisplay = "level no:- \n" + level;
    final JFrame frameStartGame = new JFrame();
    JLabel label = new JLabel(levelDisplay);
    JButton start = new JButton("click me to start");
    start.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          frameStartGame.setVisible(false);
          new TestGameIdea(false);
        } catch (Exception e1) {

        }
      }
    });

    frameStartGame.add(BorderLayout.EAST, start);
    frameStartGame.add(label);
    frameStartGame.setSize(480, 640);
    frameStartGame.setVisible(true);

  }

  private static void store(boolean b) throws IOException, ClassNotFoundException {
    System.out.println(" store Void working fine");
    String userInput = " ";
    userInput = JOptionPane.showInputDialog("Enter the world name");
    FILENAME1 = userInput + FILENAME1;
    String userInput1 = JOptionPane.showInputDialog("Enter your name");
    InGameName = "Commander " + userInput1;
    try {
      FileWriter writer = new FileWriter(FILENAME1);
      writer.write("0/1");
      writer.close();
      FileReader fileReader = new FileReader(FILENAME);
      BufferedReader reader = new BufferedReader(fileReader);
      String allTheGames = reader.readLine();
      FileWriter writer2 = new FileWriter(FILENAME);
      writer2.write(allTheGames + "\n" + userInput);
      writer2.close();
    } catch (Exception e) {

    }
    startGame();

  }

  private static void readTheFileForTheGame() throws IOException, ClassNotFoundException, EOFException {
    System.out.println(" readTheFileForTheGame Void working fine");
    FileInputStream file1 = new FileInputStream(FILENAME1);
    ObjectInputStream in = new ObjectInputStream(file1);
    TestGameIdea myGame1 = (TestGameIdea) in.readObject();

    in.close();
    file1.close();
    money = myGame1.FINALMONEY;
    level = myGame1.FINALLEVEL;
    weaponLevel = myGame1.FINALWEAPONLEVEL;
    InGameName = myGame1.username;
    System.out.println(
            myGame1.FINALMONEY + " " + myGame1.FINALLEVEL + " " + myGame1.FINALWEAPONLEVEL + " " + myGame1.username);
    completeStartGame();
  }

  private static void SeeHistory() throws IOException, ClassNotFoundException {
    FileReader fileReader = new FileReader(FILENAME);
    BufferedReader reader = new BufferedReader(fileReader);
    String allTheGames = reader.readLine();
    JOptionPane.showMessageDialog(null, allTheGames);
    openWindow();
  }

  static JProgressBar playerHealthPB;
  static JProgressBar monsterHealthPB;
  static JProgressBar gunCoolDownTimer;
  static JLabel coordinates;
  transient boolean draw = false;
  transient BufferStrategy bs;
  transient static JFrame frame;
  transient static Integer level = 1;
  transient static Integer weaponLevel = 0;
  transient static Integer buyPrice = 0;
  transient static String cities[] = { "New York", "Lagos", "Bangalore", "Delhi", "San Francisco", "Los Angeles",
          "Las Vegas", "London", "Berlin", "Moscow", "Paris", "Sydney", "Auckland", "Bankok", "Cario", "Barsilla",
          "Tornoto", "Rome", "Warsaw", "Mecca", "Jerusalem", "Beijing", "Shangai", "Hong Kong", "Madrid", "Mexico City",
          "Singapur", "Tokoyo", "Mumbai", "Dubai", "Buenos Aires", "Kuala Lumpur", "Jakarta", "Seattle", "Dallas",
          "Washington D C", "Pune", "Chennai", "Melborne", "Colombo", "Zuric", "Mountain Veiw", "Boston", "Atlanta",
          "Maimi", "Taipai" };
  transient static int noOfCities = cities.length;
  transient static String objects[] = { "Sofa", "pillow", "potted plant", "lampost", "car", "platform", "train", "Dog",
          "cat", "human being", "newspaper", "gun", "football", "basketball", "beachball", "moblie phone", "telescope",
          "phone booth", "eagle", "peigon", "motorcycle", "man", "woman", "kid", "teenager" };
  transient static int noOfObjects = objects.length;
  transient static String buldings[] = { "shack", "mansion", "barber shop", "supermarket", "pizza shop", "cafe",
          "starrbucks", "church", "mosque", "temple", "diner", "office", "villa", "sub ubran house", "hut", "barn",
          "field", "fair", "mall", "school", "bank" };
  static boolean end = false;
  transient static boolean saveProgress = false;
  transient static String username;
  transient static boolean didStoryLine = false;
  transient int monsterXpos, monsterYpos;
  transient static int mobMax = 100;
  private static final long serialVersionUID = 1L;
  transient static int mapWidth = 15;
  transient static int mapHeight = 15;
  transient static int monsterHealth = 100;
  transient static boolean monsterAlive = true;
  transient static int playerHealth = 100;
  transient static int mobXPos = 12, mobYPos = 13;
  transient static int waveNoAndMobNumber = 1;
  transient static int moneyEarned = 0;
  transient static int mobNumber = 1;
  transient static Thread thread;
  transient static int playerRegen = 0;
  transient static boolean running;
  transient BufferedImage image;
  transient static int[] pixels;
  transient static ArrayList<Texture> textures;
  transient static Camera camera;
  transient static Screen screen;
  transient static Fire fire;
  transient static int width, height;
  transient static boolean haveShootAlreadyInLast10Sec = false;
  transient static boolean endGame = false;
  transient static int timePassed = 0;
  transient static JPanel panel;
  transient static int[][] map = { //
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //
          { 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1 }, //
          { 1, 0, 4, 4, 4, 4, 4, 0, 0, 0, 0, 1, 1, 0, 1 }, //
          { 1, 0, 4, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 4, 0, 0, 4, 0, 0, 1, 1, 1, 1, 1, 0, 1 }, //
          { 1, 1, 4, 0, 0, 4, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 4, 4, 0, 4, 0, 0, 1, 0, 4, 0, 4, 0, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 4, 0, 4, 0, 1 }, //
          { 1, 1, 0, 1, 0, 1, 0, 1, 4, 4, 4, 0, 4, 4, 4 }, //
          { 1, 0, 0, 4, 0, 0, 1, 0, 0, 4, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 4, 0, 0, 1, 0, 0, 4, 0, 1, 0, 0, 4 }, //
          { 1, 0, 4, 4, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 4 }, //
          { 1, 0, 0, 4, 0, 0, 1, 0, 0, 1, 1, 0, 1, 2, 4 }, //
          { 1, 0, 0, 0, 4, 0, 0, 4, 0, 0, 0, 1, 0, 0, 4 }, //
          { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };//
  transient static int[][] mapSecondHand = { //
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 1, 1, 0, 1, 1, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 0, 3, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };//
  transient static int money = 0;
}
