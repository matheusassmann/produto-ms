package br.com.matheusassmann.produtoms.domain.model;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import br.com.matheusassmann.produtoms.dto.response.PedidoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<ItemPedido> itemPedido;

    @Enumerated(EnumType.STRING)
    private SituacaoPedido situacaoPedido; //TODO desconto será aplicável SOMENTE se o status do pedido for "ABERTO", caso contrário, nao será elegível;

    private BigDecimal percentualDesconto; //TODO aplicável somente em produtos (isService == false), no valor total do pedido;

    private BigDecimal valorPedido;


//    public static PedidoResponse toResponse(Pedido pedido) {
//        return PedidoResponse.builder()
//                .id(pedido.getId())
//                .itemPedido(pedido.getItemPedido())
//                .situacaoPedido(pedido.getSituacaoPedido())
//                .percentualDesconto(pedido.getPercentualDesconto())
//                .build();
//    }
}
