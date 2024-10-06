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

  public ClientHandler(Socket socket, ChatServer server) throws IOException {
    this.socket = socket;
    this.server = server;
    this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    this.out = new DataOutputStream(socket.getOutputStream());
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
        System.out.println("Client: " + message);
        server.broadcast("Client: " + message, this);
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
      System.out.println("Client disconnected");
    } catch (IOException e) {
      System.out.println("Error closing client connection: " + e.getMessage());
    }
  }
}
