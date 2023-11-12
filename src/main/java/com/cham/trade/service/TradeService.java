package com.cham.trade.service;

import com.cham.trade.domain.Trade;
import com.cham.trade.repository.RedisTradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private RedisTradeRepository redisTradeRepository;

    private ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public void publishTradeUsingNewVirtualThreadPerTaskExecutor(int tradeCount) throws ExecutionException, InterruptedException {
        log.info("Inside TradeService.publishTradeUsingNewVirtualThreadPerTaskExecutor()");
        for (int i = 1; i < tradeCount + 1; i++) {
            Trade trade = getTrade(i);
            Future<?> future = executor.submit(() -> redisTradeRepository.save(trade));
            future.get();
        }
        executor.shutdown();
    }

    public void publishTradeUsingSeparateVirtualThreads(int tradeCount) throws InterruptedException {
        log.info("Inside TradeService.publishTradeUsingSeparateVirtualThreads()");
        for (int i = 1; i < tradeCount +1; i++) {
            Trade trade = getTrade(i);
            Thread thread = Thread.ofVirtual().start(() -> redisTradeRepository.save(trade));
            thread.join();
        }
    }

    public void removeAllRecords(){
        redisTradeRepository.deleteAll();
    }

    public long findTradeListSize(){
        return redisTradeRepository.count();
    }

    private static Trade getTrade(int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Trade trade = Trade
                .builder()
                .tradeId(i)
                .symbol("TSLA")
                .quantity(100)
                .price(100.00)
                .status("NEW")
                .trader("Tom Smith")
                .date(formatter.format(new Date()))
                .build();
        return trade;
    }
}
