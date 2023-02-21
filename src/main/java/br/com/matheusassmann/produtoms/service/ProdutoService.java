package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.repository.PedidoRepository;
import br.com.matheusassmann.produtoms.repository.ProdutoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto findById(UUID id) {
        return produtoRepository.findByIdAndSituacaoProdutoIsNotInativo(id).orElseThrow(() -> new ObjectNotFoundException(id, "Produto nao encontrado!"));
    }

    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto save(ProdutoRequest productRequest) {
        return produtoRepository.save(Produto.from(productRequest));
    }

    public Produto update(Produto product, UUID id) {
        findById(id);
        product.setId(id);
        return produtoRepository.save(product);
    }

    public void delete(UUID id) {
        findById(id);
        produtoRepository.deleteById(id);
    }
}
