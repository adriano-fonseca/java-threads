package com.reactive.example;

import java.util.List;

import rx.Observable;

public class StockServer {

	public  static Observable<StockInfo> getFeed(List<String> symbols){
		return Observable.create(subscriber -> {
			//int count = 0;
			while(true) {
				try {
				symbols.stream()
					.map(StockInfo::fetch)
					.forEach(subscriber::onNext);
				//count++;
				//Generate a random error
				if(Math.random() > 0.8) throw new RuntimeException("Ops!!");
				UtilThread.sleep(1000);
				}catch (Exception e) {
					subscriber.onError(e);
				}
				subscriber.onNext(new StockInfo("WHAT", "0.0"));
			}
			//subscriber.onCompleted();
		});
		
	}
	

}
