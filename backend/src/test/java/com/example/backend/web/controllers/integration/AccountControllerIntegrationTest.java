package com.example.backend.web.controllers.integration;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.persistence.entities.AccountEntity;
import com.example.backend.persistence.entities.TransactionEntity;
import com.example.backend.persistence.enumerations.OperationEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AccountControllerIntegrationTest {

  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;

  @Autowired private EntityManager entityManager;
  private Long accountId;
  private Long transactionId;

  @BeforeEach
  void setUp() {
    ZonedDateTime now = ZonedDateTime.now();
    TransactionEntity transactionToSave =
        TransactionEntity.builder()
            .balance(10.)
            .date(now)
            .amount(10.)
            .operationEntity(OperationEntity.DEPOSIT)
            .build();
    List<TransactionEntity> transactions = new ArrayList<>();
    transactions.add(transactionToSave);
    AccountEntity accountToSave =
        AccountEntity.builder().balance(10.).transactionEntities(transactions).build();
    entityManager.persist(accountToSave);
    accountId = accountToSave.getId();
    transactionId = transactionToSave.getId();
    entityManager.flush();
  }

  @Test
  void accountDeposit() throws Exception {
    mvc.perform(
            put("/accounts/{accountId}/deposit", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(10.0)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.['id']").value(accountId))
        .andExpect(jsonPath("$.['balance']").value(20.))
        .andDo(print());
  }

  @Test
  void accountWithdrawal() throws Exception {
    mvc.perform(
            put("/accounts/{accountId}/withdrawal", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(10.0)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.['id']").value(accountId))
        .andExpect(jsonPath("$.['balance']").value(0.));
  }

  @Test
  void accountHistory() throws Exception {
    mvc.perform(
            get("/accounts/{accountId}/history", accountId).contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id").value(transactionId))
        .andExpect(jsonPath("$[0].amount").value(10.))
        .andExpect(jsonPath("$[0].operation").value(Operation.DEPOSIT.name()))
        .andExpect(jsonPath("$[0].balance").value(10.));
  }
}
