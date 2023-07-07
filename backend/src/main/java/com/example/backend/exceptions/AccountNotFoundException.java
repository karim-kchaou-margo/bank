package com.example.backend.exceptions;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException() {
    super("Account Not Found Exception");
  }
}
