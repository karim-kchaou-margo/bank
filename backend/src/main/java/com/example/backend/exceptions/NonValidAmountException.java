package com.example.backend.exceptions;

public class NonValidAmountException extends RuntimeException {

  public NonValidAmountException() {
    super("Non Valid Amount Exception");
  }
}
