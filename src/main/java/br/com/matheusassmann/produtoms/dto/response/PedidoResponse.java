package br.com.matheusassmann.produtoms.dto.response;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import br.com.matheusassmann.produtoms.domain.model.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {

    private UUID id;
    private List<ItemPedido> itemPedido;
    private SituacaoPedido situacaoPedido;
    private BigDecimal percentualDesconto;
}
