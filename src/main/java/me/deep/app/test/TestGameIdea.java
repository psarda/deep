package me.deep.app.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestGameIdea extends JFrame implements Runnable {
  public Camera camera;
  public Screen screen;
  public ArrayList<Texture> textures;

  private static final long serialVersionUID = 1L;
  static JFrame gamePart1Window = new JFrame();
  static JPanel gamePart1Panel = new JPanel();

  private static final String gamePart1ButtonImageDownFileName = "down.png";
  private static final String gamePart1ButtonImageUpFileName = "up.png";
  private static final String gamePart1ButtonImageLeftFileName = "left.png";
  private static final String gamePart1ButtonImageRightFileName = "right.png";
  private static final String gamePart1MapImageFileName = "map.png";

  public int mapWidth = 15;
  public int mapHeight = 15;
  private Thread thread;
  private boolean running;
  BufferedImage image;
  public int[] pixels;
  public static int[][] map = { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 2, 1, 2, 1 }, { 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1 },
          { 1, 0, 1, 2, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1 }, { 1, 0, 2, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
          { 1, 2, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1 },
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 2, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1 },
          { 1, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 1, 0, 1 }, { 1, 0, 0, 1, 2, 0, 1, 0, 0, 1, 0, 0, 0, 2, 1 },
          { 1, 0, 1, 1, 1, 1, 1, 2, 0, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 2, 0, 0, 0, 1 },
          { 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

  public TestGameIdea() {
    camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
    addKeyListener(camera);
    thread = new Thread(this);
    image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    textures = new ArrayList<Texture>();
    textures.add(Texture.wood);
    textures.add(Texture.brick);
    textures.add(Texture.bluestone);
    textures.add(Texture.stone);
    screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
    setSize(640, 480);
    setResizable(false);
    setTitle("3D Engine");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLocationRelativeTo(null);
    setVisible(true);
    start();
  }

  public static void main(String[] args) {
    TestGameIdea testGameIdea;
    testGameIdea = new TestGameIdea();
  }

  private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
    Image img = icon.getImage();
    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
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
        // handles all of the logic restricted time
        screen.update(camera, pixels);
        camera.update(map);
        delta--;
      }
      render();// displays to the screen unrestricted time
    }
  }

}
