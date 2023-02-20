package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.ItemPedido;
import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
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
        return pedidoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Pedido nao encontrado!"));
    }

    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    public Pedido save(PedidoRequest pedidoRequest) {
        List<ItemPedido> itens = new ArrayList<>();

        pedidoRequest.getProdutos()
                .forEach(p -> {
                    var produto = produtoRepository.findById(p.getId())
                            .orElseThrow(() -> new ProdutoNotFoundException("Produto nao encontrado para o id: " + p.getId()));
                    itens.add(
                            ItemPedidoMapper.INSTANCE.toItemPedido(produto, p.getQuantidade())
                    );
                });

        var pedido = PedidoMapper.INSTANCE.toPedido(pedidoRequest, itens);

        return pedidoRepository.save(pedido);
    }

    public Pedido update(Pedido pedido, UUID id) {
        findById(id);
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    public void delete(UUID id) {
        findById(id);
        pedidoRepository.deleteById(id);
    }
}
