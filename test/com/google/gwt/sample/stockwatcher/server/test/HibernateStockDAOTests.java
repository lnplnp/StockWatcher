package com.google.gwt.sample.stockwatcher.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.gwt.sample.stockwatcher.server.dao.StockPriceDAO;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"applicationContext.xml"})

public class HibernateStockDAOTests {
	// this instance will be dependency injected by type
    @Autowired    
    private StockPriceDAO stockDao;
    
    @Test
    public void retrieve() throws Exception {
    	assertNotNull(stockDao);

    }

}
