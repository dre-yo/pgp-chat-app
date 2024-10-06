package com.dryo.app.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
  private ServerSocket serverSocket;
  private List<ClientHandler> clients;

  public ChatServer(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    clients = new ArrayList<>();
  }

  public void start() {
    System.out.println("Server started. Waiting for clients...");

    while (true) {
      try {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        ClientHandler clientHandler = new ClientHandler(clientSocket, this);
        clients.add(clientHandler);
        new Thread(clientHandler).start();
      } catch (IOException e) {
        System.out.println("Error accepting client: " + e.getMessage());
      }
    }
  }

  public void broadcast(String message, ClientHandler sender) {
    for (ClientHandler client : clients) {
      if (client != sender) {
        client.sendMessage(message);
      }
    }
  }

  public void removeClient(ClientHandler client) {
    clients.remove(client);
  }
}
