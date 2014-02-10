package com.google.gwt.sample.stockwatcher.server.service;

import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gwt.sample.stockwatcher.client.DelistedException;
import com.google.gwt.sample.stockwatcher.client.StockPrice;
import com.google.gwt.sample.stockwatcher.client.StockPriceService;
import com.google.gwt.sample.stockwatcher.client.StockValidationExcepion;
import com.google.gwt.sample.stockwatcher.server.dao.StockPriceDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@Service("greetingService")
@Transactional
public class StockPriceServiceImpl extends RemoteServiceServlet implements StockPriceService {
  @Autowired
  private ValidatorFactory validator;
  // private static Validator validator;
  // private ConstraintValidator validator;
  @Autowired
  // @Qualifier("stockDao")
  private StockPriceDAO stockDao;

  // @PostConstruct
  // public void initializeValidator() {
  //
  // ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  // validator = factory.getValidator();
  // }
  public StockPriceDAO getStockDao() {
    return stockDao;
  }

  public void setStockDao(StockPriceDAO stockDao) {
    this.stockDao = stockDao;
  }

  private static final double MAX_PRICE = 100.0; // $100.00
  private static final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

  public StockPrice[] getPrices(String[] symbols) {
    Random rnd = new Random();

    StockPrice[] prices = new StockPrice[symbols.length];
    for (int i = 0; i < symbols.length; i++) {

      double price = rnd.nextDouble() * MAX_PRICE;
      double change = price * MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f);

      prices[i] = new StockPrice(symbols[i], price, change);
    }

    return prices;

  }

  public void delete(StockPrice stock) {
    stockDao.delete(stock);

  }

  public StockPrice get(String symbol) {
    return stockDao.get(symbol);
  }

  public List<StockPrice> getAllStocks() {
    // TODO Auto-generated method stub
    return stockDao.getAllStocks();
  }

  public boolean saveOrUpdate(StockPrice stock) throws StockValidationExcepion {
    Set<ConstraintViolation<StockPrice>> constraintViolations = validator.getValidator().validate(stock);

    for (ConstraintViolation violation : constraintViolations) {
      if (violation.getMessage().indexOf("delisted") != -1)
        throw new DelistedException("ERR");
      else
        throw new StockValidationExcepion("Stock code must be between 1 and 10 chars that are numbers, letters, or dots.");
    }
    // if(constraintViolations.size()!=0){
    // System.out.println("****************Caught Error..now throwing exception****************");
    // throw new DelistedException("ERR");
    // }
    // assertEquals(1, constraintViolations.size());if
    // (stock.getSymbol().equals("ERR")) {
    // if (stock.getSymbol().equals("ERR")) {
    // throw new DelistedException("ERR");
    // }

    // TODO Auto-generated method stub
    return stockDao.saveOrUpdate(stock);
  }

}
