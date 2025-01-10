package com.training.bank_system.repositorio;

import com.training.bank_system.modelos.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.account.accountId = :accountId")
    List<Transaction> findByAccount(@Param("accountId") Long accountId);
}
