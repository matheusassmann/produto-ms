package br.com.matheusassmann.produtoms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionResponse {
    private int code;
    private String status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldExceptionResponse> fields;
}
