package com.training.bank_system.service;

import com.training.bank_system.DTO.AccountRequestDTO;
import com.training.bank_system.modelos.Account;
import com.training.bank_system.modelos.User;
import com.training.bank_system.repositorio.AccountRepository;
import com.training.bank_system.repositorio.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    @Autowired
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public Account saveAccount(AccountRequestDTO accountDTO) {
        User user = userRepository.findById(accountDTO.getUserId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Account account = new Account();
        account.setUser(user);
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public boolean deleteAccount(Long idAccount) {
        try {
            accountRepository.deleteById(idAccount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
