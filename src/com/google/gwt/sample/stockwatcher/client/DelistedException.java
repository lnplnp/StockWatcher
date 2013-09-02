package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

public class DelistedException extends StockValidationExcepion // implements
                                                               // Serializable//
                                                               // Exception
                                                               // implements
                                                               // Serializable {
{
  public DelistedException() {

  }

  public DelistedException(String symbol) {
    super(symbol);
  }
  // private String symbol;
  //
  // public DelistedException() {
  // }
  //
  // public DelistedException(String symbol) {
  // this.symbol = symbol;
  // }
  //
  // public String getSymbol() {
  // return this.symbol;
  // }
}
