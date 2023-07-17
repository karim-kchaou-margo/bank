package com.example.backend.domain.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.backend.domain.enumerations.Operation;
import com.example.backend.domain.model.Account;
import com.example.backend.domain.model.Transaction;
import com.example.backend.domain.repositoryPort.AccountRepositoryPort;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AccountServiceImplTest {
  @Mock private AccountRepositoryPort accountRepositoryPort;
  @Captor ArgumentCaptor<Account> accountArgumentCaptor;

  @InjectMocks AccountServiceImpl accountService;

  private ZonedDateTime dateTime;

  @BeforeEach
  void setUp() {
    dateTime = ZonedDateTime.now();
  }

  @Test
  void accountDeposit() {
    // Given
    Account account = Account.builder().id(1L).balance(0.).build();
    Account expectedAccountToSave =
        Account.builder()
            .id(1L)
            .balance(10.)
            .transactions(
                List.of(
                    Transaction.builder()
                        .date(dateTime)
                        .operation(Operation.DEPOSIT)
                        .amount(10.)
                        .balance(10.)
                        .build()))
            .build();
    when(accountRepositoryPort.findById(1L)).thenReturn(Optional.ofNullable(account));
    when(accountRepositoryPort.save(account)).thenReturn(expectedAccountToSave);
    try (MockedStatic<ZonedDateTime> mockedLocalDateTime =
        Mockito.mockStatic(ZonedDateTime.class)) {
      mockedLocalDateTime.when(ZonedDateTime::now).thenReturn(dateTime);
      // When
      Account result = accountService.accountDeposit(1L, 10.);
      // Then
      verify(accountRepositoryPort).save(accountArgumentCaptor.capture());
      assertThat(accountArgumentCaptor.getValue()).isEqualTo(expectedAccountToSave);
      assertThat(result).isEqualTo(expectedAccountToSave);
    }
  }

  @Test
  void accountWithdrawal() {
    // Given
    Account account = Account.builder().id(1L).balance(10.).build();
    Account expectedAccountToSave =
        Account.builder()
            .id(1L)
            .balance(0.)
            .transactions(
                List.of(
                    Transaction.builder()
                        .date(dateTime)
                        .operation(Operation.WITHDRAWAL)
                        .amount(10.)
                        .balance(0.)
                        .build()))
            .build();
    when(accountRepositoryPort.findById(1L)).thenReturn(Optional.ofNullable(account));
    when(accountRepositoryPort.save(account)).thenReturn(expectedAccountToSave);
    try (MockedStatic<ZonedDateTime> mockedLocalDateTime =
        Mockito.mockStatic(ZonedDateTime.class)) {
      mockedLocalDateTime.when(ZonedDateTime::now).thenReturn(dateTime);
      // When
      Account result = accountService.accountWithdrawal(1L, 10.);
      // Then
      verify(accountRepositoryPort).save(accountArgumentCaptor.capture());
      assertThat(accountArgumentCaptor.getValue()).isEqualTo(expectedAccountToSave);
      assertThat(result).isEqualTo(expectedAccountToSave);
    }
  }
}
