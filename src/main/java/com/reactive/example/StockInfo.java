package com.reactive.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.experimental.theories.Theories;

import rx.functions.Action0;
import rx.functions.Action1;

public class StockInfo {
	
	private static final Map<String, StockInfo> myMap;
    static
    {
        myMap = new HashMap<String, StockInfo>();
        myMap.put("ORCL:1", new StockInfo("ORCL","5"));
        myMap.put("ORCL:2", new StockInfo("ORCL","5.1"));
        myMap.put("ORCL:3", new StockInfo("ORCL","5.2"));
        myMap.put("ORCL:4", new StockInfo("ORCL","5.6"));
        myMap.put("ORCL:5", new StockInfo("ORCL","5.9"));
        myMap.put("ORCL:6", new StockInfo("ORCL","5.2"));
        myMap.put("ORCL:7", new StockInfo("ORCL","5.8"));
        myMap.put("ORCL:8", new StockInfo("ORCL","5.7"));
        myMap.put("ORCL:9", new StockInfo("ORCL","5.3"));
        myMap.put("ORCL:0", new StockInfo("ORCL","5.0"));
        myMap.put("GOOG:1", new StockInfo("GOOG","5"));
        myMap.put("GOOG:2", new StockInfo("GOOG","6.1"));
        myMap.put("GOOG:3", new StockInfo("GOOG","6.2"));
        myMap.put("GOOG:4", new StockInfo("GOOG","6.6"));
        myMap.put("GOOG:5", new StockInfo("GOOG","6.9"));
        myMap.put("GOOG:6", new StockInfo("GOOG","6.2"));
        myMap.put("GOOG:7", new StockInfo("GOOG","6.8"));
        myMap.put("GOOG:8", new StockInfo("GOOG","6.7"));
        myMap.put("GOOG:9", new StockInfo("GOOG","6.3"));
        myMap.put("GOOG:0", new StockInfo("GOOG","6.0"));
        myMap.put("AAPL:1", new StockInfo("AAPL","5"));
        myMap.put("AAPL:2", new StockInfo("AAPL","7.1"));
        myMap.put("AAPL:3", new StockInfo("AAPL","7.2"));
        myMap.put("AAPL:4", new StockInfo("AAPL","7.6"));
        myMap.put("AAPL:5", new StockInfo("AAPL","7.9"));
        myMap.put("AAPL:6", new StockInfo("AAPL","7.2"));
        myMap.put("AAPL:7", new StockInfo("AAPL","7.8"));
        myMap.put("AAPL:8", new StockInfo("AAPL","7.7"));
        myMap.put("AAPL:9", new StockInfo("AAPL","7.3"));
        myMap.put("AAPL:0", new StockInfo("AAPL","7.0"));
    }
	
	private String stock;
	private String price;
	
	public StockInfo(String stock, String price) {
		this.stock = stock;
		this.price = price;
	}
	
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public static StockInfo fetch(String symbol){
		return getStockPriceTo(symbol);
	}
	
	private static StockInfo getStockPriceTo(String symbol) {
		return myMap.get(symbol+":"+getRandomIndex());
	}

	private static int getRandomIndex(){
		Random r = new Random();
		int Low = 0;
		int High = 9;
		int Result = r.nextInt(High-Low) + Low;
		return Result;
	}
	
	public static void onNext(StockInfo stock){
		
	}
	
	public static void doOnCompleted(Action0 act) {
		act.call();
	}
	
	@Override
	public String toString() {
		return stock+" : "+price;
	}
	
}
