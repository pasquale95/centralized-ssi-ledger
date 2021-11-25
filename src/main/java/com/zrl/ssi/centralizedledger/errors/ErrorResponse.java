package com.zrl.ssi.centralizedledger.errors;

public class ErrorResponse {

  public String error;
  public String message;
  public String path;
  public int status;

  public ErrorResponse(String error, String message, String path, int status) {
    this.error = error;
    this.message = message;
    this.path = path;
    this.status = status;
  }

  public static ErrorResponse create(String error, String message, String path, int status) {
    return new ErrorResponse(error, message, path, status);
  }
}
