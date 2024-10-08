package com.financial.domain.fin.user.repository;

import com.financial.domain.fin.user.model.FinUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinUserRepository extends JpaRepository<FinUser, String> {
    Optional<FinUser> findByPhoneNumber(String phone);
}
