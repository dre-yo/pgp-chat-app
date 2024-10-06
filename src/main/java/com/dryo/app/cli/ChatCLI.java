package com.dryo.app.cli;

import com.dryo.app.client.ChatClient;
import com.dryo.app.server.ChatServer;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ChatCLI {
  private final PrintStream out;
  private final InputStream in;

  public ChatCLI(InputStream in, PrintStream out) {
    this.in = in;
    this.out = out;
  }

  public void run() {
    try {
      out.println("Welcome to the chat application!");
      String choice = getUserChoice();

      if (choice.equals("1")) {
        ChatServer server = new ChatServer(6000);
        new Thread(server::start).start(); // Start server in a new thread
      } else if (choice.equals("2")) {
        ChatClient client = new ChatClient("localhost", 6000);
        client.start();
      }
    } catch (Exception e) {
      out.println("An error occurred while running the chat application.");
    }
  }

  private String getUserChoice() {
    Scanner scanner = new Scanner(in);
    String choice = "";

    while (!choice.equals("1") && !choice.equals("2")) {
      out.println("Will you be the server or client?");
      out.println("1. Server");
      out.println("2. Client");
      out.print("Please enter your choice: ");
      choice = scanner.nextLine();
    }

    return choice;
  }
}
