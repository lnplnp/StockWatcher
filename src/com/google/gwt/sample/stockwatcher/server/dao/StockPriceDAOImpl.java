package com.google.gwt.sample.stockwatcher.server.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.google.gwt.sample.stockwatcher.client.StockPrice;

@Repository
public class StockPriceDAOImpl implements StockPriceDAO {
	public StockPriceDAOImpl() {
	}

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public void delete(StockPrice stock) {

		hibernateTemplate.delete(stock);

	}

	@Override
	public StockPrice get(String symbol) {
		StockPrice stock = null;

		stock = (StockPrice) hibernateTemplate.get(StockPrice.class, symbol);// (employee);

		return stock;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockPrice> getAllStocks() {
		List<StockPrice> stocks = null;

		stocks = hibernateTemplate.getSessionFactory().openSession()
				.createQuery("from StockPrice").list();

		return stocks;
	}

	@Override
	public boolean saveOrUpdate(StockPrice stock) {
		boolean successInsert = false;
		if (this.get(stock.getSymbol()) != null) {
			successInsert = false;
			return successInsert;
		} else
			successInsert = true;

		hibernateTemplate.save(stock);// ("from StockPrice").list();//(employee);

		return successInsert;

	}

}
