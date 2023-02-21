package br.com.matheusassmann.produtoms.controller.handler;

import br.com.matheusassmann.produtoms.dto.response.ExceptionResponse;
import br.com.matheusassmann.produtoms.dto.response.FieldExceptionResponse;
import br.com.matheusassmann.produtoms.exceptions.OperacaoInvalidaException;
import br.com.matheusassmann.produtoms.exceptions.ObjetoNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var response = ExceptionResponse.builder()
                .code(status.value())
                .status(status.getReasonPhrase())
                .message("Existem campos com erros")
                .fields(getFieldsExceptionResponse(ex))
                .build();
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ObjetoNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleObjectNotFoundException(ObjetoNotFoundException e) {
        var httpStatus = HttpStatus.NOT_FOUND;
        var response = ExceptionResponse.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(e.getLocalizedMessage())
                .build();

        log.error("ProdutoNotFoundException: {}", response);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(OperacaoInvalidaException.class)
    public ResponseEntity<ExceptionResponse> handleOperacaoInvalidaException(OperacaoInvalidaException e) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        var response = ExceptionResponse.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(e.getLocalizedMessage())
                .build();

        log.error("OperacaoInvalidaException: {}", response);
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = ExceptionResponse.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message("Existe um erro no sistema")
                .build();

        log.error("Internal Server Error: {}", e.getLocalizedMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        var httpStatus = HttpStatus.BAD_REQUEST;
        var response = ExceptionResponse.builder()
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message("Entidade não pode ser excluída devido a dependências")
                .build();

        log.error("Internal Server Error: {}", e.getLocalizedMessage());
        return ResponseEntity.status(httpStatus).body(response);
    }

    private List<FieldExceptionResponse> getFieldsExceptionResponse(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> FieldExceptionResponse.builder()
                        .message(error.getDefaultMessage())
                        .field(error.getField())
                        .parameter(error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }
}
