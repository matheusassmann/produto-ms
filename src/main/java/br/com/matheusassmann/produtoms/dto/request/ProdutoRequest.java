package br.com.matheusassmann.produtoms.dto.request;

import br.com.matheusassmann.produtoms.domain.enums.SituacaoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

    private UUID id;

    @NotBlank(message = "nao pode ser vazio ou nulo")
    private String nome;

    @NotBlank(message = "nao pode ser vazio ou nulo")
    private String descricao;

    @Positive(message = "deve ser maior que 0 e um valor positivo")
    @NotNull(message = "nao pode ser nulo")
    private BigDecimal preco;

    @NotNull(message = "nao pode ser nulo")
    private Boolean isService;
}
