package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.repository.ProdutoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    public Produto findById(UUID id) {
        Produto obj = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Produto nao encontrado!"));
        return obj;
    }

    public List<Produto> findAll() {
        return repository.findAll();
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
