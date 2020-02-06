package me.deep.app.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class TestNewServer implements Serializable {

  private static final long serialVersionUID = 1L;
  private static String FILENAME = "chat/serverChat.txt";
  ArrayList clientOutputStreams;
  static BufferedReader reader;
  static Socket sock;

  public class ClientHandlerTest implements Runnable {

    public ClientHandlerTest(Socket clientSocket) {
      try {
        sock = clientSocket;
        InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(isReader);
        return;
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    public ClientHandlerTest() {

    }

    @SuppressWarnings("resource")
    public void run() {
      String message;
      try {
        while ((message = reader.readLine()) != null) {
          System.out.println(message);
          String[] getPossibleFileName = message.split("~////~", 3);
          System.out.println(getPossibleFileName[0] + " " + getPossibleFileName[1] + " " + getPossibleFileName[2]);
          File firstFile = new File("chat/" + getPossibleFileName[1] + ".txt");
          File secondFile = new File("chat/" + getPossibleFileName[2] + ".txt");
          boolean firstFileExists = firstFile.exists();
          boolean secondFileExists = secondFile.exists();
          if (firstFileExists) {
            FILENAME = "chat/" + getPossibleFileName[1] + ".txt";
          } else {
            if (!secondFileExists) {
              if (getPossibleFileName[1].equals("public")) {
                FILENAME = "chat/serverChat.txt";

              } else {
                FILENAME = "chat/" + getPossibleFileName[1] + ".txt";
                try {
                  FileWriter writer = new FileWriter(FILENAME);
                  writer.write(" ");
                  writer.close();
                } catch (Exception e) {
                  // TODO: handle exception
                }
              }
            } else {
              FILENAME = "chat/" + getPossibleFileName[2] + ".txt";
            }
          }
          System.out.println(FILENAME);
          FileReader fileReader = new FileReader(FILENAME);
          String allTheGames = new BufferedReader(fileReader).readLine();
          FileWriter writer2 = new FileWriter(FILENAME);
          if (allTheGames == null) {
            allTheGames = " ";
          }
          String s = allTheGames + "~" + getPossibleFileName[0];
          writer2.write(s);
          writer2.close();

          System.out.println("read " + message);

          tellEveryone(message);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    File file = new File(FILENAME);
    boolean tempFile = file.exists();
    if (!tempFile) {
      FileWriter writer2 = new FileWriter(FILENAME);
      writer2.write(" ");
      writer2.close();
    }
    new TestNewServer().go();
    System.out.println("returning");
    return;
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
        System.out.println("got a connection");
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
