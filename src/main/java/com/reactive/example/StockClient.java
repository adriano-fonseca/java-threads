package com.reactive.example;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class StockClient {

	public static void main(String[] args) {
		List<String> symbols = Arrays.asList("AAPL", "GOOG", "ORCL");
		
		Observable<StockInfo> feed = StockServer.getFeed(symbols);
		//This is a no blocking operation
		//feed.subscribeOn(Schedulers.computation()).subscribe(System.out::println, System.out::println);
		//UtilThread.sleep(10000);
		//System.out.println("Did I get here");
		feed.onErrorResumeNext(throwable -> {
			System.out.println(throwable);
			//Returning a backup resource
			return new StockServer().getFeed(symbols);
		}).onErrorResumeNext(throwable -> {
			System.out.println(throwable);
			//Returning a backup resource
			return new StockServer().getFeed(symbols);
		})
		.subscribe(System.out::println, System.out::println);
		
		System.out.println("I didn't get here"); //Look line 15 Using Schedulers
//		feed.subscribe(new Subscriber<StockInfo>() {
//
//			@Override
//			public void onCompleted() {
//				System.out.println("No more data...");
//			}
//
//			@Override
//			public void onError(Throwable arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onNext(StockInfo stockInfo) {
//				System.out.println(stockInfo);
//				
//			}
//		});
	}

}
