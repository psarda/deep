package me.deep.app.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class TestGameIdea extends JFrame implements Runnable {
  int monsterXpos, monsterYpos;
  public static JFrame frame;
  public static int mobMax = 100;
  private static final long serialVersionUID = 1L;
  public static int mapWidth = 15;
  public static int mapHeight = 15;
  public static int monsterHealth = 100;
  public static boolean monsterAlive = true;
  public static int playerHealth = 100;
  public static int mobXPos = 12, mobYPos = 13;
  public static int waveNoAndMobNumber = 1;
  public static int moneyEarned = 0;
  public static int mobNumber = 1;
  private static Thread thread;
  public static int playerRegen = 0;
  private static boolean running;
  private BufferedImage image;
  public static int[] pixels;
  public static ArrayList<Texture> textures;
  public static Camera camera;
  public static Screen screen;
  public static Fire fire;
  public static int width, height;
  public static boolean haveShootAlreadyInLast10Sec = false;
  public static boolean endGame = false;
  public static int timePassed = 0;
  public static int[][] map = { //
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1 }, //
          { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 1, 1, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 3, 3, 0, 1, 1, 1, 1, 1, 0, 1 }, //
          { 1, 0, 3, 0, 0, 3, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 0, 3, 3, 0, 1, 0, 3, 0, 4, 0, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 3, 0, 4, 0, 1 }, //
          { 1, 1, 0, 1, 0, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4 }, //
          { 1, 0, 0, 3, 0, 0, 1, 4, 0, 4, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 3, 0, 0, 1, 4, 0, 4, 0, 0, 0, 0, 4 }, //
          { 1, 0, 3, 3, 3, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 3, 0, 0, 1, 4, 0, 3, 3, 3, 3, 2, 4 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };//
  public static int[][] mapSecondHand = { //
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

  public TestGameIdea(String h) {
    System.out.println(h);
  }

  public TestGameIdea() {
    //
    if (endGame == false) {
      thread = new Thread(this);
      //
      image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
      //
      Progress progress = new Progress(playerHealth, mobMax, monsterHealth, timePassed, waveNoAndMobNumber);
      //
      pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
      textures = new ArrayList<Texture>();
      textures.add(Texture.wood);
      textures.add(Texture.brick);
      textures.add(Texture.bluestone);
      textures.add(Texture.stone);
      //
      camera = new Camera(4, 4, 1, 0, 0, -.66);
      screen = new Screen(mapWidth, mapHeight, textures, 640, 480);
      //
      addKeyListener(camera);
      //
      setSize(640, 480);
      setResizable(false);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBackground(Color.black);
      setLocationRelativeTo(null);
      setVisible(true);
      //
      start();
    } else {
      setVisible(false);
    }
  }

  public static void open(int i) {
    waveNoAndMobNumber = i;
    mobNumber = i;
    TestGameIdea game = new TestGameIdea();
  }

  private synchronized void start() {
    running = true;
    thread.start();
  }

  public synchronized void stop() {
    running = false;
    //
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //
  }

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    //
    if (bs == null) {
      //
      createBufferStrategy(3);
      return;
      //
    }
    //
    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    bs.show();
  }

  public void run() {
    long lastTime = System.nanoTime();
    final double ns = 1000000000.0 / 60.0;// 60 times per second
    double delta = 0;
    requestFocus();
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

  public static void update() throws ClassNotFoundException, IOException {
    if (monsterHealth < 1) {
      waveNoAndMobNumber--;
      monsterHealth = 100;
    }
    //
    if (waveNoAndMobNumber < 1) {
      moneyEarned = mobNumber * 20;
      running = false;
      //
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      TestGameIdea testGame = new TestGameIdea();
      mobNumber++;
      MyGame game = new MyGame(moneyEarned, mobNumber);
      //
    }
    //
    if (playerHealth < 1) {
      moneyEarned = waveNoAndMobNumber * 20;
      running = false;
      //
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      //
      TestGameIdea testGame = new TestGameIdea();
      MyGame game = new MyGame(moneyEarned, mobNumber);

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
    screen.update(map, camera, pixels); //
    camera.update(map, camera, haveShootAlreadyInLast10Sec, monsterAlive, monsterHealth, timePassed);//
    Progress progress = new Progress();
    progress.update(playerHealth, monsterHealth, timePassed, haveShootAlreadyInLast10Sec, camera, waveNoAndMobNumber);
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
          System.out.println(map[1][1]);
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
    if (distance < 4) {
      //
      monsterShoot = true;
      int random = (int) (Math.random() * 180);
      //
      if (random == 0) {
        playerHealth = playerHealth - 10;
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
        int moveRandom = (int) (Math.random() * 3);
        if (moveRandom == 0) {
          teleportMonster();
        } else {
          moveMonster();
        }
      }
    }
  }

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

  public static void main(String[] args) {
    TestGameIdea game = new TestGameIdea();
  }
}