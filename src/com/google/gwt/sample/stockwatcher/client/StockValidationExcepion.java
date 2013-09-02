package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

public class StockValidationExcepion extends Exception implements Serializable {
  private String symbol;

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public StockValidationExcepion(String symbol) {
    super(symbol);
    this.symbol = symbol;
  }

  public StockValidationExcepion() {

  }
}
