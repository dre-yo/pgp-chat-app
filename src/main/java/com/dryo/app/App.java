package com.dryo.app;

import com.dryo.app.cli.ChatCLI;
import java.io.InputStream;
import java.io.PrintStream;

/** Hello world! */
public class App {
  public static void main(String[] args) {
    // Create the scanner here
    InputStream input = System.in;
    PrintStream output = System.out;

    // Pass the scanner to ChatCLI
    ChatCLI chatCLI = new ChatCLI(input, output);
    chatCLI.run();
  }
}
