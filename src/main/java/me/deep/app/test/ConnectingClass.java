package me.deep.app.test;

import java.io.IOException;

import javax.swing.JOptionPane;

public class ConnectingClass {

  public static void main(String[] args) throws ClassNotFoundException, IOException {
    String UserInput = JOptionPane
            .showInputDialog("Enter cal for calculator \n Enter crypt for crypting and decrypting your information");
    String UserInputNoCaps = UserInput.toLowerCase();
    if (UserInputNoCaps.equals("cal")) {
      Calculator.main(null);
    } else if (UserInputNoCaps.equals("crypt")) {
      crypterstart.main(null);
    }
  }

}
