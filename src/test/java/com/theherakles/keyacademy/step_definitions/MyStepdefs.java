package com.theherakles.keyacademy.step_definitions;

import io.cucumber.java.en.Given;

public class MyStepdefs {

  @Given("just print")
  public void justPrint() {
    System.out.println("Herakles");
  }
}
