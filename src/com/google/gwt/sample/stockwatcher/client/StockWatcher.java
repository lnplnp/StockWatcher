package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;
public class StockWatcher implements EntryPoint {

  private static final int REFRESH_INTERVAL = 5000; // ms
  private VerticalPanel mainPanel = new VerticalPanel();
  private FlexTable stocksFlexTable = new FlexTable();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private TextBox newSymbolTextBox = new TextBox();
  private Button addStockButton = new Button("Add");
  private Label lastUpdatedLabel = new Label();
  private ArrayList<String> stocks = new ArrayList<String>();
  private StockPriceServiceAsync stockPriceSvc = GWT.create(StockPriceService.class);
  private Label errorMsgLabel = new Label();
  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  loadStocks();
    // Create table for stock data.
    stocksFlexTable.setText(0, 0, "Symbol");
    stocksFlexTable.setText(0, 1, "Price");
    stocksFlexTable.setText(0, 2, "Change");
    stocksFlexTable.setText(0, 3, "Remove");

    // Add styles to elements in the stock list table.
    stocksFlexTable.setCellPadding(6);
    stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
    stocksFlexTable.addStyleName("watchList");
    stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
    stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
    stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

    // Assemble Add Stock panel.
    addPanel.add(newSymbolTextBox);
    addPanel.add(addStockButton);
    addPanel.addStyleName("addPanel");

    // Assemble Main panel.
    errorMsgLabel.setStyleName("errorMessage");
    errorMsgLabel.setVisible(false);

    mainPanel.add(errorMsgLabel);
    mainPanel.add(stocksFlexTable);
  
	
    mainPanel.add(addPanel);
    mainPanel.add(lastUpdatedLabel);

    // Associate the Main panel with the HTML host page.
    RootPanel.get("stockList").add(mainPanel);

    // Move cursor focus to the input box.
    newSymbolTextBox.setFocus(true);
    
    // Setup timer to refresh list automatically.
    Timer refreshTimer = new Timer() {
      @Override
      public void run() {
        refreshWatchList();
      }
    };
    refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

