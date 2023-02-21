package br.com.matheusassmann.produtoms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AplicaDescontoRequest {

    @NotNull(message = "id do pedido nao pode ser null")
    private UUID id;

    @DecimalMin(value = "1")
    @DecimalMax(value = "60")
    private BigDecimal percentualDesconto;
}
