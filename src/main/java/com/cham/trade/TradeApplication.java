package com.cham.trade;

import com.cham.trade.service.TradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@Slf4j
public class TradeApplication  implements CommandLineRunner {
	@Autowired
	private TradeService tradeService;
	private final int TRADE_COUNT = 100000;

	private final long NANO_SECONDS = 5_000_000_000L;
	private final long THREAD_WAIT_TIME = 120000;

	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
	}

	@Override
	public void run(String... args) throws InterruptedException, ExecutionException {
//		run this one at a time
		publishTradeUsingNewVirtualThreadPerTaskExecutor();
//		publishTradeUsingSeparateVirtualThreads();
	}

	private void publishTradeUsingNewVirtualThreadPerTaskExecutor() throws InterruptedException, ExecutionException {
		log.info("Inside TradeApplication.publishToRedisUsingSeparateVirtualThreads()");
		long startTime = System.nanoTime();
		tradeService.publishTradeUsingNewVirtualThreadPerTaskExecutor(TRADE_COUNT);
		long endTime   = System.nanoTime();
		double elipsedTime = (double)(endTime - startTime) / NANO_SECONDS;
		log.info("Time taken to persist " + TRADE_COUNT + " trades is " + elipsedTime + " seconds using separate virtual " +
				"threads");
		cleanUp();
	}

	private void publishTradeUsingSeparateVirtualThreads() throws InterruptedException {
		log.info("Inside TradeApplication.publishTradeUsingSeparateVirtualThreads()");
		long startTime = System.nanoTime();
		tradeService.publishTradeUsingSeparateVirtualThreads(TRADE_COUNT);
		long endTime   = System.nanoTime();
		double elipsedTime = (double)(endTime - startTime) / NANO_SECONDS;
		log.info("Time taken to persist " + TRADE_COUNT + " trades is " + elipsedTime + " seconds using " +
				"Thread.ofVirtual().start()");
		cleanUp();
	}

	private void cleanUp() throws InterruptedException {
		Thread.sleep(THREAD_WAIT_TIME);
		findTradeListSizeInRedis();
		tradeService.removeAllRecords();
	}

	private void findTradeListSizeInRedis(){
		log.info("Trade List in Redis is " + tradeService.findTradeListSize());
	}

}
