package com.example.backend.domain.useCase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.domain.model.Account;
import com.example.backend.domain.model.Transaction;
import com.example.backend.exceptions.InsufficientBalanceException;
import com.example.backend.exceptions.NonValidAmountException;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountOperationsTest {
  private ZonedDateTime dateTime;

  @BeforeEach
  void setUp() {
    dateTime = ZonedDateTime.now();
  }

  @Nested
  class WithdrawalTests {
    @Test
    void withdrawalOptimalCondition() {
      // Given
      Account account = Account.builder().balance(20.).build();
      // When
      try (MockedStatic<ZonedDateTime> mockedLocalDateTime =
          Mockito.mockStatic(ZonedDateTime.class)) {
        mockedLocalDateTime.when(ZonedDateTime::now).thenReturn(dateTime);
        Account result = AccountOperations.withdrawal(account, 10.);
        // Then
        Account expected =
            Account.builder()
                .balance(10.)
                .transactions(
                    List.of(
                        Transaction.builder()
                            .operation(Operation.WITHDRAWAL)
                            .balance(10.)
                            .date(dateTime)
                            .amount(10.)
                            .build()))
                .build();
        assertThat(result).isEqualTo(expected);
      }
    }

    @Test
    void withdrawalWithInsufficientBalance() {
      // Given
      Account account = Account.builder().build();
      // When
      // Then
      assertThrows(
          InsufficientBalanceException.class, () -> AccountOperations.withdrawal(account, 10.0));
    }

    @Test
    void withdrawalWithNewEmptyAccount() {
      // Given
      Account account = Account.builder().build();
      // When
      // Then
      assertThrows(
          InsufficientBalanceException.class, () -> AccountOperations.withdrawal(account, 10.0));
    }

    @Test
    void withdrawalWithNonValidAmount() {
      // Given
      Account account = Account.builder().balance(20.).build();
      // When
      // Then
      assertThrows(
          NonValidAmountException.class, () -> AccountOperations.withdrawal(account, -10.0));
    }
  }

  @Nested
  class DepositTests {
    @Test
    void depositOptimalCondition() {
      // Given
      Account account = Account.builder().balance(20.).build();
      // When
      try (MockedStatic<ZonedDateTime> mockedLocalDateTime =
          Mockito.mockStatic(ZonedDateTime.class)) {
        mockedLocalDateTime.when(ZonedDateTime::now).thenReturn(dateTime);
        Account result = AccountOperations.deposit(account, 10.);
        // Then
        Account expected =
            Account.builder()
                .balance(30.)
                .transactions(
                    List.of(
                        Transaction.builder()
                            .operation(Operation.DEPOSIT)
                            .balance(30.)
                            .date(dateTime)
                            .amount(10.)
                            .build()))
                .build();
        assertThat(result).isEqualTo(expected);
      }
    }

    @Test
    void depositWithNewAccount() {
      // Given
      Account account = Account.builder().build();
      // When
      try (MockedStatic<ZonedDateTime> mockedLocalDateTime =
          Mockito.mockStatic(ZonedDateTime.class)) {
        mockedLocalDateTime.when(ZonedDateTime::now).thenReturn(dateTime);
        Account result = AccountOperations.deposit(account, 10.);
        // Then
        Account expected =
            Account.builder()
                .balance(10.)
                .transactions(
                    List.of(
                        Transaction.builder()
                            .operation(Operation.DEPOSIT)
                            .balance(10.)
                            .date(dateTime)
                            .amount(10.)
                            .build()))
                .build();
        assertThat(result).isEqualTo(expected);
      }
    }

    @Test
    void depositWithNonValidAmount() {
      // Given
      Account account = Account.builder().build();
      // When
      // Then
      assertThrows(NonValidAmountException.class, () -> AccountOperations.deposit(account, -10.0));
    }
  }
}
