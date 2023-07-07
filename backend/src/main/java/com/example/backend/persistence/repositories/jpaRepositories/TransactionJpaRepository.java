package com.example.backend.persistence.repositories.jpaRepositories;

import com.example.backend.persistence.entities.TransactionEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
  List<TransactionEntity> findAllByAccountEntityId(Long accountId);
}
