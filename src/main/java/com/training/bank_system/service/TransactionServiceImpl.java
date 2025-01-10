package com.training.bank_system.service;

import com.training.bank_system.DTO.TransactionRequestDTO;
import com.training.bank_system.modelos.Account;
import com.training.bank_system.modelos.StatusTransaction;
import com.training.bank_system.modelos.Transaction;
import com.training.bank_system.modelos.TransactionType;
import com.training.bank_system.repositorio.AccountRepository;
import com.training.bank_system.repositorio.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(TransactionRequestDTO transactionDTO) {

        Account account = accountRepository.findById(transactionDTO.getAccountId())
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);

        if(transactionDTO.getTransactionType() == TransactionType.RETIRO){

            if(account.getBalance() < transactionDTO.getAmount()){
                transaction.setStatusTransaction(StatusTransaction.DECLINADO);
                accountRepository.save(account);
                return transactionRepository.save(transaction);
            } else {
                account.setBalance(account.getBalance()- transactionDTO.getAmount());
            }
        } else {
            account.setBalance(account.getBalance() + transactionDTO.getAmount());
        }

        transaction.setStatusTransaction(StatusTransaction.EXITOSO);
        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getAllTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccount(accountId);
    }
}
