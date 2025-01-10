package com.training.bank_system;

import com.training.bank_system.DTO.TransactionRequestDTO;
import com.training.bank_system.modelos.*;
import com.training.bank_system.repositorio.AccountRepository;
import com.training.bank_system.repositorio.TransactionRepository;
import com.training.bank_system.service.TransactionServiceImpl;
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

class TransactionServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTransaction_ShouldSaveDepositTransaction() {
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setAccountId(1L);
        transactionDTO.setTransactionType(TransactionType.DEPOSITO);
        transactionDTO.setAmount(500.0);

        Account account = new Account();
        account.setAccountId(1L);
        account.setBalance(1000.0);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setTransactionId(1L);
        savedTransaction.setTransactionType(TransactionType.DEPOSITO);
        savedTransaction.setAmount(500.0);
        savedTransaction.setAccount(account);
        savedTransaction.setStatusTransaction(StatusTransaction.EXITOSO);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.saveTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        assertEquals(TransactionType.DEPOSITO, result.getTransactionType());
        assertEquals(StatusTransaction.EXITOSO, result.getStatusTransaction());
        assertEquals(1500.0, account.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void saveTransaction_ShouldSaveWithdrawalTransactionWhenBalanceIsSufficient() {
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setAccountId(1L);
        transactionDTO.setTransactionType(TransactionType.RETIRO);
        transactionDTO.setAmount(500.0);

        Account account = new Account();
        account.setAccountId(1L);
        account.setBalance(1000.0);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setTransactionId(1L);
        savedTransaction.setTransactionType(TransactionType.RETIRO);
        savedTransaction.setAmount(500.0);
        savedTransaction.setAccount(account);
        savedTransaction.setStatusTransaction(StatusTransaction.EXITOSO);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.saveTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        assertEquals(TransactionType.RETIRO, result.getTransactionType());
        assertEquals(StatusTransaction.EXITOSO, result.getStatusTransaction());
        assertEquals(500.0, account.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void saveTransaction_ShouldDeclineWithdrawalTransactionWhenBalanceIsInsufficient() {
        TransactionRequestDTO transactionDTO = new TransactionRequestDTO();
        transactionDTO.setAccountId(1L);
        transactionDTO.setTransactionType(TransactionType.RETIRO);
        transactionDTO.setAmount(1500.0);

        Account account = new Account();
        account.setAccountId(1L);
        account.setBalance(1000.0);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setTransactionId(1L);
        savedTransaction.setTransactionType(TransactionType.RETIRO);
        savedTransaction.setAmount(1500.0);
        savedTransaction.setAccount(account);
        savedTransaction.setStatusTransaction(StatusTransaction.DECLINADO);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = transactionService.saveTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        assertEquals(TransactionType.RETIRO, result.getTransactionType());
        assertEquals(StatusTransaction.DECLINADO, result.getStatusTransaction());
        assertEquals(1000.0, account.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getTransaction_ShouldReturnTransactionIfExists() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1L);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransaction(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getTransactionId());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void getTransaction_ShouldReturnEmptyOptionalIfNotExists() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Transaction> result = transactionService.getTransaction(1L);

        assertFalse(result.isPresent());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTransactions_ShouldReturnListOfTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(1L);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(2L);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    void getAllTransactionsByAccount_ShouldReturnListOfTransactionsForAccount() {
        Transaction transaction1 = new Transaction();
        transaction1.setTransactionId(1L);

        Transaction transaction2 = new Transaction();
        transaction2.setTransactionId(2L);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findByAccount(1L)).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactionsByAccount(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findByAccount(1L);
    }
}