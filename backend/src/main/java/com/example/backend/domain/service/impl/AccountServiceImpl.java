package com.example.backend.domain.service.impl;

import com.example.backend.domain.model.Account;
import com.example.backend.domain.repositoryPort.AccountRepositoryPort;
import com.example.backend.domain.service.AccountService;
import com.example.backend.domain.useCase.AccountOperations;
import com.example.backend.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
  private final AccountRepositoryPort accountRepository;

  @Override
  @Transactional
  public Account accountDeposit(Long accountId, Double amount) {
    Account account =
        accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    return accountRepository.save(AccountOperations.deposit(account, amount));
  }

  @Override
  @Transactional
  public Account accountWithdrawal(Long accountId, Double amount) {
    Account account =
        accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);
    return accountRepository.save(AccountOperations.withdrawal(account, amount));
  }
}
