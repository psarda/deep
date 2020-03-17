package me.deep.app.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;

public class Camera implements KeyListener {
  public double xPos, yPos, xDir, yDir, xPlane, yPlane;
  public boolean leftBoolean, rightBoolean, forwardBoolean, backboolean;
  public final double MOVE_SPEED = 0.08;
  public final double ROTATION_SPEED = .045;
  public int width = 640;
  public int[][] map;
  public TestGameIdea testGameIdea;
  public boolean fire = false;
  public int timePassed = 0;
  static String fileName;
  static KeyEvent keyEvent;
  boolean keyPressed = false;
  static int forward;
  static int back;
  static int left;
  static int right;
  static int shoot;

  public Camera(double x, double y, double xd, double yd, double xp, double yp, String s) {
    xPos = x;
    yPos = y;
    xDir = xd;
    yDir = yd;
    xPlane = xp;
    yPlane = yp;
    fileName = s;
  }

  public void keyPressed(KeyEvent key) {
    keyEvent = key;
    keyPressed = true;

  }

  public void keyReleased(KeyEvent key) {
    keyPressed = false;
  }

  public void update(int[][] map, Camera cam, boolean haveShootAlreadyInLast10Sec, boolean monsterAlive,
          int monsterHealth, int timePassed) {//
    if (keyPressed) {
      try {
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String fileText = bufferedReader.readLine();
        String[] keyBind = fileText.split("/");
        for (int i = 0; i < 5; i++) {
          if (i == 0) {
            forward = Integer.parseInt(keyBind[i]);
          } else if (i == 1) {
            back = Integer.parseInt(keyBind[i]);
          } else if (i == 2) {
            right = Integer.parseInt(keyBind[i]);
          } else if (i == 3) {
            left = Integer.parseInt(keyBind[i]);
          } else if (i == 4) {
            shoot = Integer.parseInt(keyBind[i]);
          }
        }

      } catch (Exception e) {
        // TODO: handle exception
      }
      if ((keyEvent.getKeyCode() == left)) {
        leftBoolean = true;
      }
      if ((keyEvent.getKeyCode() == right)) {
        rightBoolean = true;
      }
      if ((keyEvent.getKeyCode() == forward)) {
        forwardBoolean = true;
      }
      if ((keyEvent.getKeyCode() == back)) {
        backboolean = true;
      }
      if ((keyEvent.getKeyCode() == shoot)) {
        System.out.println("key pressed");
        fire = true;
      }

      if (forwardBoolean) {
        if (map[(int) (xPos + xDir * MOVE_SPEED)][(int) yPos] == 0) {
          xPos += xDir * MOVE_SPEED;
        }
        if (map[(int) xPos][(int) (yPos + yDir * MOVE_SPEED)] == 0) {
          yPos += yDir * MOVE_SPEED;
        }
      }
      if (backboolean) {
        if (map[(int) (xPos - xDir * MOVE_SPEED)][(int) yPos] == 0) {
          xPos -= xDir * MOVE_SPEED;
        }
        if (map[(int) xPos][(int) (yPos - yDir * MOVE_SPEED)] == 0) {
          yPos -= yDir * MOVE_SPEED;
        }
      }
      if (rightBoolean) {
        double oldxDir = xDir;
        xDir = xDir * Math.cos(-ROTATION_SPEED) - yDir * Math.sin(-ROTATION_SPEED);
        yDir = oldxDir * Math.sin(-ROTATION_SPEED) + yDir * Math.cos(-ROTATION_SPEED);
        double oldxPlane = xPlane;
        xPlane = xPlane * Math.cos(-ROTATION_SPEED) - yPlane * Math.sin(-ROTATION_SPEED);
        yPlane = oldxPlane * Math.sin(-ROTATION_SPEED) + yPlane * Math.cos(-ROTATION_SPEED);
      }
      if (leftBoolean) {
        double oldxDir = xDir;
        xDir = xDir * Math.cos(ROTATION_SPEED) - yDir * Math.sin(ROTATION_SPEED);
        yDir = oldxDir * Math.sin(ROTATION_SPEED) + yDir * Math.cos(ROTATION_SPEED);
        double oldxPlane = xPlane;
        xPlane = xPlane * Math.cos(ROTATION_SPEED) - yPlane * Math.sin(ROTATION_SPEED);
        yPlane = oldxPlane * Math.sin(ROTATION_SPEED) + yPlane * Math.cos(ROTATION_SPEED);
      }
      // System.out.println(monsterAlive + " " + monsterHealth + " " +
      // haveShootAlreadyInLast10Sec + " " + timePassed);
      // System.out.println(cam.xPlane + " " + cam.yPlane + " " + cam.xDir + " " +
      // cam.yDir + " " + cam.xPos + " " + cam.yPos);//
      if (fire == true) {
        int mapX = 0;
        int mapY = 0;
        int MAPX = 0;
        int MAPY = 0;
        try {
          if (haveShootAlreadyInLast10Sec == false) {
            fire = false;
            timePassed = 0;
            haveShootAlreadyInLast10Sec = true;
            // System.out.println("working fine");
            for (int i = 0; i < 10; i++) {
              for (int x = 0; x < width; x = x + 1) {
                double cameraX = 2 * x / (double) (width) - 1;
                double rayDirX = xDir + xPlane * cameraX;
                double rayDirY = cam.yDir + yPlane * cameraX;
                // Map position
                // System.out.println("working finex2");
                mapX = (int) cam.xPos;
                mapY = (int) cam.yPos;
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
                  sideDistX = (cam.xPos - mapX) * deltaDistX;
                } else {
                  stepX = 1;
                  sideDistX = (mapX + 1.0 - cam.xPos) * deltaDistX;
                }
                if (rayDirY < 0) {
                  stepY = -1;
                  sideDistY = (cam.yPos - mapY) * deltaDistY;
                } else {
                  stepY = 1;
                  sideDistY = (mapY + 1.0 - cam.yPos) * deltaDistY;
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
                }
                if (map[mapX][mapY] == 2) {
                  MAPX = mapX;
                  MAPY = mapY;
                  break;
                }
              }
            }
            if (map[MAPX][MAPY] == 2) {
              monsterHealth = monsterHealth - 50;
              if (monsterHealth < 1) {
                monsterAlive = false;
                System.out.println("You killed it");
                System.out.println(monsterHealth);
              }
            }

          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    testGameIdea = new TestGameIdea(map, monsterAlive, monsterHealth, haveShootAlreadyInLast10Sec, timePassed);
    // System.out.println(haveShootAlreadyInLast10Sec);
  }

  public void keyTyped(KeyEvent arg0) {

  }
}