    // Listen for mouse events on the Add button.
    addStockButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        addStock();
      }
    });

    // Listen for keyboard events in the input box.
    newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          addStock();
        }
      }
    });
    
  }

  /**
   * Add stock to FlexTable. Executed when the user clicks the addStockButton or
   * presses enter in the newSymbolTextBox.
   */
  private void addStock() {
    final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
    newSymbolTextBox.setFocus(true);

   
    // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
    //commenting out to centralize validation logic
//    if (!symbol.matches("^[0-9a-zA-Z\\.]{1,10}$")) {
//      Window.alert("'" + symbol + "' is not a valid symbol.");
//      newSymbolTextBox.selectAll();
//      return;
//    }

    newSymbolTextBox.setText("");

    // Don't add the stock if it's already in the table.
    if (stocks.contains(symbol))// || checkExistingStock(symbol))
      return;

    // Add the stock to the table.
    int row = stocksFlexTable.getRowCount();

    //add to DB
    if(persistStock(symbol)){
    
   /* 
    
    // Add a button to remove this stock from the table.
    Button removeStockButton = new Button("x");
    removeStockButton.addStyleDependentName("remove");
    removeStockButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
    	  purgeStock(event,symbol);
//        int removedIndex = stocks.indexOf(symbol);
//        stocks.remove(removedIndex);
//        stocksFlexTable.removeRow(removedIndex + 1);
      }  

	
    });
    stocksFlexTable.setText(row, 0, symbol);
    stocksFlexTable.setWidget(row, 1, new Label());
    stocksFlexTable.setWidget(row, 2, new Label());
    stocksFlexTable.setWidget(row, 3, removeStockButton);
    stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
    stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
    stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
    */
   // stocks.add(symbol);
    // Get the stock price.
    refreshWatchList();

  }
  }
   boolean state=false;
   private void purgeStock(ClickEvent event, final String symbol) {
		 StockPrice stockToRemove=null;
		 int removedIndex = stocks.indexOf(symbol);
		 String primaryKey=stocks.get(removedIndex);
		 
	        //s//tocks.remove(removedIndex);
	        //stocksFlexTable.removeRow(removedIndex + 1);
	        
		// Initialize the service proxy.
	    if (stockPriceSvc == null) {
	      stockPriceSvc = GWT.create(StockPriceService.class);
	    }
	    
	    
	    stockPriceSvc.get(primaryKey, new AsyncCallback<StockPrice>() {
		      public void onFailure(Throwable caught) {
			        // TODO: Do something with errors.
			      }

			      public void onSuccess(StockPrice stock) {
			    	  if(stock!=null){
			    		  				// ################################# CODE to DELETE ##########################################################
										
			    		  
			    		    // Set up the callback object.
			    		    AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			    		      public void onFailure(Throwable caught) {
			    		        // TODO: Do something with errors.
			    		      }

			    		      public void onSuccess(Void result) {
			    		        int removedIndex = stocks.indexOf(symbol);
			    		        stocks.remove(removedIndex);
			    		        stocksFlexTable.removeRow(removedIndex + 1);

			    		        //updateTable(result);
			    		      }
			    		    };

			    		    // Make the call to the stock price service.
			    		    stockPriceSvc.delete(stock, callback);//(new StockPrice(symbol, 1), callback);//(stocks.toArray(new String[0]), callback);

			    		  
			    		  
			    		  
			    		  
			    		  
			    		  				// ################################# CODE to DELETE ##########################################################
			    		  
			    	  }
			        //updateTable(result);
			      }
			    }
	    		);
	    
	

	}
   
	 /*
	  * @
	  * return true if stock exists
	  * retunr false if stock doesn't exist
	  */
  private boolean checkExistingStock(String symbol) {
	  // Initialize the service proxy.
	    if (stockPriceSvc == null) {
	      stockPriceSvc = GWT.create(StockPriceService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<StockPrice> callback = new AsyncCallback<StockPrice>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(StockPrice stock) {
	    	    if(stock!=null)
	    	    		state=true;
	    	    else
	    	    		state=false;

	        //updateTable(result);
	      }
	    };

	    // Make the call to the stock price service.
	    stockPriceSvc.get(symbol, callback);//(stocks.toArray(new String[0]), callback);
	
	return state;
}
  boolean bVal=false;
private boolean persistStock(final String symbol) {
	 
	  // Initialize the service proxy.
	    if (stockPriceSvc == null) {
	      stockPriceSvc = GWT.create(StockPriceService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
	      public void onFailure(Throwable caught) {
	    	  System.out.println("**********************EXCEPTION CAUGHT*************************");
	    	  // If the stock code is in the list of delisted codes, display an error message.
	          String details = caught.getMessage();
	          if (caught instanceof DelistedException) 
	            details = "Company '" + ((DelistedException)caught).getSymbol() + "' was delisted";
	          else if(caught instanceof StockValidationExcepion)
	      		details = caught.getMessage();

	          errorMsgLabel.setText("Error: " + details);
	          errorMsgLabel.setVisible(true);
	      }

	      public void onSuccess(Boolean result) {
	    	  bVal=result.booleanValue();
	    	  if(result.booleanValue()==true)  
	    	  stocks.add(symbol);
	    	    // Clear any errors.
	    	    errorMsgLabel.setVisible(false);
	        //updateTable(result);
	      }
	    };

	    // Make the call to the stock price service.
	    stockPriceSvc.saveOrUpdate(new StockPrice(symbol), callback);//(stocks.toArray(new String[0]), callback);
	return bVal;
}
  private void loadStocks(){
	// Initialize the service proxy.
	    if (stockPriceSvc == null) {
	      stockPriceSvc = GWT.create(StockPriceService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<List<StockPrice>> callback = new AsyncCallback<List<StockPrice>>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(List<StockPrice> existingStocks) {
	       if(existingStocks!=null){
	    	   for(int i=0;i<existingStocks.size();i++){
	    		   StockPrice tmp=existingStocks.get(i);
	    		   stocks.add(tmp.getSymbol());
	    	   }
//	    	   for(StockPrice stock:existingStocks)
//	    		   	stocks.add(stock.getSymbol());
	    		   	
	       }
	      }
	    };

	    // Make the call to the stock price service.
	    stockPriceSvc.getAllStocks(callback);//(stocks.toArray(new String[0]), callback);
	  //  refreshWatchList();
  }

/**
   * Generate random stock prices.
   */
  private void refreshWatchList() {
	    // Initialize the service proxy.
	    if (stockPriceSvc == null) {
	      stockPriceSvc = GWT.create(StockPriceService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<StockPrice[]> callback = new AsyncCallback<StockPrice[]>() {
	      public void onFailure(Throwable caught) {
	    	// TODO: Do something with errors.
	      }

	      public void onSuccess(StockPrice[] result) {
	        updateTable(result);
	      }
	    };

	    // Make the call to the stock price service.
	    stockPriceSvc.getPrices(stocks.toArray(new String[0]), callback);
	  }


  /**
   * Update the Price and Change fields all the rows in the stock table.
   *
   * @param prices Stock data for all rows.
   */
  private void updateTable(StockPrice[] prices) {
    for (int i = 0; i < prices.length; i++) {
      updateTable(prices[i]);
    }

    // Display timestamp showing last refresh.
    lastUpdatedLabel.setText("Last update : "
        + DateTimeFormat.getMediumDateTimeFormat().format(new Date()));
    // Clear any errors.
    //errorMsgLabel.setVisible(false);

  }

  /**
   * Update a single row in the stock table.
   *
   * @param price Stock data for a single row.
   */
  private void updateTable(final StockPrice price) {
	  
    // Make sure the stock is still in the stock table.
    if (!stocks.contains(price.getSymbol())) {
      return;
    }

    int row = stocks.indexOf(price.getSymbol()) + 1;

    // Format the data in the Price and Change fields.
    String priceText = NumberFormat.getFormat("#,##0.00").format(
        price.getPrice());
    NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
    String changeText = changeFormat.format(price.getChange());
    String changePercentText = changeFormat.format(price.getChangePercent());
    stocksFlexTable.setText(row, 0, price.getSymbol());
    // Populate the Price and Change fields with new data.
    stocksFlexTable.setText(row, 1, priceText);
    Label changeWidget = new Label("");//(Label)stocksFlexTable.getWidget(row, 2);
    changeWidget.setText(changeText + " (" + changePercentText + "%)");
    stocksFlexTable.setWidget(row, 2, changeWidget);
    // Change the color of text in the Change field based on its value.
    String changeStyleName = "noChange";
    if (price.getChangePercent() < -0.1f) {
      changeStyleName = "negativeChange";
    }
    else if (price.getChangePercent() > 0.1f) {
      changeStyleName = "positiveChange";
    }

    changeWidget.setStyleName(changeStyleName);
    
    //##############################################temp code
    // Add a button to remove this stock from the table.
    Button removeStockButton = new Button("x");
    removeStockButton.addStyleDependentName("remove");
    removeStockButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
    	  purgeStock(event,price.getSymbol());
      }  

	
    });
   
    stocksFlexTable.setWidget(row, 3, removeStockButton);
    stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
    
    //##############################################temp code
    
  }
  
}
