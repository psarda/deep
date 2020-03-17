package me.deep.app.test;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DeepBasicInputField {
  private String defaultTitle = " ";
  private JFrame frame;
  private JTextField userInputArea;

  public DeepBasicInputField(String title) {
    defaultTitle = title;
    createCustomInputField();
    return;
  }

  public String getUserInputtedValue() {
    String userInputtedValue = null;
    userInputtedValue = userInputArea.getText();
    frame.setVisible(false);
    return userInputtedValue;
  }

  private JFrame createCustomInputField() {
    frame = new JFrame();
    userInputArea = new JTextField(" ", 20);
    userInputArea.setSize(150, 100);
    JLabel label = new JLabel(defaultTitle);
    frame.add(label);
    frame.add(userInputArea);
    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setVisible(true);
    return frame;
  }

  public static void main(String[] args) {
    DeepBasicInputField test = new DeepBasicInputField(" enter value");
    try {
      Thread.sleep(10000);
    } catch (Exception e) {
      // TODO: handle exception
    }
    String testOutput = test.getUserInputtedValue();
    System.out.println(testOutput);
  }

}
