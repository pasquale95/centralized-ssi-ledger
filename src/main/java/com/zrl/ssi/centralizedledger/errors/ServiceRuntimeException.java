package com.zrl.ssi.centralizedledger.errors;

import org.springframework.http.HttpStatus;

public class ServiceRuntimeException extends RuntimeException {

  protected final String message;
  protected final int code;
  private final String context;

  public ServiceRuntimeException(HttpStatus httpStatus, String message, Throwable cause) {
    super(message, cause);

    this.code = httpStatus.value();
    this.message = message;
    this.context = cause.getMessage();
  }

  public ServiceRuntimeException(HttpStatus httpStatus, String message, String context) {
    super(message);

    this.code = httpStatus.value();
    this.message = message;
    this.context = context;
  }

  public ServiceRuntimeException(HttpStatus httpStatus, String message) {
    super(message);

    this.code = httpStatus.value();
    this.message = message;
    this.context = null;
  }

  public ServiceRuntimeException(String message, Throwable cause) {
    super(message, cause);

    this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    this.message = message;
    this.context = cause.getMessage();
  }

  public ServiceRuntimeException(String message) {
    super(message);

    this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    this.message = message;
    this.context = null;
  }

  public int getCode() {
    return this.code;
  }

  public String getContext() {
    return this.context;
  }
}
