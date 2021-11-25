/**
 * Licensed Materials - Property of IBM
 * IBM Digital Health Pass PID-TBD
 * (c) Copyright IBM Corporation 2020  All Rights Reserved.
 */

package com.zrl.ssi.centralizedledger.errors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrl.ssi.centralizedledger.constant.Messages;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @ExceptionHandler(value = ServiceRuntimeException.class)
    @ResponseBody
    public ErrorResponse handleServiceRuntimeExceptionBusinessException(ServiceRuntimeException e, HttpServletRequest req, HttpServletResponse res) {
        String userMessage = logException(e, req.getRequestURI());
        res.setStatus(e.getCode());

        HttpStatus status = HttpStatus.valueOf(e.getCode());
        return ErrorResponse.create(
                status.getReasonPhrase(),
                userMessage,
                req.getRequestURI(),
                e.getCode());
    }
    
    @ExceptionHandler(value = HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String handleHttpMediaTypeNotAcceptableException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        String headerValue = req.getHeader(HttpHeaders.ACCEPT);
        String message = String.format("Accept value %s is not supported.  Supported MIME type: %s", headerValue, MediaType.APPLICATION_JSON_VALUE);
        logException(e, message, HttpStatus.NOT_ACCEPTABLE, req.getRequestURI());

        // Cannot send back ErrorResponse JSON because caller specified something else in the header.
        return mapper.writeValueAsString(
                ErrorResponse.create(
                        HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(),
                        message,
                        req.getRequestURI(),
                        HttpStatus.NOT_ACCEPTABLE.value())
                );
    }
    
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public Object handleHttpMediaTypeNotSupportedException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleHttpMessageNotReadableException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.BAD_REQUEST);
    }
        
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public Object handleHttpRequestMethodNotSupportedException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleMissingServletRequestParameterException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleMissingServletRequestPartException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.BAD_REQUEST);
    }
        
    @ExceptionHandler(value = TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleTypeMismatchException(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.BAD_REQUEST);
    }
        
    // catch anything unexpected
    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object handleInternalError(HttpServletRequest req, Throwable e) throws JsonProcessingException {
        return logAndReturnErrorResponse(e, req, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private Object logAndReturnErrorResponse(Throwable e, HttpServletRequest req, HttpStatus status) throws JsonProcessingException {
        String userMessage = logException(e, status, req.getRequestURI());
        
        ErrorResponse errorResponse = ErrorResponse.create(
                status.getReasonPhrase(),
                userMessage,
                req.getRequestURI(),
                status.value());
        
        // If the accept header is not JSON or * then cannot return ErrorResponse JSON - must return a string 
        if (isJsonAcceptHeader(req)) {
            return errorResponse;
        } else {
            return mapper.writeValueAsString(errorResponse);
        }
    }
    
    private boolean isJsonAcceptHeader(HttpServletRequest req) {
        String acceptValue = req.getHeader(HttpHeaders.ACCEPT);
        if (acceptValue == null || acceptValue.isEmpty()) {
            return true;
        }
        MediaType mediaType = MediaType.valueOf(acceptValue); 
        return mediaType.equals(MediaType.APPLICATION_JSON) || mediaType.equals(MediaType.ALL); 
    }
    
    private String logException(ServiceRuntimeException e, String path) {
        return logException(e, null, HttpStatus.valueOf(e.getCode()), path);
    }
    
    private String logException(Throwable e, HttpStatus httpStatus, String path) {
        return logException(e, null, httpStatus, path);
    }
    
    private String logException(Throwable e, String message, HttpStatus httpStatus, String path) {
        boolean isError = httpStatus.value() >= HttpStatus.INTERNAL_SERVER_ERROR.value();
        
        ExceptionDetails exceptionDetails = new ExceptionDetails(e, httpStatus);
                
        String msg = String.format(
                "%s:%s(),message = %s, context = %s",
                exceptionDetails.getClassName(),
                exceptionDetails.getMethodName(),
                e.getLocalizedMessage(),
                exceptionDetails.getContext()
            );

        if (isError) {
            logger.error(msg, e);
        } else {
            logger.warn(msg);
        }
        return exceptionDetails.getUserMessage();
    }
    
    static final class ExceptionDetails {
        private Throwable e;
        private Throwable cause;
        private String userMessage;
        private String jsonErrorPath;
        private String className;
        private String methodName;
        private String context = "NULL";

        ExceptionDetails(Throwable e, HttpStatus httpStatus) {
            this.e = e;
            this.userMessage = httpStatus.value() >= HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ? httpStatus.getReasonPhrase()
                    : e.getLocalizedMessage();
            this.cause = getRootCause(e);
            appendJsonErrorPathToMessage();
            setClassAndMethodName();
        }

        public String getUserMessage() {
            return userMessage;
        }

        public String getClassName() {
            return className;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getContext() {
            return context;
        }
        
        /*
         * ServiceRuntimeException can be wrapped in another exception by Spring Boot
         * and will not be caught by the ServiceRuntimeException ExceptionHandler.
         * This ensures the user friendly ServiceRuntimeException message can be found
         * to be returned to the caller.
         * One example of this is during a request with a bad date format in the JSON body
         * and a ServiceRuntimeException is thrown in RFC339DateSerializer.  
         * */ 
        private Throwable getRootCause(Throwable e) {
            Throwable cause = e;
            while (true)
            {
                if (cause instanceof JsonMappingException) {
                    setJsonErrorPath((JsonMappingException) cause);    
                }
                if (cause instanceof ServiceRuntimeException) {
                    setServiceRuntimeExceptionDetails((ServiceRuntimeException) cause);
                    break;
                }
                
                // The cause of JsonMappingException contains a user friendly message.
                // Do no break because the cause chain may have a ServiceRuntimeException.
                if (cause instanceof JsonMappingException && cause.getCause() != null) {
                    this.userMessage = String.format("%s: %s", Messages.JSON_PARSE_EXCEPTION, cause.getCause().getLocalizedMessage());;
                }
                
                if (cause.getCause() == null || cause.getCause() == cause) {
                    break;
                }
                cause = cause.getCause();
            }
            return cause;
        }
        
        private void setServiceRuntimeExceptionDetails(ServiceRuntimeException e) {
            this.userMessage = e.getLocalizedMessage();
            String context = e.getContext();
            
            if (context != null && !context.isEmpty()) {
                this.context = context; 
            }
        }
        
        private void setJsonErrorPath(JsonMappingException e) {
            this.jsonErrorPath = e.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));
        }
        
        // Adds the error path to the user friendly message in the event of a JsonMappingException
        private void appendJsonErrorPathToMessage() {
            if (this.jsonErrorPath == null || this.jsonErrorPath.length() == 0) {
                return;
            }
            this.userMessage = String.format("%s, through reference chain [%s]", this.userMessage, this.jsonErrorPath);
        }
        
        private void setClassAndMethodName() {
            StackTraceElement[] stackTrace = this.cause != null
                    ? this.cause.getStackTrace()
                    : this.e.getStackTrace();
            
            this.className = stackTrace != null && stackTrace.length > 0
                    ? stackTrace[0].getClassName() : "NULL";
            
            this.methodName = stackTrace != null && stackTrace.length > 0
                    ? stackTrace[0].getMethodName() : "NULL";
        }
    }
}
