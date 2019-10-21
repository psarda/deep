package me.deep.app.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class Welcome implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String FILENAME = "Welcome.txt";
  private static boolean firstLogin;

  public Welcome() {

  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    File tempFile = new File(FILENAME);
    boolean exist = tempFile.exists();
    if (exist == false) {
      Welcome welcome = new Welcome();
      JOptionPane.showMessageDialog(null, "Welcome User");
      store(welcome);
    } else {
      JOptionPane.showMessageDialog(null, "Welcome  back User");
      Date.main(null);
    }
    ConnectingClass.main(null);
  }

  private static void store(Welcome welcome) throws IOException {
    // Saving of object in a file
    FileOutputStream file = new FileOutputStream(FILENAME);
    ObjectOutputStream out = new ObjectOutputStream(file);

    // Method for serialization of object
    out.writeObject(welcome);

    out.close();
    file.close();

  }

}
