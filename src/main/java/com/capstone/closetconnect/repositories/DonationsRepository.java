package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.Donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationsRepository extends JpaRepository<Donations, Long> {
}
