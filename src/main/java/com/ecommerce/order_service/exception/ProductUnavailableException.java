package com.ecommerce.order_service.exception;

public class ProductUnavailableException extends RuntimeException {
  public ProductUnavailableException(String message) {
    super(message);
  }

  public ProductUnavailableException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductUnavailableException(Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    return "ProductUnavailableException: " + getMessage();
  }
}
