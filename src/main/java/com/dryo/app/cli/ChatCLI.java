package com.dryo.app.cli;

import java.util.Scanner;

public class ChatCLI {
  private final Scanner scanner = new Scanner(System.in);

  public void run() {
    System.out.println("Enter your name: ");
    String name = scanner.nextLine();
    System.out.println("Welcome " + name);
  }
}
