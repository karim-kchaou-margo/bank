package com.example.backend.persistence.enumerations;

import com.example.backend.domain.enumerations.Operation;

public enum OperationEntity {
  DEPOSIT,
  WITHDRAWAL;

  public static Operation toDomainOperation(OperationEntity operation) {
    switch (operation) {
      case DEPOSIT:
        return Operation.DEPOSIT;
      case WITHDRAWAL:
        return Operation.WITHDRAWAL;
      default:
        return null;
    }
  }

  public static OperationEntity fromDomainOperation(Operation operation) {
    switch (operation) {
      case DEPOSIT:
        return OperationEntity.DEPOSIT;
      case WITHDRAWAL:
        return OperationEntity.WITHDRAWAL;
      default:
        return null;
    }
  }
}
