package com.fruitpal.model;

import java.util.ArrayList;
import java.util.Objects;

public class CostData extends ArrayList<CostData> {
  String country;
  String commodity;
  double fixed_overhead;
  double variable_overhead;

  public String getCountry() {
    return country;
  }

  public CostData withCountry(String country) {
    this.country = country;
    return this;
  }

  public String getCommodity() {
    return commodity;
  }

  public CostData withCommodity(String commodity) {
    this.commodity = commodity;
    return this;
  }

  public double getFixed_overhead() {
    return fixed_overhead;
  }

  public CostData withFixed_overhead(double fixed_overhead) {
    this.fixed_overhead = fixed_overhead;
    return this;
  }

  public double getVariable_overhead() {
    return variable_overhead;
  }

  public CostData withVariable_overhead(double variable_overhead) {
    this.variable_overhead = variable_overhead;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CostData costData = (CostData) o;
    return Double.compare(costData.fixed_overhead, fixed_overhead) == 0 &&
            Double.compare(costData.variable_overhead, variable_overhead) == 0 &&
            Objects.equals(country, costData.country) &&
            Objects.equals(commodity, costData.commodity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(country, commodity, fixed_overhead, variable_overhead);
  }

  @Override
  public String toString() {
    return "CostData{" +
            "country='" + country + '\'' +
            ", commodity='" + commodity + '\'' +
            ", fixed_overhead=" + fixed_overhead +
            ", variable_overhead=" + variable_overhead +
            '}';
  }
}

