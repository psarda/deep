package me.deep.app.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class VerySimpleChatServer implements Serializable {

  private static final long serialVersionUID = 1L;
  ArrayList clientOutputStreams;
  static String message;
  private static final String FILENAME = "chat/serverChat.txt";

  public class ClientHandler implements Runnable {
    BufferedReader reader;
    Socket sock;

    public ClientHandler(Socket clientSocket) {
      try {
        sock = clientSocket;
        InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
        reader = new BufferedReader(isReader);
        System.out.println("working fine");
        new VerySimpleChatServer().go();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    public void run() {

      try {
        while ((message = reader.readLine()) != null) {
          System.out.println("read " + message);
          FileReader fileReader = new FileReader(FILENAME);
          BufferedReader bufferedReader = new BufferedReader(fileReader);
          String allTheGames = bufferedReader.readLine();
          FileWriter writer2 = new FileWriter(FILENAME);
          if (allTheGames == null) {
            allTheGames = " ";
          }
          writer2.write(allTheGames + "~" + message);
          writer2.close();
          tellEveryone(message);
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {

    new VerySimpleChatServer().go();
    return;
  }

  public void go() {
    clientOutputStreams = new ArrayList();
    try {
      ServerSocket serverSock = new ServerSocket(5000);
      while (true) {
        Socket clientSocket = serverSock.accept();
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        clientOutputStreams.add(writer);
        Thread t = new Thread(new ClientHandler(clientSocket));
        t.start();

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
