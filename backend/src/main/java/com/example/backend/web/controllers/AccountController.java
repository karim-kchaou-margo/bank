package com.example.backend.web.controllers;

import com.example.backend.domain.service.AccountService;
import com.example.backend.domain.service.TransactionService;
import com.example.backend.web.dto.AccountResponse;
import com.example.backend.web.dto.TransactionResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;
  private final TransactionService transactionService;

  @Transactional
  @PutMapping("/{accountId}/deposit")
  public ResponseEntity<AccountResponse> accountDeposit(
      @PathVariable Long accountId, @RequestBody Double amount) {
    return ResponseEntity.ok(
        AccountResponse.response(accountService.accountDeposit(accountId, amount)));
  }

  @Transactional
  @PutMapping("/{accountId}/withdrawal")
  public ResponseEntity<AccountResponse> accountWithdrawal(
      @PathVariable Long accountId, @RequestBody Double amount) {
    return ResponseEntity.ok(
        AccountResponse.response(accountService.accountWithdrawal(accountId, amount)));
  }

  @Transactional
  @GetMapping("/{accountId}/history")
  public ResponseEntity<List<TransactionResponse>> accountHistory(
      @PathVariable("accountId") Long accountId) {
    return ResponseEntity.ok(
        transactionService.accountHistory(accountId).stream()
            .map(TransactionResponse::response)
            .collect(Collectors.toList()));
  }
}
