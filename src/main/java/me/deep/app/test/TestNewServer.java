package me.deep.app.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class TestNewServer implements Serializable {

  private static final long serialVersionUID = 1L;
  private static final String FILENAME = "serverChat.txt";
  static String allMessages;
  ArrayList clientOutputStreams;

  static String message;

  public class ClientHandlerTest implements Runnable {
    BufferedReader reader;
    Socket sock;

    public ClientHandlerTest(Socket clientSocket) {
      try {
        sock = clientSocket;
        InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(isReader);

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    public ClientHandlerTest() {

    }

    public void run() {
      String input = "";
      try {
        while ((message = reader.readLine()) != null) {
          System.out.println("read " + message);
          try {

            TestNewServer test = new TestNewServer();
            FileInputStream file = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for serialization of object
            test = (TestNewServer) in.readObject();

            in.close();
            file.close();
            input = test.allMessages;
            System.out.println(input + "\n" + message);
          } catch (Exception e) {

          }
          try {
            TestNewServer test1 = new TestNewServer();
            test1.allMessages = input + " \n " + message;
            FileOutputStream file1 = new FileOutputStream(FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(file1);
            // Method for serialization of object
            out.writeObject(test1);

            out.close();
            file1.close();

            System.out.println("Storage sucessful");

          } catch (Exception e) {
            System.out.println("ERROR");
          }
          tellEveryone(message);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    TestNewServer test = new TestNewServer();
    test.allMessages = " ";
    FileOutputStream file = new FileOutputStream(FILENAME);
    ObjectOutputStream out = new ObjectOutputStream(file);
    // Method for serialization of object
    out.writeObject(test);

    out.close();
    file.close();
    new TestNewServer().go();
  }

  public void go() {
    clientOutputStreams = new ArrayList();
    try {
      ServerSocket serverSock = new ServerSocket(1234);
      while (true) {
        Socket clientSocket = serverSock.accept();
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        clientOutputStreams.add(writer);

        Thread t = new Thread(new ClientHandlerTest(clientSocket));
        t.start();
        TestNewServer test = new TestNewServer();
        FileInputStream file = new FileInputStream(FILENAME);
        ObjectInputStream in = new ObjectInputStream(file);

        // Method for serialization of object
        test = (TestNewServer) in.readObject();

        in.close();
        file.close();

        try {

          writer.println(message);
          writer.flush();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();

    }
  }

  public void tellEveryone(String message) {

    Iterator it = clientOutputStreams.iterator();
    while (it.hasNext()) {
      try {
        PrintWriter writer = (PrintWriter) it.next();
        writer.println(message);
        writer.flush();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}
