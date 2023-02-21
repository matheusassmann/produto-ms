package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto;
import br.com.matheusassmann.produtoms.domain.model.ItemPedido;
import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.dto.request.AplicaDescontoRequest;
import br.com.matheusassmann.produtoms.dto.request.CriaPedidoRequest;
import br.com.matheusassmann.produtoms.exceptions.OperacaoInvalidaException;
import br.com.matheusassmann.produtoms.exceptions.ProdutoNotFoundException;
import br.com.matheusassmann.produtoms.mapper.ItemPedidoMapper;
import br.com.matheusassmann.produtoms.mapper.PedidoMapper;
import br.com.matheusassmann.produtoms.repository.PedidoRepository;
import br.com.matheusassmann.produtoms.repository.ProdutoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido findById(UUID id) {
        return pedidoRepository.findByIdAndSituacaoPedidoIsNotCancelado(id).orElseThrow(() -> new ObjectNotFoundException(id, "Pedido nao encontrado!"));
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Pedido save(CriaPedidoRequest criaPedidoRequest) {
        Pedido pedido = criaPedidoHandler(criaPedidoRequest);
        return pedidoRepository.save(pedido);
    }

    public Pedido update(CriaPedidoRequest criaPedidoRequest, UUID id) {
        Pedido pedido = findById(id);
        if (!SituacaoPedido.ABERTO.equals(pedido.getSituacaoPedido())){
            throw new OperacaoInvalidaException("É possível alterar apenas pedidos em ABERTO");
        }
        criaPedidoRequest.setId(id);
        return pedidoRepository.save(criaPedidoHandler(criaPedidoRequest));
    }

    public void delete(UUID id) {
        Pedido pedido = findById(id);
        pedido.setSituacaoPedido(SituacaoPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

    public void finalizar(UUID id) {
        Pedido pedido = findById(id);
        pedido.setSituacaoPedido(SituacaoPedido.FECHADO);
        pedidoRepository.save(pedido);
    }

    public void aplicarDesconto(AplicaDescontoRequest aplicaDescontoRequest) {
        Pedido pedido = findById(aplicaDescontoRequest.getId());
        if (pedido.getSituacaoPedido() != SituacaoPedido.ABERTO) {
            throw new OperacaoInvalidaException("Não é possível aplicar o desconto nesse pedido");
        }
        pedido.setPercentualDesconto(aplicaDescontoRequest.getPercentualDesconto().setScale(2, RoundingMode.HALF_EVEN));
        var valorProdutos = calculaValorProdutos(pedido.getItemPedido());
        var valorDesconto = valorProdutos.multiply(aplicaDescontoRequest.getPercentualDesconto().divide(BigDecimal.valueOf(100)));
        var novoValor = pedido.getValorPedido().subtract(valorDesconto).setScale(2, RoundingMode.HALF_EVEN);
        pedido.setValorPedido(novoValor);
        pedidoRepository.save(pedido);
    }

    private BigDecimal calculaValorProdutos(List<ItemPedido> itemPedidos){
        return itemPedidos.stream().filter(i -> !i.getProduto().getIsService())
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Pedido criaPedidoHandler(CriaPedidoRequest criaPedidoRequest) {
        List<ItemPedido> itens = new ArrayList<>();

        criaPedidoRequest.getProdutos()
                .forEach(p -> {
                    var produto = produtoRepository.findById(p.getId()).filter(s -> s.getSituacaoProduto() != SituacaoProduto.INATIVO)
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado para o id: " + p.getId()));
                    itens.add(
                            ItemPedidoMapper.INSTANCE.toItemPedido(produto, p.getQuantidade())
                    );
                });

        return PedidoMapper.INSTANCE.toPedido(criaPedidoRequest, itens);
    }
}
