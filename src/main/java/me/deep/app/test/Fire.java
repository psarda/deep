package me.deep.app.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Fire implements KeyListener {
  public TestGameIdea testGameIdea;
  public int[][] map;
  public int width, height;
  public boolean fire;
  public Camera camera;
  int timePassed;
  boolean haveShootAlreadyInLast10Sec;
  boolean monsterAlive;
  int monsterHealth;

  public Fire(int[][] m, int w, int h, boolean ma, int mo, Camera c, boolean a, int t) {
    map = m;
    width = w;
    height = h;
    camera = c;
    monsterAlive = ma;
    monsterHealth = mo;
    haveShootAlreadyInLast10Sec = a;
    timePassed = t;
  }

  public static class PairOfIntAndBoolean {
    public final int i;
    public final int i1;
    public final boolean b;
    public final boolean b1;

    public PairOfIntAndBoolean(int i, boolean b, boolean b1, int i1) {
      this.i = i;
      this.b = b;
      this.b1 = b1;
      this.i1 = i1;
    }

  }

  public void update(int[][] map, int w, int h, boolean monsterAlive, int monsterHealth, Camera camera,
          boolean haveShootAlreadyInLast10Sec, int timePassed) {

    fire = false;
    if (haveShootAlreadyInLast10Sec == false) {
      timePassed = 0;
      haveShootAlreadyInLast10Sec = true;
      System.out.println("working fine");
      for (int x = 0; x < width; x = x + 1) {
        double cameraX = 2 * x / (double) (width) - 1;
        double rayDirX = camera.xDir + camera.xPlane * cameraX;
        double rayDirY = camera.yDir + camera.yPlane * cameraX;
        // Map position
        int mapX = (int) camera.xPos;
        int mapY = (int) camera.yPos;
        // length of ray from current position to next x or y-side
        double sideDistX;
        double sideDistY;
        // Length of ray from one side to next in map
        double deltaDistX = Math.sqrt(1 + (rayDirY * rayDirY) / (rayDirX * rayDirX));
        double deltaDistY = Math.sqrt(1 + (rayDirX * rayDirX) / (rayDirY * rayDirY));
        double perpWallDist;
        // Direction to go in x and y
        int stepX, stepY;
        boolean hit = false;// was a wall hit
        int side = 0;// was the wall vertical or horizontal
        // Figure out the step direction and initial distance to a side

        if (rayDirX < 0) {
          stepX = -1;
          sideDistX = (camera.xPos - mapX) * deltaDistX;
        } else {
          stepX = 1;
          sideDistX = (mapX + 1.0 - camera.xPos) * deltaDistX;
        }
        if (rayDirY < 0) {
          stepY = -1;
          sideDistY = (camera.yPos - mapY) * deltaDistY;
        } else {
          stepY = 1;
          sideDistY = (mapY + 1.0 - camera.yPos) * deltaDistY;
        }
        // Loop to find where the ray hits a wall
        while (!hit) {
          // Jump to next square
          if (sideDistX < sideDistY) {
            sideDistX += deltaDistX;
            mapX += stepX;
            side = 0;
          } else {
            sideDistY += deltaDistY;
            mapY += stepY;
            side = 1;
          }
          // Check if ray has hit a wall

          if (map[mapX][mapY] > 0) {
            hit = true;
          }

          System.out.println(map[mapX][mapY]);

        }
        if (map[mapX][mapY] == 2) {
          haveShootAlreadyInLast10Sec = true;
          Random rand = new Random();
          int isMonsterThereChance = rand.nextInt(10);
          if (isMonsterThereChance < 1) {
            int didYouHitTheMonsterChance = rand.nextInt(10);
            if (didYouHitTheMonsterChance < 1) {
              timePassed = 0;
              monsterHealth = monsterHealth - 50;
              if (monsterHealth < 1) {
                monsterAlive = false;
              }
            } else {
              System.out.println("You missed but monster is here");
            }
          } else {
            System.out.println(" monster is not here");

          }
        }
      }
    }

    System.out.println(haveShootAlreadyInLast10Sec);
  }

  public static void main(String[] args) {
  }

  public void keyTyped(KeyEvent key) {

  }

  public void keyPressed(KeyEvent key) {
    if ((key.getKeyCode() == KeyEvent.VK_ENTER)) {
      System.out.println("key pressed");
      if (haveShootAlreadyInLast10Sec == false) {
        fire = true;
        update(map, 640, 480, monsterAlive, monsterHealth, camera, haveShootAlreadyInLast10Sec, timePassed);
        testGameIdea = new TestGameIdea(map, 640, 480, monsterAlive, monsterHealth, camera, haveShootAlreadyInLast10Sec,
                timePassed);
      }
    }
  }

  public void keyReleased(KeyEvent key) {
    //
  }

}
