package br.com.matheusassmann.produtoms.dto.request;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import br.com.matheusassmann.produtoms.domain.model.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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

    private List<ItemPedido> itemPedido;

    @NotBlank(message = "nao pode ser vazio ou nulo")
    private SituacaoPedido situacaoPedido;

    @Positive(message = "deve ser maior que 0 e um valor positivo")
    @NotNull(message = "nao pode ser nulo")
    private BigDecimal percentualDesconto;
}
