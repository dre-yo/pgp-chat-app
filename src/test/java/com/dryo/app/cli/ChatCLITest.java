package com.dryo.app.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class ChatCLITest {

  @Test
  public void testValidServerChoice() {
    String input = "1\n"; // Simulates user input
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ChatCLI cli = new ChatCLI(in, new PrintStream(out));
    cli.run();

    String output = out.toString();
    assertTrue(output.contains("Server is not implemented yet."));
  }

  @Test
  public void testValidClientChoice() {
    String input = "2\n"; // Simulates user input
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ChatCLI cli = new ChatCLI(in, new PrintStream(out));
    cli.run();

    String output = out.toString();
    assertTrue(output.contains("Client is not implemented yet."));
  }

  @Test
  public void testInvalidChoice() {
    String input = "3\n1\n"; // Simulates invalid input followed by a valid one
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ChatCLI cli = new ChatCLI(in, new PrintStream(out));
    cli.run();

    String output = out.toString();
    assertTrue(output.contains("Invalid choice."));
    assertTrue(output.contains("Server is not implemented yet."));
  }

  @Test
  public void testQuitChoice() {
    String input = "q\n"; // Simulates quit input
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
      ChatCLI cli = new ChatCLI(in, new PrintStream(out));
      cli.run();
    } catch (SecurityException e) {
      // Catch System.exit exception
    }

    String output = out.toString();
    assertTrue(output.contains("Exiting the application..."));
  }
}
