package com.example.backend.persistence.repositories.adapters;

import com.example.backend.domain.model.Transaction;
import com.example.backend.domain.repositoryPort.TransactionRepositoryPort;
import com.example.backend.persistence.entities.TransactionEntity;
import com.example.backend.persistence.repositories.jpaRepositories.TransactionJpaRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {
  private final TransactionJpaRepository transactionJpaRepository;

  @Override
  public List<Transaction> findAllByAccountId(Long accountId) {
    return transactionJpaRepository.findAllByAccountEntityId(accountId).stream()
        .map(TransactionEntity::toDomainModel)
        .collect(Collectors.toList());
  }
}
