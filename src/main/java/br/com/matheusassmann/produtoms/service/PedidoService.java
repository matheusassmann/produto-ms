package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoPedido;
import br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto;
import br.com.matheusassmann.produtoms.domain.model.ItemPedido;
import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.dto.request.AplicarDescontoRequest;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
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

    public Pedido save(PedidoRequest pedidoRequest) {
        List<ItemPedido> itens = new ArrayList<>();

        pedidoRequest.getProdutos()
                .forEach(p -> {
                    var produto = produtoRepository.findById(p.getId()).filter(s -> s.getSituacaoProduto() != SituacaoProduto.INATIVO)
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado para o id: " + p.getId()));
                    itens.add(
                            ItemPedidoMapper.INSTANCE.toItemPedido(produto, p.getQuantidade())
                    );
                });

        var pedido = PedidoMapper.INSTANCE.toPedido(pedidoRequest, itens);

        return pedidoRepository.save(pedido);
    }

    public Pedido update(PedidoRequest pedidoRequest) {
        Pedido pedido = findById(pedidoRequest.getId());
        pedido.setId(pedidoRequest.getId());
        return pedidoRepository.save(pedido);
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

    public void aplicarDesconto(AplicarDescontoRequest aplicarDescontoRequest) {
        Pedido pedido = findById(aplicarDescontoRequest.getId());
        if (pedido.getSituacaoPedido() != SituacaoPedido.ABERTO) {
            throw new OperacaoInvalidaException("Não é possível aplicar o desconto nesse pedido");
        }
        pedido.setPercentualDesconto(aplicarDescontoRequest.getPercentualDesconto().setScale(2, RoundingMode.HALF_EVEN));
        var valorDesconto = pedido.getValorPedido().multiply(aplicarDescontoRequest.getPercentualDesconto().divide(BigDecimal.valueOf(100)));
        var novoValor = pedido.getValorPedido().subtract(valorDesconto).setScale(2, RoundingMode.HALF_EVEN);
        pedido.setValorPedido(novoValor);
        pedidoRepository.save(pedido);
    }
}
