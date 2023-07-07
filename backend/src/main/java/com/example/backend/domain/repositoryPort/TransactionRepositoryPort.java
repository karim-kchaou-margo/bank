package com.example.backend.domain.repositoryPort;

import com.example.backend.domain.model.Transaction;
import java.util.List;

public interface TransactionRepositoryPort {
  List<Transaction> findAllByAccountId(Long accountId);
}
