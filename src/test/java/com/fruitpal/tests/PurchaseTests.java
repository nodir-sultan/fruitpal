package com.fruitpal.tests;

import com.fruitpal.model.CostData;
import com.fruitpal.model.PurchaseData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PurchaseTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validPurchasesFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/purchase.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<PurchaseData> purchases = gson.fromJson(json, new TypeToken<List<PurchaseData>>() {
      }.getType()); // List<PurchaseData>.class
      return purchases.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(enabled = false)
  public void testFtpConnection() {
    //app.ftp().download("commoditydata.json", );
  }

  @Test(dataProvider = "validPurchasesFromJson")
  public void testShowCostOfBuyingWhenSufficientArgumentsSupplied(PurchaseData purchaseData) throws IOException {
    CostData costData = new CostData().withCommodity("orange").withCountry("US")
            .withFixed_overhead(32.00).withVariable_overhead(1.24);

    String costDataCountry = app.trader().specifyPurchaseData(purchaseData);
    assertThat(costDataCountry, equalTo(costData.getCountry()));
  }

  @Test
  public void testShowUsageWhenInvalidCommand() throws IOException {
    String responseMessage = app.trader().specifyInvalidCommand("403");
    assertThat(responseMessage, equalTo(" Unrecognized command '403' Please enter a valid command"));
  }

  @Test
  public void testShowUsageWhenInsufficientArgumentsSupplied_Commodity() throws IOException {
    String[] responseMessage = app.trader().specifyPurchaseWithWrongValueForArguments("", 53, 405);
    assertThat(responseMessage[0], equalTo(" No results for the input commodity found"));
  }

  @Test
  public void testShowUsageWhenInsufficientArgumentsSupplied_Price() throws IOException {
    String[] responseMessage = app.trader().specifyPurchaseWithWrongValueForArguments("mango", 0, 405);
    assertThat(responseMessage[0], equalTo(" Invalid number of arguments"));
  }

  @Test
  public void testShowUsageWhenInsufficientArgumentsSupplied_TradeVolume() throws IOException {
    String[] responseMessage = app.trader().specifyPurchaseWithWrongValueForArguments("mango", 53, 0);
    assertThat(responseMessage[0] + responseMessage[1] + responseMessage[2], equalTo(" The value passed for argument  cannot be parsed to type Decimal"));
  }

  @Test
  public void testAbortWhenTooManyArgumentsSupplied() throws IOException {
    String responseMessage = app.trader().specifyPurchaseWithTooManyArguments(
            "mango", 53, 405, "orange", 53, 405);
    assertThat(responseMessage, equalTo(" Invalid number of arguments"));
  }

  @Test
  public void testWhenArgumentsSupplied_PriceIsNotInteger() throws IOException {
    String[] responseMessage = app.trader().specifyPurchaseWithWrongValueForArguments("mango", "XYZ", "40");
    assertThat(responseMessage[0] + responseMessage[1] + responseMessage[2], equalTo(" The value passed for argument XYZ cannot be parsed to type "));
  }

  @Test
  public void testWhenArgumentsSupplied_TradeVolumeIsNotInteger() throws IOException {
    String[] responseMessage = app.trader().specifyPurchaseWithWrongValueForArguments("mango", "20", "ABC");
    assertThat(responseMessage[0] + responseMessage[1] + responseMessage[2], equalTo(" The value passed for argument ABC cannot be parsed to type "));
  }

}
