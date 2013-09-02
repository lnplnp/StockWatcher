package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import com.google.gwt.sample.stockwatcher.server.validation.CheckDelistedStock;

@SuppressWarnings("serial")
public class StockPrice implements Serializable {
  // @NotNull(message="Stock symbol is mandatory", jjk=oo)
  // @Size(min = 1, max = 10,
  // message="Stock symbol should be between 1 and 10 characters")
  @Pattern(regexp = "[0-9a-zA-Z\\.]{1,10}$", message = "Stock code must be between 1 and 10 chars that are numbers, letters, or dots.")
  // , flag=)
  @CheckDelistedStock
  private String symbol;

  private double price;
  private double change;

  public StockPrice(String symbol, double price, double change) {
    super();
    this.symbol = symbol;
    this.price = price;
    this.change = change;
  }

  public StockPrice() {
  }

  public StockPrice(String symbol) {
    super();
    this.symbol = symbol;
  }

  public String getSymbol() {
    return this.symbol;
  }

  public double getPrice() {
    return this.price;
  }

  public double getChange() {
    return this.change;
  }

  public double getChangePercent() {
    return 100.0 * this.change / this.price;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setChange(double change) {
    this.change = change;
  }
}
