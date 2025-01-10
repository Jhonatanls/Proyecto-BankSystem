package com.training.bank_system.controller;

import com.training.bank_system.DTO.AccountRequestDTO;
import com.training.bank_system.modelos.Account;
import com.training.bank_system.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity saveAccount(@RequestBody AccountRequestDTO requestDTO){
        Account account = accountService.saveAccount(requestDTO);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAccount(@PathVariable("id") Long idAccount){
        return new ResponseEntity<>(accountService.getAccount(idAccount), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity getAllAccounts(){
        return new ResponseEntity(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAccount(@PathVariable("id") Long idAccount){
        boolean response = accountService.deleteAccount(idAccount);
        if (response){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
