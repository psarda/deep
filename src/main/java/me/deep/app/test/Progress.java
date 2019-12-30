package me.deep.app.test;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Progress extends JFrame {
  private static final long serialVersionUID = 1L;
  public static int playerHealth, mobMax, monsterHealth, timepassed = 0;
  public static boolean haveShootAlreadyInLast10Sec, coolDownStarted = false;
  public static JProgressBar playerHealthPB;
  public static JProgressBar monsterHealthPB;
  public static JProgressBar gunCoolDownTimer;
  public static JLabel coordinates;

  public Progress(int p, int m, int mo, int t) {
    playerHealth = p;
    mobMax = m;
    monsterHealth = mo;
    t = timepassed;

    createProgressBar();
  }

  public Progress() {

  }

  public void createProgressBar() {
    JFrame frame = new JFrame();
    String coordinatesString = "X pos:- 4.5 + \n Y pos:- 4.5";
    JPanel coordinatesPanel = new JPanel();
    coordinates = new JLabel(coordinatesString);
    coordinatesPanel.add(coordinates);
    frame.setSize(680, 480);
    monsterHealthPB = new JProgressBar();
    monsterHealthPB.setString("monster health:-" + monsterHealth + "/" + mobMax);
    monsterHealthPB.setStringPainted(true);
    monsterHealthPB.setValue(monsterHealth);
    monsterHealthPB.setMaximum(mobMax);
    playerHealthPB = new JProgressBar();
    playerHealthPB.setString("your health:-" + playerHealth + "/" + mobMax);
    playerHealthPB.setStringPainted(true);
    playerHealthPB.setValue(playerHealth);
    playerHealthPB.setMaximum(mobMax);
    gunCoolDownTimer = new JProgressBar();
    gunCoolDownTimer.setStringPainted(true);
    gunCoolDownTimer.setString(" Gun Cool down timer:- CoolDown Complete");
    gunCoolDownTimer.setValue(10);
    playerHealthPB.setMaximum(10);
    frame.add(BorderLayout.BEFORE_FIRST_LINE, monsterHealthPB);
    frame.add(BorderLayout.CENTER, playerHealthPB);
    frame.add(BorderLayout.SOUTH, gunCoolDownTimer);
    frame.add(BorderLayout.EAST, coordinates);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  public void update(int p, int mo, int t, boolean b, Camera cam) {
    changeProgressBarValue(p, mo, t, b, cam);
  }

  private static void changeProgressBarValue(int p, int mo, int t, boolean b, Camera cam) {
    String coordinatesString = "X pos:-" + cam.xPos + "\n Y pos:-" + cam.yPos;
    coordinates.setText(coordinatesString);
    int timePassedInSec = t / 60;
    if (p != playerHealth) {
      playerHealthPB.setVisible(false);
      playerHealthPB.setString("your health:-" + p + "/" + mobMax);
      playerHealthPB.setStringPainted(true);
      playerHealthPB.setValue(p);
      playerHealthPB.setVisible(true);
    }
    if (mo != monsterHealth) {
      monsterHealthPB.setVisible(false);
      monsterHealthPB.setString("monster health:-" + mo + "/" + mobMax);
      monsterHealthPB.setStringPainted(true);
      monsterHealthPB.setValue(mo);
      monsterHealthPB.setVisible(true);
    }
    if (b != haveShootAlreadyInLast10Sec) {
      haveShootAlreadyInLast10Sec = b;
      if (b == true) {
        gunCoolDownTimer.setVisible(false);
        gunCoolDownTimer.setString(" Gun Cool down timer:- " + timePassedInSec + "/ 10");
        gunCoolDownTimer.setStringPainted(true);
        gunCoolDownTimer.setValue(timePassedInSec);
        gunCoolDownTimer.setVisible(true);
      } else if (b == false) {
        gunCoolDownTimer.setVisible(false);
        gunCoolDownTimer.setString(" Gun Cool down timer:- Cool Down Complete");
        gunCoolDownTimer.setStringPainted(true);
        gunCoolDownTimer.setValue(10);
        gunCoolDownTimer.setVisible(true);
      }

    } else if (b == haveShootAlreadyInLast10Sec) {
      if (b == true) {
        gunCoolDownTimer.setVisible(false);
        gunCoolDownTimer.setString(" Gun Cool down timer:- " + timePassedInSec + "/ 10");
        gunCoolDownTimer.setStringPainted(true);
        gunCoolDownTimer.setValue(timePassedInSec);
        gunCoolDownTimer.setVisible(true);
      }
    }
  }

}
