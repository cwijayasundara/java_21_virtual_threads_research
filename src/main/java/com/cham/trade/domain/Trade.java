package com.cham.trade.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("trades")
@Builder
public record  Trade (@Id int tradeId,
                      String symbol,
                      int quantity,
                      double price,
                      String status,
                      String trader,
                      String date) {
    public double getValue() {
        return quantity * price;
    }
}
