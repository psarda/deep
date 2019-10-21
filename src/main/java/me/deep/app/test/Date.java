package me.deep.app.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class Date implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String FILENAME = "date.txt";
  private static LocalDateTime lastLogin;
  private static LocalDateTime firstLogin;

  public Date() {

  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    JOptionPane.showMessageDialog(null, dtf.format(now));
    File tempFile = new File(FILENAME);
    boolean exist = tempFile.exists();

    if (exist == false) {
      Date date = new Date();
      Date.firstLogin = now;
      Date.lastLogin = now;
      FileOutputStream file = new FileOutputStream(FILENAME);
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(date);

      out.close();
      file.close();

    } else {
      Date store = new Date();
      FileInputStream file = new FileInputStream(FILENAME);
      ObjectInputStream in = new ObjectInputStream(file);

      // Method for serialization of object
      store = (Date) in.readObject();

      in.close();
      file.close();
      JOptionPane.showMessageDialog(null, Date.lastLogin);
      Date storeDate = new Date();
      Date.firstLogin = now;
      FileOutputStream file1 = new FileOutputStream(FILENAME);
      ObjectOutputStream out = new ObjectOutputStream(file1);

      // Method for serialization of object
      out.writeObject(storeDate);

      out.close();
      file.close();
    }
    ConnectingClass.main(null);

  }
}
