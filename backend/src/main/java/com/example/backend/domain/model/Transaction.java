package com.example.backend.domain.model;

import com.example.backend.domain.enumerations.Operation;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
  private Long id;
  private ZonedDateTime date;
  private Double amount;
  private Operation operation;
  private Double balance;
}
