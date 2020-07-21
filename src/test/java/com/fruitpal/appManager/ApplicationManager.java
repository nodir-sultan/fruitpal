package com.fruitpal.appManager;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private final Properties properties;
  public static ThreadLocal<Process> tlDriver = new ThreadLocal<>();
  public Process process;
  public Expect expect;
  StringBuilder wholeBuffer;

  private FtpHelper ftp;
  private TraderHelper traderHelper;

  public ApplicationManager() {
    properties = new Properties();
  }

  public Expect init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    if (tlDriver.get() != null) {
      process = tlDriver.get();
      return expect;
    }
    process = Runtime.getRuntime().exec(getProperty("app.baseFolder"));
    tlDriver.set(process);
    wholeBuffer = new StringBuilder();
    expect = new ExpectBuilder()
            .withInputs(process.getInputStream())
            .withOutput(process.getOutputStream())
            .withTimeout(1, TimeUnit.SECONDS)
            .withEchoOutput(wholeBuffer)
            .withEchoInput(wholeBuffer)
            .withExceptionOnFailure()
            .build();
    Runtime.getRuntime().addShutdownHook(
            new Thread(() -> {
              process.destroyForcibly();
              process = null;
            }));
    return expect;
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public TraderHelper trader() {
    if (traderHelper == null) {
      traderHelper = new TraderHelper(this);
    }
    return traderHelper;
  }

  public FtpHelper ftp() {
    if (ftp == null) {
      ftp = new FtpHelper(this);
    }
    return ftp;
  }

}
