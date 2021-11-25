package com.zrl.ssi.centralizedledger.errors;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ServiceRuntimeException {

  public NotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }

  public NotFoundException(String message, String context) {
    super(HttpStatus.NOT_FOUND, message, context);
  }
}
