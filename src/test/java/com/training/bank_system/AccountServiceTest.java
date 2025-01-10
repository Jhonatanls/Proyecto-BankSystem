package com.training.bank_system;

import com.training.bank_system.DTO.AccountRequestDTO;
import com.training.bank_system.modelos.Account;
import com.training.bank_system.modelos.User;
import com.training.bank_system.repositorio.AccountRepository;
import com.training.bank_system.repositorio.UserRepository;
import com.training.bank_system.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAccount_ShouldSaveAccountForValidUser() {
        AccountRequestDTO accountDTO = new AccountRequestDTO();
        accountDTO.setUserId(1L);

        User user = new User();
        user.setUserId(1L);
        user.setName("John Doe");

        Account account = new Account();
        account.setAccountId(1L);
        account.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.saveAccount(accountDTO);

        assertNotNull(result);
        assertEquals(1L, result.getAccountId());
        assertEquals("John Doe", result.getUser().getName());
        verify(userRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void saveAccount_ShouldThrowExceptionForInvalidUser() {
        AccountRequestDTO accountDTO = new AccountRequestDTO();
        accountDTO.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.saveAccount(accountDTO));
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(99L);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void getAccount_ShouldReturnAccountIfExists() {
        Account account = new Account();
        account.setAccountId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Account> result = accountService.getAccount(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getAccountId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getAccount_ShouldReturnEmptyOptionalIfNotExists() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Account> result = accountService.getAccount(1L);

        assertFalse(result.isPresent());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void getAllAccounts_ShouldReturnListOfAccounts() {
        Account account1 = new Account();
        account1.setAccountId(1L);

        Account account2 = new Account();
        account2.setAccountId(2L);

        List<Account> accounts = Arrays.asList(account1, account2);
        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findAll();
    }

    @Test
    void deleteAccount_ShouldReturnTrueIfDeletedSuccessfully() {
        doNothing().when(accountRepository).deleteById(1L);

        boolean result = accountService.deleteAccount(1L);

        assertTrue(result);
        verify(accountRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAccount_ShouldReturnFalseIfExceptionThrown() {
        doThrow(new RuntimeException("Error")).when(accountRepository).deleteById(1L);

        boolean result = accountService.deleteAccount(1L);

        assertFalse(result);
        verify(accountRepository, times(1)).deleteById(1L);
    }

}
