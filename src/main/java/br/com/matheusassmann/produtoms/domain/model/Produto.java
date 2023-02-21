package br.com.matheusassmann.produtoms.domain.model;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.dto.response.ProdutoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")
public class Produto {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean isService;

    @Enumerated(EnumType.STRING)
    private SituacaoProduto situacaoProduto;

    public static Produto from(ProdutoRequest request) {
        return Produto.builder()
                .id(request.getId())
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .preco(request.getPreco())
                .isService(request.getIsService())
                .situacaoProduto(SituacaoProduto.ATIVO)
                .build();
    }

    public static ProdutoResponse toResponse(Produto produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .isService(produto.getIsService())
                .situacaoProduto(produto.situacaoProduto)
                .build();
    }
}
