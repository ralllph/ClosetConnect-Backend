package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.Trades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trades, Long> {
}
