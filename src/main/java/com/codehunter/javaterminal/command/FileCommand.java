package com.codehunter.javaterminal.command;

import com.codehunter.javaterminal.GlobalStack;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class FileCommand {
  private static final Logger log = LogManager.getLogger(FileCommand.class);
  private final GlobalStack globalStack;

  public FileCommand(GlobalStack globalStack) {
    this.globalStack = globalStack;
  }

  @ShellMethod("Current directory")
  public String cd(String uri) {
    if (isAbsolutePath(uri)) {
      globalStack.setCurrentDirectory(uri);
      return "change directory successfully";
    } else {
      var newUri = Paths.get(globalStack.getCurrentDirectory()).resolve(uri).toAbsolutePath();
      if (Files.isDirectory(newUri)) {
        globalStack.setCurrentDirectory(newUri.toAbsolutePath().normalize().toString());
        return "change directory successfully";
      }
      return "input is not a directory";
    }
  }

  private boolean isAbsolutePath(String uri) {
    var path = Paths.get(uri);
    return path.isAbsolute() && Files.isDirectory(path);
  }

  @ShellMethod("List all file in directory")
  public String ls() throws IOException {
    var path = Path.of(globalStack.getCurrentDirectory());
    var result = new StringBuilder();
    if (Files.isDirectory(path)) {
      Files.list(path).forEach(e -> result.append(e).append('\n'));
    }
    return result.toString();
  }

  @ShellMethod("Current working directory")
  public String pwd() {
    return Paths.get(globalStack.getCurrentDirectory()).toAbsolutePath().toString();
  }

  @ShellMethod("Create new folder")
  public void mkdir(String filename) throws IOException {
    var path = Path.of(globalStack.getCurrentDirectory()).toAbsolutePath().resolve(filename);
    var newPath = Files.createDirectory(path);
    log.info("Create folder successfully" + newPath);
  }
}
