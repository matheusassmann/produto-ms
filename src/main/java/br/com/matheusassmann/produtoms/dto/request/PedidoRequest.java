package br.com.matheusassmann.produtoms.dto.request;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    private List<ItemPedidoRequest> itemPedido;

    private SituacaoPedido situacaoPedido;

    private BigDecimal percentualDesconto;

    private BigDecimal valorPedido;

}


