package com.dryo.app.cli;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ChatCLI {
  private final Scanner scanner;
  private final PrintStream out;

  public ChatCLI(InputStream in, PrintStream out) {
    this.scanner = new Scanner(in);
    this.out = out;
  }

  public void run() {
    try {
      out.println("Welcome to the chat application!");
      String choice = getUserChoice();

      if (choice.equals("1")) {
        out.println("Server is not implemented yet.");
      } else if (choice.equals("2")) {
        out.println("Client is not implemented yet.");
      }
    } finally {
      scanner.close();
    }
  }

  private String getUserChoice() {
    String choice = "";
    while (!choice.equals("1")
        && !choice.equals("2")
        && !choice.equalsIgnoreCase("q")
        && !choice.equalsIgnoreCase("quit")) {

      out.println("Will you be the server or client?");
      out.println("1. Server");
      out.println("2. Client");
      out.println("Enter 'q' or 'quit' to exit.");
      out.print("Please enter your choice (1, 2, q, or quit): ");

      // Check if there is input available
      if (scanner.hasNext()) {
        choice = scanner.next(); // Read the input only if available
      } else {
        out.println("No input detected. Please try again.");
        break; // Prompt again if no input was detected
      }

      if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
        out.println("Exiting the application...");
        break;
      } else if (!choice.equals("1") && !choice.equals("2")) {
        out.println("Invalid choice. Please select 1 for Server, 2 for Client, or q/quit to exit.");
      }
    }
    return choice;
  }
}
