package br.com.matheusassmann.produtoms.dto.response;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {

    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean isService;
    @JsonIgnore
    private SituacaoProduto situacaoProduto;
}
