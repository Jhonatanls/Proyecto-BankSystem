package com.training.bank_system.service;

import com.training.bank_system.DTO.AccountRequestDTO;
import com.training.bank_system.modelos.Account;
import com.training.bank_system.modelos.User;
import com.training.bank_system.repositorio.UserRepository;

import java.util.List;
import java.util.Optional;

public interface AccountService {



    Account saveAccount(AccountRequestDTO accountDTO);

    Optional<Account> getAccount(Long accountId);

    List<Account> getAllAccounts();

    boolean deleteAccount(Long idAccount);
}
