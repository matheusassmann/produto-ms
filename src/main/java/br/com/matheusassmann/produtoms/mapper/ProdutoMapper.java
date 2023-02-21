package br.com.matheusassmann.produtoms.mapper;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.dto.response.ProdutoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "descricao", source = "produtoRequest.descricao")
    @Mapping(target = "preco", source = "produtoRequest.preco")
    @Mapping(target = "isService", source = "produtoRequest.isService")
    @Mapping(target = "situacaoProduto", expression = "java(br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto.ATIVO)")
    Produto toProduto(ProdutoRequest produtoRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "produtoRequest.descricao")
    @Mapping(target = "preco", source = "produtoRequest.preco")
    @Mapping(target = "isService", source = "produtoRequest.isService")
    @Mapping(target = "situacaoProduto", source = "produtoRequest.situacaoProduto")
    Produto toProduto(ProdutoRequest produtoRequest, UUID id);

    ProdutoRequest toProdutoRequest(Produto produto);

    ProdutoResponse toProdutoResponse(Produto produto);
}