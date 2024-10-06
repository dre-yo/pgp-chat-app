package com.dryo.app.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
  private Socket socket;
  private DataOutputStream out;
  private DataInputStream in;
  private Scanner scanner;

  public ChatClient(String address, int port) throws IOException {
    socket = new Socket(address, port);
    out = new DataOutputStream(socket.getOutputStream());
    in = new DataInputStream(socket.getInputStream());
    scanner = new Scanner(System.in);
  }

  public void start() {
    System.out.println("Connected to the server");

    // Create a new thread to listen for incoming messages from the server
    new Thread(this::listenForMessages).start();

    String message = "";
    while (!message.equals("exit")) {
      try {
        System.out.print("You: ");
        message = scanner.nextLine();
        out.writeUTF(message);
        out.flush();
      } catch (IOException e) {
        System.out.println("Error sending message: " + e.getMessage());
      }
    }

    closeConnection();
  }

  // Method to listen for messages from the server
  private void listenForMessages() {
    String messageFromServer = "";
    try {
      while (!socket.isClosed() && (messageFromServer = in.readUTF()) != null) {
        System.out.println(messageFromServer); // Display message from other clients
      }
    } catch (IOException e) {
      System.out.println("Connection closed.");
    }
  }

  private void closeConnection() {
    try {
      in.close();
      out.close();
      socket.close();
      System.out.println("Connection closed");
    } catch (IOException e) {
      System.out.println("Error closing connection: " + e.getMessage());
    }
  }
}
