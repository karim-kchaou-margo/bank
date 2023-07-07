package com.example.backend.domain.service;

import com.example.backend.domain.model.Transaction;
import java.util.List;

public interface TransactionService {
  List<Transaction> accountHistory(Long accountId);
}
