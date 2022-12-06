package com.example.exchange.rate.service.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class ControllerAdviceHandler {

    @ExceptionHandler(value = Throwable.class)
    @ResponseStatus
    @ResponseBody
    public ResponseEntity<Problem> generalException(Throwable ex) {
        Problem problem = Problem.create()
                .withTitle(ErrorCode.INTERNAL_ERROR_SERVER.getMsg())
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withDetail(ex.getMessage());
        log.error("Internal server error thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    public ResponseEntity<Problem> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Problem problem = Problem.create()
                .withTitle("Not implemented")
                .withStatus(HttpStatus.NOT_IMPLEMENTED)
                .withDetail(ex.getMessage());
        log.error("Not implemented thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.NOT_IMPLEMENTED);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Problem> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Problem problem = Problem.create()
                .withTitle(ErrorCode.INCORRECT_REQUEST_BODY.getMsg())
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail(ex.getMessage());
        log.error("Bad request thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Problem> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Problem problem = Problem.create()
                .withTitle(ErrorCode.INCORRECT_REQUEST_BODY.getMsg())
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail(ex.getMessage());
        log.error("Bad request thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<Problem> constraintViolationException(ConstraintViolationException ex) {
        Problem problem = Problem.create()
                .withTitle(ErrorCode.INCORRECT_REQUEST_BODY.getMsg())
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail(ex.getMessage());
        log.error("Bad request thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    public ResponseEntity<Problem> customException(CustomException ex) {
        Problem problem = Problem.create()
                .withTitle(ex.getCode().getMsg())
                .withStatus(HttpStatus.BAD_REQUEST)
                .withDetail(ex.getMessage());
        log.error("Bad request thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value = HttpStatus.BAD_GATEWAY, code = HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(value = ExternalServiceException.class)
    @ResponseBody
    public ResponseEntity<Problem> externalServiceException(ExternalServiceException ex) {
        Problem problem = Problem.create()
                .withTitle(ex.getService())
                .withStatus(HttpStatus.BAD_GATEWAY)
                .withDetail(ex.getMessage());
        log.error("Bad gateway thrown: {}", problem);
        return new ResponseEntity<>(problem, HttpStatus.BAD_GATEWAY);
    }

}
