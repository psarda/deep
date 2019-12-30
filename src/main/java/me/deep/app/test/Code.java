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
public class Code implements Serializable {
  private static final String FILENAME = "code.txt";

  private String history = "";

  public Code() {
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException, EOFException {
    File tempFile = new File(FILENAME);
    boolean exists = tempFile.exists();
    if (exists == false) {
      Code code = new Code();
      code.history = " Your history is ";
      FileOutputStream file = new FileOutputStream(FILENAME);
      ObjectOutputStream out = new ObjectOutputStream(file);

      // Method for serialization of object
      out.writeObject(code);

      out.close();
      file.close();
    }

    Code Code = readTheFile();
    String his = readStringFromUserAndCrypt();
    Code.history = Code.history + "\n" + his;

    store(Code);
    askAgain(Code);
  }

  private static String readStringFromUserAndCrypt() throws IOException {
    String s = JOptionPane.showInputDialog("enter the text. \n");

    s = s.toUpperCase();
    return CryptTheString(s);
  }

  private static String CryptTheString(String s) throws IOException {
    String h = "";
    int x = 0;
    while (x < s.length()) {
      char c = s.charAt(x);
      h = h + cryptTheChar(c);
      x++;
    }
    JOptionPane.showMessageDialog(null, h);
    return h;
  }

  private static char cryptTheChar(char c) throws IOException {
    if (c == 'A') {
      return 'M';
    } else if (c == 'B') {
      return 'N';
    } else if (c == 'C') {
      return 'B';
    } else if (c == 'D') {
      return 'V';
    } else if (c == 'E') {
      return 'C';
    } else if (c == 'F') {
      return 'X';
    } else if (c == 'G') {
      return 'Z';
    } else if (c == 'H') {
      return 'A';
    } else if (c == 'I') {
      return 'S';
    } else if (c == 'J') {
      return 'D';
    } else if (c == 'K') {
      return 'F';
    } else if (c == 'L') {
      return 'G';
    } else if (c == 'M') {
      return 'H';
    } else if (c == 'N') {
      return 'J';
    } else if (c == 'O') {
      return 'K';
    } else if (c == 'P') {
      return 'L';
    } else if (c == 'Q') {
      return 'P';
    } else if (c == 'R') {
      return 'O';
    } else if (c == 'S') {
      return 'I';
    } else if (c == 'T') {
      return 'U';
    } else if (c == 'U') {
      return 'Y';
    } else if (c == 'V') {
      return 'T';
    } else if (c == 'W') {
      return 'R';
    } else if (c == 'X') {
      return 'E';
    } else if (c == 'Y') {
      return 'W';
    } else if (c == 'Z') {
      return 'Q';
    } else if (c == '1') {
      return '-';
    } else if (c == '2') {
      return ':';
    } else if (c == '3') {
      return '[';
    } else if (c == '4') {
      return '?';
    } else if (c == '5') {
      return '|';
    } else if (c == '6') {
      return ';';
    } else if (c == '7') {
      return '<';
    } else if (c == '8') {
      return ',';
    } else if (c == '9') {
      return '.';
    } else if (c == '0') {
      return '>';
    } else {
      return c;
    }
  }

  private static void askAgain(Code Code) throws IOException, ClassNotFoundException {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    String i = JOptionPane.showInputDialog(
            " \n enter 1 on if you want to go back to the crypter or \n 0 if you want to end it or \n  2 to go back to main menu   \n if you want to see history then enter 3");
    int x = Integer.parseInt(i);
    if (x == 0) {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    } else if (x == 1) {
      main(null);
    }

    else if (x == 2) {
      crypterstart.main(null);
    } else if (x == 3) {
      SeeHistory(Code);
    } else {
      askAgain(Code);
    }
  }

  private static void store(Code Code) throws IOException {
    // Saving of object in a file
    FileOutputStream file = new FileOutputStream(FILENAME);
    ObjectOutputStream out = new ObjectOutputStream(file);

    // Method for serialization of object
    out.writeObject(Code);

    out.close();
    file.close();
  }

  private static Code readTheFile() throws IOException, ClassNotFoundException, EOFException {
    FileInputStream file = new FileInputStream(FILENAME);
    ObjectInputStream in = new ObjectInputStream(file);
    Code Code = (Code) in.readObject();

    in.close();
    file.close();
    return Code;
  }

  private static void SeeHistory(Code Code) throws IOException, ClassNotFoundException {
    JOptionPane.showMessageDialog(null, "history is " + Code.history);
    askAgain(Code);
  }

}
