package com.dryo.app.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private Socket socket;
  private ChatServer server;
  private DataInputStream in;
  private DataOutputStream out;
  private String username;

  public ClientHandler(Socket socket, ChatServer server) throws IOException {
    this.socket = socket;
    this.server = server;
    this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    this.out = new DataOutputStream(socket.getOutputStream());

    // Read the username from the client when they connect
    this.username = in.readUTF();
    System.out.println(username + " has joined the chat.");
    server.broadcast(username + " has joined the chat.", this); // Broadcast the username
  }

  @Override
  public void run() {
    String message;
    try {
      while (true) {
        message = in.readUTF();
        if (message.equals("exit")) {
          break;
        }
        System.out.println(username + ": " + message);
        server.broadcast(username + ": " + message, this); // Prepend the username to the message
      }
    } catch (IOException e) {
      System.out.println("Error handling client: " + e.getMessage());
    } finally {
      closeConnection();
    }
  }

  public void sendMessage(String message) {
    try {
      out.writeUTF(message);
    } catch (IOException e) {
      System.out.println("Error sending message to client: " + e.getMessage());
    }
  }

  private void closeConnection() {
    try {
      in.close();
      out.close();
      socket.close();
      server.removeClient(this);
      System.out.println(username + " has left the chat.");
      server.broadcast(username + " has left the chat.", this); // Notify other clients
    } catch (IOException e) {
      System.out.println("Error closing client connection: " + e.getMessage());
    }
  }
}
