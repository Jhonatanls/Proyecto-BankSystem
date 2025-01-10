package com.training.bank_system.DTO;

import com.training.bank_system.modelos.StatusTransaction;
import com.training.bank_system.modelos.Transaction;
import com.training.bank_system.modelos.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequestDTO {

    private Long accountId;
    private TransactionType transactionType;
    private double amount;

}
