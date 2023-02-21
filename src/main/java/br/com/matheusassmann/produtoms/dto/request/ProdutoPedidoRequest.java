package br.com.matheusassmann.produtoms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoPedidoRequest {

    @NotNull(message = "id do produto é necessário")
    private UUID id;

    @Positive(message = "deve ser maior que 0 e um valor positivo")
    @NotNull(message = "quantidade do produto deve ser maior que 0")
    private Integer quantidade;
}
