package com.example.backend.persistence.repositories.adapters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.backend.domain.model.Account;
import com.example.backend.domain.repositoryPort.AccountRepositoryPort;
import com.example.backend.persistence.entities.AccountEntity;
import com.example.backend.persistence.repositories.jpaRepositories.AccountJpaRepository;
import java.util.ArrayList;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@ActiveProfiles("test")
class AccountRepositoryAdapterTest {

  @Autowired EntityManager entityManager;

  @Autowired AccountJpaRepository accountJpaRepository;

  AccountRepositoryPort accountRepositoryAdapter;

  @BeforeEach
  void setUp() {
    accountRepositoryAdapter = new AccountRepositoryAdapter(accountJpaRepository);
  }

  @AfterEach
  void tearDown() {
    entityManager.clear();
  }

  @Test
  void findById() {
    // Given
    AccountEntity savedAccount =
        AccountEntity.builder().balance(0.).transactionEntities(new ArrayList<>()).build();
    entityManager.persist(savedAccount);
    // When
    Optional<Account> result = accountRepositoryAdapter.findById(1L);
    // Then
    Account expected = Account.builder().id(1L).balance(0.).transactions(new ArrayList<>()).build();
    assertThat(result).isEqualTo(Optional.ofNullable(expected));
  }

  @Test
  void save() {
    // Given
    Account accountToSave = Account.builder().balance(0.).transactions(new ArrayList<>()).build();
    // When
    Account result = accountRepositoryAdapter.save(accountToSave);
    Long accountId = result.getId();
    // Then
    Account expected =
        Account.builder().id(accountId).balance(0.).transactions(new ArrayList<>()).build();
    assertThat(result).isEqualTo(expected);
  }
}
