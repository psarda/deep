package me.deep.app.test;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Uncode implements Serializable {

  private String history = "";

  public Uncode() {
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // out.println(" have you done the setup then enter 1 else 0");
    File tempFile = new File("c:/temp/temp.txt");
    boolean exists = tempFile.exists();
    if (exists == false) {
      Uncode uncode;
      uncode = new Uncode();
      uncode.history = (" Your history is ");
      FileOutputStream file = new FileOutputStream(filename);
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(uncode);

      out.close();
      file.close();
    }
    Uncode uncode = read();
    String h = readStringFromUserAndDecrypt();
    uncode.history = uncode.history + "\n" + h;

    store(uncode);
    askAgain(uncode);
  }

  private static String readStringFromUserAndDecrypt() throws IOException {
    String s = JOptionPane.showInputDialog("enter the text. \n");
    s = s.toUpperCase();
    return decryptTheString(s);
  }

  private static String decryptTheString(String s) throws IOException {
    String h = "";
    int x = 0;
    while (x < s.length()) {
      char c = s.charAt(x);
      h = h + decryptTheChar(c);
      x++;
    }
    JOptionPane.showMessageDialog(null, h);
    return h;
  }

  private static char decryptTheChar(char c) throws IOException {
    if (c == 'M') {
      return 'A';
    } else if (c == 'N') {
      return 'B';
    } else if (c == 'B') {
      return 'C';
    } else if (c == 'V') {
      return 'D';
    } else if (c == 'C') {
      return 'E';
    } else if (c == 'X') {
      return 'F';
    } else if (c == 'Z') {
      return 'G';
    } else if (c == 'A') {
      return 'H';
    } else if (c == 'S') {
      return 'I';
    } else if (c == 'D') {
      return 'J';
    } else if (c == 'F') {
      return 'K';
    } else if (c == 'G') {
      return 'L';
    } else if (c == 'H') {
      return 'M';
    } else if (c == 'J') {
      return 'N';
    } else if (c == 'K') {
      return 'O';
    } else if (c == 'L') {
      return 'P';
    } else if (c == 'P') {
      return 'Q';
    } else if (c == 'O') {
      return 'R';
    } else if (c == 'I') {
      return 'S';
    } else if (c == 'U') {
      return 'T';
    } else if (c == 'Y') {
      return 'U';
    } else if (c == 'T') {
      return 'V';
    } else if (c == 'R') {
      return 'W';
    } else if (c == 'E') {
      return 'X';
    } else if (c == 'W') {
      return 'Y';
    } else if (c == 'Q') {
      return 'Z';
    } else if (c == '-') {
      return '1';
    } else if (c == ':') {
      return '2';
    } else if (c == '[') {
      return '3';
    } else if (c == '?') {
      return '4';
    } else if (c == '|') {
      return '5';
    } else if (c == ';') {
      return '6';
    } else if (c == '<') {
      return '7';
    } else if (c == ',') {
      return '8';
    } else if (c == '.') {
      return '9';
    } else if (c == '>') {
      return '0';
    } else {
      return ' ';
    }
  }

  private static void askAgain(Uncode uncode) throws IOException, ClassNotFoundException {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    String i = JOptionPane.showInputDialog(
            " \n enter 1 on if you want to go back to the crypter or \n 0 if you want to end it or \n  2 to go back to main menu  \n if you want to see history then enter 3");
    int x = Integer.parseInt(i);
    if (x == 0) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    } else if (x == 1) {
      main(null);
    } else if (x == 2) {
      crypterstart.main(null);
    } else if (x == 3) {
      SeeHistory(uncode);
    } else {
      askAgain(uncode);
    }
  }

  static String filename = "uncode.txt";

  private static void store(Uncode uncode) throws IOException {
    // Saving of object in a file
    FileOutputStream file = new FileOutputStream(filename);
    ObjectOutputStream out = new ObjectOutputStream(file);

    // Method for serialization of object
    out.writeObject(uncode);

    out.close();
    file.close();

  }

  private static Uncode read() throws IOException, ClassNotFoundException, EOFException {
    // Saving of object in a file
    FileInputStream file = new FileInputStream(filename);
    ObjectInputStream in = new ObjectInputStream(file);

    // Method for serialization of object
    Uncode uncode = (Uncode) in.readObject();

    in.close();
    file.close();

    return uncode;
  }

  private static void SeeHistory(Uncode uncode) throws IOException, ClassNotFoundException {
    JOptionPane.showMessageDialog(null, "history is " + uncode.history);
    askAgain(uncode);
  }
}
