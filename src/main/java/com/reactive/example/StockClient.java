package com.reactive.example;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

public class StockClient {

	public static void main(String[] args) {
		List<String> symbols = Arrays.asList("AAPL", "GOOG", "ORCL");
		
		Observable<StockInfo> feed = StockServer.getFeed2(symbols);
				
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
			return new StockServer().getFeed2(symbols);
		})
		.subscribe(System.out::println, System.out::println, () -> System.out.println("END UP"));
		
		 
		//Closing chanell Look line 15 Using Schedle
		/*feed.subscribe(new Subscriber<StockInfo>() {

			@Override
			public void onCompleted() {
				
			}

			@Override
			public void onError(Throwable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNext(StockInfo stockInfo) {
				System.out.println(stockInfo);
				
			}
		});*/
	}

}
