/**
 * Licensed Materials - Property of IBM
 * IBM Digital Health Pass PID-TBD
 * (c) Copyright IBM Corporation 2020  All Rights Reserved.
 */

package com.zrl.ssi.centralizedledger.errors;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ServiceRuntimeException {
  public BadRequestException(String message, String context) {
    super(HttpStatus.BAD_REQUEST, message, context);
  }

  public BadRequestException(String message, Throwable e) {
    super(HttpStatus.BAD_REQUEST, message, e);
  }
}
