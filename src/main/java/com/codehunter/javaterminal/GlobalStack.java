package com.codehunter.javaterminal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class GlobalStack {
  private static final Logger log = LogManager.getLogger(GlobalStack.class);

  private String currentDirectory = "D:/";

  public String getCurrentDirectory() {
    return currentDirectory;
  }

  public void setCurrentDirectory(String currentDirectory) {
    log.info("Change directory to: " + currentDirectory);
    this.currentDirectory = currentDirectory;
  }
}
