package com.cham.trade.repository;

import com.cham.trade.domain.Trade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RedisTradeRepository extends CrudRepository<Trade, String> {
    @Override
    <S extends Trade> S save(S entity);
    Optional<Trade> findById(String id);
    List<Trade> findAll();
}
