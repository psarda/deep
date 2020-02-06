package me.deep.app.test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("deprecation")
public class Welcome {

  private static final long serialVersionUID = 1L;
  private static String FILENAME = "Welcome.txt";
  private static final String FILENAME2 = "users/ALLtheUSERS.txt";
  private static String USERNAME;

  public Welcome() {

  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    File file = new File("users");
    if (!file.exists()) {
      if (file.mkdir()) {
        System.out.println("Directory is created!");
      } else {
        System.out.println("Failed to create directory!");
      }
    }
    file = new File(FILENAME2);
    if (!file.exists()) {
      FileWriter writer = new FileWriter(FILENAME2);
      writer.write(" ");
      writer.close();
    }
    startProgram();
  }

  private static void startProgram() {
    final JFrame frame = new JFrame();
    JButton newUserbutton = new JButton("create account");
    JButton loginUserButton = new JButton("sign in");
    newUserbutton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        createNewUser();
      }
    });
    loginUserButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        loginUser();
      }
    });
    frame.getContentPane().add(loginUserButton, BorderLayout.CENTER);
    frame.getContentPane().add(newUserbutton, BorderLayout.SOUTH);
    frame.setSize(300, 150);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  static JFrame frame;

  private static void createNewUser() {
    frame = new JFrame();
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    final JTextField usernameTextField = new JTextField("enter username here and enter password down");
    final JPasswordField passwordTextFeild = new JPasswordField(" ");
    JButton createButton = new JButton("create");
    createButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        String userName = usernameTextField.getText();
        String password = passwordTextFeild.getText();
        System.out.println(password);
        String userInfo = userName + "~" + password;
        FILENAME = "users/" + userName + ".txt";
        File file = new File(FILENAME);
        boolean exists = file.exists();
        if (exists) {
          JOptionPane.showMessageDialog(frame, " Opps! It looks like there is already a user with that name.");
          createNewUser();
        } else {

          File createFolder = new File("users/" + userName);
          createFolder.mkdir();
          try {
            FileReader usersFileReader = new FileReader(FILENAME2);
            BufferedReader inputReader = new BufferedReader(usersFileReader);
            String input = inputReader.readLine();
            if (input == null) {
              input = " ";
            }
            FileWriter writer1 = new FileWriter(FILENAME2);
            String allPeople = input + "~" + userName;
            writer1.write(allPeople);
            writer1.close();
            FileWriter writer = new FileWriter(FILENAME);
            writer.write(userInfo);
            writer.close();

          } catch (Exception e1) {
            // TODO: handle exception
          }
        }
        ConnectingClass connect = new ConnectingClass();
        connect.start(userName, frame);
      }
    });
    panel.add(usernameTextField);
    panel.add(passwordTextFeild);
    panel.add(createButton);
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setSize(500, 150);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  static File tempFile;

  private static void loginUser() {
    String userName = JOptionPane.showInputDialog(frame, " enter username");
    FILENAME = "users/" + userName + ".txt";
    USERNAME = userName;
    tempFile = new File(FILENAME);
    boolean exist = tempFile.exists();
    if (exist == false) {
      JOptionPane.showMessageDialog(frame, " Error:- You have not created an account. ");
      startProgram();
    } else {
      try {
        checkPassword();
      } catch (Exception e) {
        // TODO: handle exception
      }
    }

  }

  private static void checkPassword() {
    try {
      FileReader file = new FileReader(tempFile);

      BufferedReader inputReader = new BufferedReader(file);
      String input = inputReader.readLine();
      String[] userInfo = input.split("~");
      String password = userInfo[1];
      String userInputedString = JOptionPane.showInputDialog(frame, "Enter password ");
      if (userInputedString.equals(password)) {
        new ConnectingClass().start(USERNAME, frame);
      } else {
        JOptionPane.showMessageDialog(frame, "Invalid password");
        checkPassword();
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}
