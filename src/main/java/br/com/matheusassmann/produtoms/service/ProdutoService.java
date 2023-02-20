package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
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
    private ProdutoRepository repository;

    public Produto findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Produto nao encontrado!"));
    }

    public Page<Produto> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Produto save(ProdutoRequest productRequest) {
        return repository.save(Produto.from(productRequest));
    }

    public Produto update(Produto product, UUID id) {
        findById(id);
        product.setId(id);
        return repository.save(product);
    }

    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }

}
