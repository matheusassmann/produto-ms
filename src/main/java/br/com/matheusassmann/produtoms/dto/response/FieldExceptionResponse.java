package br.com.matheusassmann.produtoms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldExceptionResponse {

    private final String field;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Object parameter;
}
