package com.google.gwt.sample.stockwatcher.server.validation;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.gwt.sample.stockwatcher.server.dao.StockPriceDAO;
@Service("validationService")
public class CheckDelistedStockValidator implements ConstraintValidator<CheckDelistedStock, String> {
	@Autowired
	//@Qualifier("stockDaoV")
	private StockPriceDAO stockDao;
	
	public StockPriceDAO getStockDao() {
		return stockDao;
	}

	public void setStockDao(StockPriceDAO stockDao) {
		this.stockDao = stockDao;
	}
	//private CaseMode caseMode;

    public void initialize(CheckDelistedStock constraintAnnotation) {
      //  this.caseMode = constraintAnnotation.value();
    }

    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
System.out.println("******************VALIDATOR Impl Called*****************************DAO ref-->"+stockDao);
        if (object == null)
            return true;

//        if (caseMode == CaseMode.UPPER)
//            return object.equals(object.toUpperCase());
//        else
//            return object.equals(object.toLowerCase());
        return (object.equals(DelistedStock.ERR.toString())? false : true);
//        if(object.equals(DelistedStock.ERR.toString()))
//        	return false;
//        else 
//        	return true;
    }

}
