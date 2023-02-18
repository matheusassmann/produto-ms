package br.com.matheusassmann.produtoms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
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
    @AssertFalse
    private boolean isService;
}
