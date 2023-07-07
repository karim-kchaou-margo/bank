package com.example.backend.persistence.repositories.adapters;

import com.example.backend.domain.model.Account;
import com.example.backend.domain.repositoryPort.AccountRepositoryPort;
import com.example.backend.persistence.entities.AccountEntity;
import com.example.backend.persistence.repositories.jpaRepositories.AccountJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {
  private final AccountJpaRepository accountJpaRepository;

  @Override
  public Optional<Account> findById(Long accountId) {
    return accountJpaRepository.findById(accountId).map(AccountEntity::toDomainModel);
  }

  @Override
  public Account save(Account account) {
    return accountJpaRepository.save(AccountEntity.fromDomainModel(account)).toDomainModel();
  }
}
