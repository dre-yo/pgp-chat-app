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
  private String username;

  public ChatClient(String address, int port) throws IOException {
    socket = new Socket(address, port);
    out = new DataOutputStream(socket.getOutputStream());
    in = new DataInputStream(socket.getInputStream());
    scanner = new Scanner(System.in);
  }

  public void start() {
    System.out.print("Enter your username: ");
    username = scanner.nextLine(); // Ask for the username
    System.out.println("Connected to the server");

    // Send the username to the server
    try {
      out.writeUTF(username);
      out.flush();
    } catch (IOException e) {
      System.out.println("Error sending username to server: " + e.getMessage());
    }

    // Create a new thread to listen for incoming messages from the server
    new Thread(this::listenForMessages).start();

    String message = "";
    while (!message.equals("exit")) {
      try {
        System.out.print("> "); // Show the prompt for user input
        message = scanner.nextLine();

        // Ensure the user input is not empty before sending
        if (!message.trim().isEmpty()) {
          out.writeUTF(message);
          out.flush();
        }
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
        // Only display non-empty messages
        if (!messageFromServer.trim().isEmpty()) {
          System.out.println(messageFromServer);
        }
        System.out.print("> ");
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
