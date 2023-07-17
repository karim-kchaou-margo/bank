package com.example.backend.domain.service.impl;

import com.example.backend.domain.model.Transaction;
import com.example.backend.domain.repositoryPort.TransactionRepositoryPort;
import com.example.backend.domain.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepositoryPort transactionRepositoryPort;

  @Override
  @Transactional
  public List<Transaction> accountHistory(Long accountId) {
    return transactionRepositoryPort.findAllByAccountId(accountId);
  }
}
