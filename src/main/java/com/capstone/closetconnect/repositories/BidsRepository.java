package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidsRepository extends JpaRepository<Bid, Long> {
}
