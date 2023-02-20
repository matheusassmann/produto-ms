package br.com.matheusassmann.produtoms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    private UUID id;

    private List<ProdutoPedidoRequest> produtos;

//    @Positive(message = "deve ser maior que 0 e um valor positivo")
//    @NotNull(message = "nao pode ser nulo")
//    private BigDecimal percentualDesconto;
}
