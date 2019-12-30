package me.deep.app.test;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Fire implements KeyListener {

  public static class PairOfIntAndBoolean {
    public final int i;
    public final int i1;
    public final boolean b;
    public final boolean b1;

    public PairOfIntAndBoolean(int mo, boolean ma, boolean ha, int a1) throws NullPointerException {
      System.out.println("working finex3");
      this.i = mo;
      this.b = ma;
      this.b1 = ha;
      this.i1 = a1;
    }

  }

  public Fire() {

  }

  public void changeValue(boolean haveShootAlreadyInLast10Sec, int timePassed) {
    haveShootAlreadyInLast10Sec = false;
    timePassed = 0;
    TestGameIdea test = new TestGameIdea(timePassed, haveShootAlreadyInLast10Sec);
  }

  public Fire(int mo, boolean ma, boolean a) {
    TestGameIdea test = new TestGameIdea(ma, mo, a);
  }

  public void keyTyped(KeyEvent key) {

  }

  public void keyPressed(KeyEvent key) {

  }

  public void keyReleased(KeyEvent key) {
    //
  }

}
