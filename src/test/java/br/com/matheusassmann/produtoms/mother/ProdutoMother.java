package br.com.matheusassmann.produtoms.mother;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto;
import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.mapper.ProdutoMapper;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class ProdutoMother {

    public static Produto getProduto(UUID id) {
        return Produto.builder()
                .id(id)
                .nome("Produto Teste")
                .descricao("Descricao produto teste")
                .preco(BigDecimal.valueOf(100.00))
                .isService(false)
                .situacaoProduto(SituacaoProduto.ATIVO)
                .build();
    }

    public static ProdutoRequest getProdutoRequest(UUID id) {
        return ProdutoMapper.INSTANCE.toProdutoRequest(getProduto(id));
    }
}
