package com.training.bank_system.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column()
    private double balance = 0.0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    private void generateAccountNumber(){
        if(StringUtils.isBlank(this.accountNumber)){
            Random random = new Random();
            BigInteger randomNumber = BigInteger.valueOf(10000000 + random.nextInt(90000000));
            this.accountNumber = randomNumber.toString();
        }
    }



}
