package me.deep.app.test;

import java.io.IOException;

import javax.swing.JOptionPane;

public class crypterstart {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    String i = JOptionPane.showInputDialog("If you want to code it then enter 1 else to uncode it press 0 ");

    int x = Integer.parseInt(i);
    if (x == 1) {
      Code.main(null);
    } else {
      Uncode.main(null);
    }

  }
}
