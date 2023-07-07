package com.example.backend.web.controllers;

import com.example.backend.persistence.entities.AccountEntity;
import com.example.backend.persistence.entities.TransactionEntity;
import com.example.backend.persistence.enumerations.OperationEntity;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
  @Autowired private MockMvc mvc;

  @BeforeEach
  void setUp() {
    List<TransactionEntity> transactions = new ArrayList<>();
    AccountEntity account =
        AccountEntity.builder().balance(10.).transactionEntities(transactions).build();
    TransactionEntity transaction1 =
        TransactionEntity.builder()
            .date(ZonedDateTime.of(2023, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
            .amount(10.)
            .operationEntity(OperationEntity.DEPOSIT)
            .balance(10.)
            .accountEntity(account)
            .build();
  }

  @Test
  void accountDeposit() throws Exception {
    //        mvc.perform(put("/accounts/{accountId}/deposit", )).
  }

  @Test
  void accountWithdrawal() {}

  @Test
  void accountHistory() {}
}
