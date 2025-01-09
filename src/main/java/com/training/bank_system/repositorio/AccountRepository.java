package com.training.bank_system.repositorio;

import com.training.bank_system.modelos.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long>{

}

