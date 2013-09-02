package com.google.gwt.sample.stockwatcher.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StockPriceServiceAsync {

  void getPrices(String[] symbols, AsyncCallback<StockPrice[]> callback);

  void getAllStocks(AsyncCallback<List<StockPrice>> callback);

  void saveOrUpdate(StockPrice stock, AsyncCallback<Boolean> callback);

  void delete(StockPrice stock, AsyncCallback<Void> callback);

  void get(String symbol, AsyncCallback<StockPrice> callback);
}
