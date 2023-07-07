package com.example.backend.domain.repositoryPort;

import com.example.backend.domain.model.Account;
import java.util.Optional;

public interface AccountRepositoryPort {
  Optional<Account> findById(Long accountId);

  Account save(Account account);
}
