package com.example.backend.domain.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  private Long id;
  private Double balance;
  @Builder.Default private List<Transaction> transactions = new ArrayList<>();
}
