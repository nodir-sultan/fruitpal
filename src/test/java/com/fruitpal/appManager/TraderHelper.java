package com.fruitpal.appManager;

import com.fruitpal.model.PurchaseData;

import java.io.IOException;

import static net.sf.expectit.matcher.Matchers.contains;
import static net.sf.expectit.matcher.Matchers.regexp;

public class TraderHelper {

  private final ApplicationManager app;

  public TraderHelper(ApplicationManager app) {
    this.app = app;
  }

  public void open() throws IOException {
    app.init();
  }

  public boolean isOpen() throws IOException {
    return app.expect.expect(contains(">")).isSuccessful();
  }

  public void close() throws IOException {
    String response = app.wholeBuffer.toString();
    System.out.println(response);
    app.expect.close();
    app.process.destroyForcibly();
    app.process = null;
  }

  public String specifyPurchaseData(PurchaseData purchaseData) throws IOException {
    if (!isOpen()) {
      return "App is not initialised";
    } else {
      app.expect.sendLine("fruitpal " + purchaseData.getCommodity()
              + " " + purchaseData.getPrice() + " " + purchaseData.getTradeVolume());
      String list = app.expect.expect(regexp("\n")).getInput();
      System.out.println("List of all available countries of origin: " + list);
      // return String array;
      String[] costDataArrayList = list.split("< ");
      String[] costDataArray = costDataArrayList[1].split(" ");
      String costDataCountry = costDataArray[0];
      return costDataCountry;
    }
  }

  public String[] specifyPurchaseWithWrongValueForArguments(String commodity, long pricePerTon, int tradeVolume) throws IOException {
    app.expect.expect(contains(">"));
    if (commodity.equals("")) {
      app.expect.sendLine("fruitpal " + " " + pricePerTon + " " + tradeVolume);
    } else if (pricePerTon == 0) {
      app.expect.sendLine("fruitpal " + commodity + " " + tradeVolume);
    } else if (tradeVolume == 0) {
      app.expect.sendLine("fruitpal " + commodity + " " + pricePerTon + " ");
    }
    String fullOutput = app.expect.expect(regexp("\r\n")).getInput();
    String delims = "[.,?!'']+";
    String[] messages = fullOutput.split(delims);
    return messages;
  }

  public String[] specifyPurchaseWithWrongValueForArguments(String commodity, String pricePerTon, String tradeVolume) throws IOException {
    app.expect.expect(contains(">"));
    app.expect.sendLine("fruitpal " + commodity + " " + pricePerTon + " " + tradeVolume);
    String fullOutput = app.expect.expect(regexp("\r\n")).getInput();
    String delims = "[.,?!'']+";
    String[] messages = fullOutput.split(delims);
    return messages;
  }

  public String specifyPurchaseWithTooManyArguments(String commodity1, long pricePerTon1, int tradeVolume1,
                                                    String commodity2, int pricePerTon2, int tradeVolume3) throws IOException {
    app.expect.expect(contains(">"));
    app.expect.sendLine("fruitpal " + commodity1 + " " + pricePerTon1 + " " + tradeVolume1 + " "
            + commodity2 + " " + pricePerTon2 + " " + tradeVolume3);
    String fullOutput = app.expect.expect(regexp("\n")).getInput();
    String delims = "[.,?!]+";
    String[] messages = fullOutput.split(delims);
    return messages[0];
  }

  public String specifyInvalidCommand(String command) throws IOException {
    app.expect.expect(contains(">"));
    if (command.equals("fruitpal")) {
      app.expect.sendLine("fruitpal ");
      String fullOutput = app.expect.expect(regexp("\n")).getInput();
      String delims = "[.,?!]+";
      String[] messages = fullOutput.split(delims);
      return messages[0] + messages[1];
    } else {
      app.expect.sendLine(command);
      String fullOutput = app.expect.expect(regexp("\n")).getInput();
      String delims = "[.,?!]+";
      String[] messages = fullOutput.split(delims);
      return messages[0] + messages[1];
    }
  }
}
