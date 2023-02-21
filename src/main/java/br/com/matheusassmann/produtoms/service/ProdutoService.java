package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.exceptions.ObjetoNotFoundException;
import br.com.matheusassmann.produtoms.mapper.ProdutoMapper;
import br.com.matheusassmann.produtoms.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto findById(UUID id) {
        return produtoRepository.findByIdAndSituacaoProdutoIsNotInativo(id).orElseThrow(() -> new ObjetoNotFoundException("Produto nao encontrado!"));
    }

    public Page<Produto> findAll(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Produto save(ProdutoRequest productRequest) {
        return produtoRepository.save(ProdutoMapper.INSTANCE.toProduto(productRequest));
    }

    public Produto update(ProdutoRequest produtoRequest, UUID id) {
        findById(id);
        Produto produto = ProdutoMapper.INSTANCE.toProduto(produtoRequest, id);
        return produtoRepository.save(produto);
    }

    public void delete(UUID id) {
        findById(id);
        produtoRepository.deleteById(id);
    }
}
