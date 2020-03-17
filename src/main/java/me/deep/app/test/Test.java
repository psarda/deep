package me.deep.app.test;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.deep.app.test.TestGameIdea.SettingsSetter;

public class Test {
  public static int[][] map = { //
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 1, 1, 0, 1, 1, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 0, 3, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 2, 4 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };//
  static String cities[] = { "New York", "Lagos", "Bangalore", "Delhi", "San Francisco", "Los Angeles", "Las Vegas",
          "London", "Berlin", "Moscow", "Paris", "Sydney", "Auckland", "Bankok", "Cario", "Barsilla", "Tornoto", "Rome",
          "Warsaw", "Mecca", "Jerusalem", "Beijing", "Shangai", "Hong Kong", "Madrid", "Mexico City", "Singapur",
          "Tokoyo", "Mumbai", "Dubai", "Buenos Aires", "Kuala Lumpur", "Jakarta", "Seattle", "Dallas", "Washington D C",
          "Pune", "Chennai", "Melborne", "Colombo", "Zuric", "Mountain Veiw", "Boston", "Atlanta", "Maimi", "Taipai" };
  static JButton button;
  static JFrame frame;

  public static void main(String[] args) {

    JFrame settingsFrame = new JFrame();
    JPanel mainPanel = new JPanel();
    JPanel forwardPanel = new JPanel();
    JPanel backPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel shootPanel = new JPanel();
    JButton forwardButton = new JButton();
    JButton backButton = new JButton();
    JButton rightButton = new JButton();
    JButton leftButton = new JButton();
    JButton shootButton = new JButton();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    forwardPanel.setLayout(new BoxLayout(forwardPanel, BoxLayout.X_AXIS));
    backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.X_AXIS));
    leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
    shootPanel.setLayout(new BoxLayout(shootPanel, BoxLayout.X_AXIS));

    mainPanel.addKeyListener(new SettingsSetter());
    JLabel forwardLabel = new JLabel("walk front");
    JLabel backLabel = new JLabel("walk back");
    JLabel lookRightLabel = new JLabel("look Right");
    JLabel lookLeftLabel = new JLabel("look Left");
    JLabel shootLabel = new JLabel("shoot gun");
    forwardButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    backButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    leftButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    rightButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    shootButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

      }
    });
    forwardPanel.add(forwardLabel);
    forwardPanel.add(forwardButton);
    backPanel.add(backLabel);
    backPanel.add(backButton);
    rightPanel.add(lookRightLabel);
    rightPanel.add(rightButton);
    leftPanel.add(lookLeftLabel);
    leftPanel.add(leftButton);
    shootPanel.add(shootLabel);
    shootPanel.add(shootButton);
    mainPanel.add(forwardPanel);
    mainPanel.add(backPanel);
    mainPanel.add(rightPanel);
    mainPanel.add(leftPanel);
    mainPanel.add(shootPanel);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    settingsFrame.add(mainPanel);
    settingsFrame.setSize(screenSize);
    settingsFrame.setVisible(true);
  }

  static class updateButton implements Runnable {

    public void run() {
      long lastTime = System.nanoTime();
      final double ns = 1000000000.0 / 60.0;// 60 times per second
      double delta = 0;
      frame.requestFocus();
      //
      while (true) {
        long now = System.nanoTime();
        delta = delta + ((now - lastTime) / ns);
        lastTime = now;
        //
        while (delta >= 1)// Make sure update is only happening 60 times a second
        {
          button.setVisible(false);
          try {
            Thread.sleep(1000);
          } catch (Exception e) {
            // TODO: handle exception
          }
          button.setVisible(true);
          delta--;
        }
      }
    }
  }
}
