package com.example.backend.web.dto;

import com.example.backend.domain.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
  private Long id;
  private Double balance;

  public static AccountResponse response(Account account) {
    return AccountResponse.builder().id(account.getId()).balance(account.getBalance()).build();
  }
}
