package br.com.matheusassmann.produtoms.mapper;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.dto.response.ProdutoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "descricao", source = "produtoRequest.descricao")
    @Mapping(target = "preco", source = "produtoRequest.preco")
    @Mapping(target = "isService", source = "produtoRequest.isService")
    Produto toProduto(ProdutoRequest produtoRequest);

    ProdutoResponse toProdutoResponse(Produto produto);
}