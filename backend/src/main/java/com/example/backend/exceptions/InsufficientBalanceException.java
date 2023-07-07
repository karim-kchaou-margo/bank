package com.example.backend.exceptions;

public class InsufficientBalanceException extends RuntimeException {

  public InsufficientBalanceException() {
    super("Insufficient Balance Exception");
  }
}
