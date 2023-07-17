package com.example.backend.persistence.repositories.adapters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.domain.model.Transaction;
import com.example.backend.persistence.entities.AccountEntity;
import com.example.backend.persistence.entities.TransactionEntity;
import com.example.backend.persistence.enumerations.OperationEntity;
import com.example.backend.persistence.repositories.jpaRepositories.TransactionJpaRepository;
import java.time.ZonedDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@ActiveProfiles("test")
class TransactionRepositoryAdapterTest {

  @Autowired EntityManager entityManager;

  @Autowired TransactionJpaRepository transactionJpaRepository;

  TransactionRepositoryAdapter transactionRepositoryAdapter;

  @BeforeEach
  void setUp() {
    transactionRepositoryAdapter = new TransactionRepositoryAdapter(transactionJpaRepository);
  }

  @AfterEach
  void tearDown() {
    entityManager.clear();
  }

  @Test
  void findAllByAccountId() {
    // Given
    ZonedDateTime now = ZonedDateTime.now();
    TransactionEntity transactionToSave =
        TransactionEntity.builder()
            .balance(10.)
            .date(now)
            .amount(10.)
            .operationEntity(OperationEntity.DEPOSIT)
            .build();
    AccountEntity accountToSave =
        AccountEntity.builder()
            .balance(10.)
            .transactionEntities(List.of(transactionToSave))
            .build();
    entityManager.persist(accountToSave);
    // When
    List<Transaction> result = transactionRepositoryAdapter.findAllByAccountId(1L);
    // Then
    Transaction transaction =
        Transaction.builder()
            .id(1L)
            .balance(10.)
            .date(now)
            .amount(10.)
            .operation(Operation.DEPOSIT)
            .build();
    List<Transaction> expected = List.of(transaction);
    assertThat(result).isEqualTo(expected);
  }
}
