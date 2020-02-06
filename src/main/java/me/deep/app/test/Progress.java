package me.deep.app.test;

import javax.swing.JFrame;

public class Progress extends JFrame {
  private static final long serialVersionUID = 1L;
  public static int playerHealth, mobMax, monsterHealth, timepassed = 0;
  public static boolean haveShootAlreadyInLast10Sec, coolDownStarted = false;
  static JFrame frame;

  public Progress(int p, int m, int mo, int t, int k) {
    playerHealth = p;
    mobMax = m;
    monsterHealth = mo;
    t = timepassed;

    createProgressBar(k);
  }

  public Progress() {

  }

  public void createProgressBar(int k) {
    frame = new JFrame();

  }

  public void update(int p, int mo, int t, boolean b, Camera cam, int k) {
    changeProgressBarValue(p, mo, t, b, cam, k);
  }

  private static void changeProgressBarValue(int p, int mo, int t, boolean b, Camera cam, int k) {

  }

}
