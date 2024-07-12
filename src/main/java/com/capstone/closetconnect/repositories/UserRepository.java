package com.capstone.closetconnect.repositories;

import com.capstone.closetconnect.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u JOIN FETCH u.clothingItems WHERE u.id = :userId")
    Page<User> findUserWithClothingItems(@Param("userId") Long userId, Pageable pageable);

}
