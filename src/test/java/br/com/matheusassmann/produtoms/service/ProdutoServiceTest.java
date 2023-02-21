package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.mapper.ProdutoMapper;
import br.com.matheusassmann.produtoms.mother.ProdutoMother;
import br.com.matheusassmann.produtoms.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class ProdutoServiceTest {

    private ProdutoService fixture;

    @Mock
    private ProdutoRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        fixture = new ProdutoService(repository);
    }

    @Test
    void mustReturnProduto_WhenCallingFindById() {

        //given:
        UUID id = UUID.randomUUID();
        Produto produto = ProdutoMother.getProduto(id);
        when(repository.findByIdAndSituacaoProdutoIsNotInativo(any(UUID.class))).thenReturn(Optional.of(produto));

        //when:
        Produto produtoTeste = fixture.findById(id);

        //then:
        assertEquals(produto, produtoTeste);
        verify(repository).findByIdAndSituacaoProdutoIsNotInativo(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustReturnAllProdutos_WhenCallingFindAll() {

        //given:
        Pageable pageable = Pageable.ofSize(3);
        Produto produto = ProdutoMother.getProduto(UUID.randomUUID());
        Page<Produto> pageProduto = new PageImpl<>(List.of(produto));
        when(repository.findAll(any(Pageable.class))).thenReturn(pageProduto);

        //when:
        Page<Produto> pageProdutoTeste = fixture.findAll(pageable);

        //then:
        assertEquals(pageProduto, pageProdutoTeste);
        verify(repository).findAll(pageable);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustSaveProduto_WhenCallingSaveMethod() {

        //given:
        ProdutoRequest produtoRequest = ProdutoMother.getProdutoRequest(UUID.randomUUID());
        Produto produto = ProdutoMother.getProduto(UUID.randomUUID());
        when(repository.save(any(Produto.class))).thenReturn(produto);

        //when:
        Produto produtoTeste = fixture.save(produtoRequest);

        //then:
        assertEquals(produto, produtoTeste);
        verify(repository).save(ProdutoMapper.INSTANCE.toProduto(produtoRequest));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustUpdateProduto_WhenCallingUpdateMethod() {

        //given:
        UUID id = UUID.randomUUID();
        ProdutoRequest produtoRequest = ProdutoMother.getProdutoRequest(id);
        Produto produto = ProdutoMother.getProduto(id);
        when(repository.findByIdAndSituacaoProdutoIsNotInativo(any(UUID.class))).thenReturn(Optional.of(produto));
        when(repository.save(any(Produto.class))).thenReturn(produto);

        //when:
        Produto produtoTeste = fixture.update(produtoRequest, id);

        //then:
        assertEquals(produto, produtoTeste);
        verify(repository).findByIdAndSituacaoProdutoIsNotInativo(id);
        verify(repository).save(produto);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void mustDeleteProduto_WhenCallingDeleteMethod() {

        //given:
        UUID id = UUID.randomUUID();
        Produto produto = ProdutoMother.getProduto(id);
        when(repository.findByIdAndSituacaoProdutoIsNotInativo(any(UUID.class))).thenReturn(Optional.of(produto));
        doNothing().when(repository).deleteById(any(UUID.class));

        //when:
        fixture.delete(id);

        //then:
        verify(repository).findByIdAndSituacaoProdutoIsNotInativo(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }
}