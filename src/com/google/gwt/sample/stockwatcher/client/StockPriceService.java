package com.google.gwt.sample.stockwatcher.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

//@RemoteServiceRelativePath("stockPrices")
@RemoteServiceRelativePath("springGwtServices/greetingService")
public interface StockPriceService extends RemoteService {

  StockPrice[] getPrices(String[] symbols) ;

  
  
  List<StockPrice> getAllStocks();
  boolean saveOrUpdate(StockPrice stock)throws StockValidationExcepion;
  void delete(StockPrice stock);
  StockPrice get(String symbol);
  
}