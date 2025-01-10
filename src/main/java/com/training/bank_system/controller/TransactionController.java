package com.training.bank_system.controller;


import com.training.bank_system.DTO.TransactionRequestDTO;
import com.training.bank_system.modelos.Transaction;
import com.training.bank_system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity saveTransaction(@RequestBody TransactionRequestDTO requestDTO){
        Transaction transaction = transactionService.saveTransaction(requestDTO);
        return new ResponseEntity(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTransaction(@PathVariable("id") Long idTransaction){
        return new ResponseEntity<>(transactionService.getTransaction(idTransaction), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllTransactions(){
        return new ResponseEntity(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/by-account")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@RequestParam Long accountId){
        List<Transaction> transactions = transactionService.getAllTransactionsByAccount(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
