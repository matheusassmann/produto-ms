package br.com.matheusassmann.produtoms.domain.model;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
import br.com.matheusassmann.produtoms.dto.response.PedidoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedido;

    @Enumerated(EnumType.STRING)
    private SituacaoPedido situacaoPedido; //TODO desconto será aplicável SOMENTE se o status do pedido for "ABERTO", caso contrário, nao será elegível;

    private BigDecimal percentualDesconto; //TODO aplicável somente em produtos (isService == false), no valor total do pedido;

    private BigDecimal valorPedido;

    public static Pedido from(PedidoRequest request) {
        return Pedido.builder()
                .id(request.getId())
                .itemPedido(request.getItemPedido())
                .situacaoPedido(request.getSituacaoPedido())
                .percentualDesconto(request.getPercentualDesconto())
                .build();
    }

    public static PedidoResponse toResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .itemPedido(pedido.getItemPedido())
                .situacaoPedido(pedido.getSituacaoPedido())
                .percentualDesconto(pedido.getPercentualDesconto())
                .build();
    }

    public static List<PedidoResponse> toResponse(List<Pedido> pedidos) {
        List<PedidoResponse> listPedidos = new ArrayList<>();
        pedidos.forEach(pedido -> listPedidos.add(Pedido.toResponse(pedido)));
        return listPedidos;
    }
}