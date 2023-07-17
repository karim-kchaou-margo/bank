package com.example.backend.web.controllers.unit;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.domain.model.Account;
import com.example.backend.domain.model.Transaction;
import com.example.backend.domain.service.AccountService;
import com.example.backend.domain.service.TransactionService;
import com.example.backend.web.controllers.AccountController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
class AccountControllerUnitTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private AccountService accountService;
  @MockBean private TransactionService transactionService;

  private ZonedDateTime dateTime;

  @BeforeEach
  void setUp() {
    dateTime = ZonedDateTime.now();
  }

  @Test
  public void accountDeposit() throws Exception {
    // Given
    when(accountService.accountDeposit(1L, 10.0))
        .thenReturn(Account.builder().id(1L).balance(10.0).build());
    // When
    // Then
    mockMvc
        .perform(
            put("/accounts/{accountId}/deposit", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(10.0)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.['id']").value(1L))
        .andExpect(jsonPath("$.['balance']").value(10.));
  }

  @Test
  void accountWithdrawal() throws Exception {
    // Given
    when(accountService.accountWithdrawal(1L, 10.0))
        .thenReturn(Account.builder().id(1L).balance(0.0).build());
    // When
    // Then
    mockMvc
        .perform(
            put("/accounts/{accountId}/withdrawal", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(10.0)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.['id']").value(1L))
        .andExpect(jsonPath("$.['balance']").value(0.));
  }

  @Test
  void accountHistory() throws Exception {
    // Given
    when(transactionService.accountHistory(1L))
        .thenReturn(
            List.of(
                Transaction.builder()
                    .id(1L)
                    .date(dateTime)
                    .amount(10.)
                    .operation(Operation.DEPOSIT)
                    .balance(10.)
                    .build()));
    // When
    // Then
    mockMvc
        .perform(get("/accounts/{accountId}/history", 1L).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].amount").value(10.))
        .andExpect(jsonPath("$[0].operation").value(Operation.DEPOSIT.name()))
        .andExpect(jsonPath("$[0].balance").value(10.));
  }
}
