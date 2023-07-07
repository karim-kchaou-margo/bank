package com.example.backend.domain.useCase;

import com.example.backend.domain.model.Transaction;
import java.util.List;

public class TransactionsHistory {
  public static String showHistory(List<Transaction> transactions) {
    StringBuilder result = new StringBuilder();
    for (Transaction transaction : transactions) {
      result.append(transaction.toString());
      result.append('\n');
    }
    return result.toString();
  }
}
