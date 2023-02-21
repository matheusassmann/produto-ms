package br.com.matheusassmann.produtoms.mapper;

import br.com.matheusassmann.produtoms.domain.model.ItemPedido;
import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
import br.com.matheusassmann.produtoms.dto.response.PedidoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(target = "id", source = "pedidoRequest.id")
    @Mapping(target = "itemPedido", source = "itens")
    @Mapping(target = "situacaoPedido", expression = "java(br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido.ABERTO)")
    @Mapping(target = "valorPedido", expression = "java(itens.stream().map(i -> i.getPrecoTotal()).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add))")
    Pedido toPedido(PedidoRequest pedidoRequest, List<ItemPedido> itens);

    PedidoResponse toPedidoResponse(Pedido pedido);
}