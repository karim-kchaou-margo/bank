package com.example.backend.domain.useCase;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.domain.model.Account;
import com.example.backend.domain.model.Transaction;
import com.example.backend.exceptions.InsufficientBalanceException;
import com.example.backend.exceptions.NonValidAmountException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class AccountOperations {

  public static Account deposit(Account account, Double amount) {
    if (amount < 0) {
      throw new NonValidAmountException();
    }
    if (Objects.isNull(account.getBalance())) {
      account.setBalance((double) 0);
    }
    Transaction transaction =
        Transaction.builder()
            .date(ZonedDateTime.now())
            .operation(Operation.DEPOSIT)
            .amount(amount)
            .balance(account.getBalance() + amount)
            .build();
    account.setBalance(transaction.getBalance());
    if (account.getTransactions().isEmpty()) {
      account.setTransactions(new ArrayList<>());
    }
    account.getTransactions().add(transaction);
    return account;
  }

  public static Account withdrawal(Account account, Double amount) {
    if (amount < 0) {
      throw new NonValidAmountException();
    }
    if (Objects.isNull(account.getBalance())) {
      account.setBalance((double) 0);
    }
    if (account.getBalance() < amount) {
      throw new InsufficientBalanceException();
    }
    Transaction transaction =
        Transaction.builder()
            .date(ZonedDateTime.now())
            .operation(Operation.WITHDRAWAL)
            .amount(amount)
            .balance(account.getBalance() - amount)
            .build();
    account.setBalance(transaction.getBalance());
    if (account.getTransactions().isEmpty()) {
      account.setTransactions(new ArrayList<>());
    }
    account.getTransactions().add(transaction);
    return account;
  }
}
