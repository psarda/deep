package me.deep.app.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;

public class TestGameIdea extends JFrame implements Runnable {

  private static final long serialVersionUID = 1L;
  public int mapWidth = 15;
  public int mapHeight = 15;
  public int monsterHealth = 100;
  public boolean monsterAlive = true;
  private Thread thread;
  private boolean running;
  private BufferedImage image;
  public int[] pixels;
  public ArrayList<Texture> textures;
  public Camera camera;
  public Screen screen;
  public Fire fire;
  public int width, height;
  public boolean haveShootAlreadyInLast10Sec = false;
  public int timePassed = 0;
  public static int[][] map = { //
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 2, 0, 1, 1, 1, 0, 1, 1, 1 }, //
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

  public TestGameIdea() {
    thread = new Thread(this);
    image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    textures = new ArrayList<Texture>();
    textures.add(Texture.wood);
    textures.add(Texture.brick);
    textures.add(Texture.bluestone);
    textures.add(Texture.stone);
    camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
    screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
    fire = new Fire(map, 640, 480, monsterAlive, monsterHealth, camera, haveShootAlreadyInLast10Sec, timePassed);
    addKeyListener(camera);
    addKeyListener(fire);
    setSize(640, 480);
    setResizable(false);
    setTitle("3D Engine");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLocationRelativeTo(null);
    setVisible(true);
    start();
  }

  public TestGameIdea(int[][] m, int w, int h, boolean ma, int mo, Camera c, boolean a, int t) {
    map = m;
    width = w;
    height = h;
    camera = c;
    monsterAlive = ma;
    monsterHealth = mo;
    haveShootAlreadyInLast10Sec = a;
    timePassed = t;
    fire = new Fire(map, 640, 480, monsterAlive, monsterHealth, camera, haveShootAlreadyInLast10Sec, timePassed);
  }

  private synchronized void start() {
    running = true;
    thread.start();
  }

  public synchronized void stop() {
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    bs.show();
  }

  public void run() {
    long lastTime = System.nanoTime();
    final double ns = 1000000000.0 / 60.0;// 60 times per second
    double delta = 0;
    requestFocus();
    while (running) {
      long now = System.nanoTime();
      delta = delta + ((now - lastTime) / ns);
      lastTime = now;
      while (delta >= 1)// Make sure update is only happening 60 times a second
      {
        if (haveShootAlreadyInLast10Sec == true) {
          if (timePassed > 599) {
            haveShootAlreadyInLast10Sec = false;
            timePassed = 0;
          } else {
            timePassed++;
          }
        }
        // handles all of the logic restricted time
        fire = new Fire(map, 640, 480, monsterAlive, monsterHealth, camera, haveShootAlreadyInLast10Sec, timePassed);
        screen.update(camera, pixels);
        camera.update(map);

        delta--;
      }
      render();// displays to the screen unrestricted time
    }
  }

  public static void main(String[] args) {
    TestGameIdea game = new TestGameIdea();
  }
}