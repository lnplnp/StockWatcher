package com.google.gwt.sample.stockwatcher.server.dao;

import java.util.List;

import com.google.gwt.sample.stockwatcher.client.StockPrice;

public interface StockPriceDAO {
List<StockPrice> getAllStocks();
boolean saveOrUpdate(StockPrice stock);
void delete(StockPrice stock);
StockPrice get(String symbol);
}
