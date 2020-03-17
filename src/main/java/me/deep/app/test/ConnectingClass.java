package me.deep.app.test;

import java.applet.Applet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConnectingClass extends Applet {

  public static void main(String[] args) {

  }

  public void start(String USERNAME, JFrame frame) {
    System.out.println(USERNAME);
    String UserInput = JOptionPane.showInputDialog(frame,
            "Enter cal for calculator \n Enter chat for chat application \n enter game for game");
    String UserInputNoCaps = UserInput.toLowerCase();
    if (UserInputNoCaps.equals("cal")) {
      Calculator.main(null);
    } else if (UserInputNoCaps.equals("chat")) {
      new SimpleChatClient().start(USERNAME);
    } else if (UserInputNoCaps.equals("game")) {
      new TestGameIdea().start(USERNAME);
    } else {
      start(USERNAME, frame);
    }
  }

}
