package com.example.backend.web.dto;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.domain.model.Transaction;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
  private Long id;
  private ZonedDateTime date;
  private Double amount;
  private Operation operation;
  private Double balance;

  public static TransactionResponse response(Transaction transaction) {
    return TransactionResponse.builder()
        .id(transaction.getId())
        .date(transaction.getDate())
        .amount(transaction.getAmount())
        .operation(transaction.getOperation())
        .balance(transaction.getBalance())
        .build();
  }
}
