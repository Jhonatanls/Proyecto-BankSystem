package com.training.bank_system.service;

import com.training.bank_system.DTO.TransactionRequestDTO;
import com.training.bank_system.modelos.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Transaction saveTransaction(TransactionRequestDTO transactionDTO);

    Optional<Transaction> getTransaction(Long transactionId);

    List<Transaction> getAllTransactions();

    List<Transaction> getAllTransactionsByAccount(Long accountId);

}
