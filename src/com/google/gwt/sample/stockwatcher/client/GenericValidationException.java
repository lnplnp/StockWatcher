package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

public class GenericValidationException extends StockValidationExcepion {// implements
                                                                         // Serializable{
  public GenericValidationException() {

  }

  public GenericValidationException(String symbol) {
    super(symbol);
  }
}
