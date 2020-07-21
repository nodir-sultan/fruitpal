package com.fruitpal.model;

import java.util.Objects;

public class PurchaseData {
  String commodity;
  double price;
  int tradeVolume;

  public String getCommodity() {
    return commodity;
  }

  public PurchaseData withCommodity(String commodity) {
    this.commodity = commodity;
    return this;
  }

  public double getPrice() {
    return price;
  }

  public PurchaseData withPrice(double price) {
    this.price = price;
    return this;
  }

  public int getTradeVolume() {
    return tradeVolume;
  }

  public PurchaseData withTradeVolume(int tradeVolume) {
    this.tradeVolume = tradeVolume;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PurchaseData that = (PurchaseData) o;
    return Double.compare(that.price, price) == 0 &&
            tradeVolume == that.tradeVolume &&
            Objects.equals(commodity, that.commodity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commodity, price, tradeVolume);
  }

  @Override
  public String toString() {
    return "PurchaseData{" +
            "commodity='" + commodity + '\'' +
            ", price=" + price +
            ", tradeVolume=" + tradeVolume +
            '}';
  }
}
