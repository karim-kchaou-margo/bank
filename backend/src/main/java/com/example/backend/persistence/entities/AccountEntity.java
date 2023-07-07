package com.example.backend.persistence.entities;

import com.example.backend.domain.model.Account;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccountEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double balance;
  @OneToMany private List<TransactionEntity> transactionEntities;

  public Account toDomainModel() {
    return Account.builder()
        .id(id)
        .balance(balance)
        .transactions(
            transactionEntities.stream()
                .map(TransactionEntity::toDomainModel)
                .collect(Collectors.toList()))
        .build();
  }

  public static AccountEntity fromDomainModel(Account account) {
    return AccountEntity.builder()
        .id(account.getId())
        .balance(account.getBalance())
        .transactionEntities(
            account.getTransactions().stream()
                .map(TransactionEntity::fromDomainModel)
                .collect(Collectors.toList()))
        .build();
  }
}
