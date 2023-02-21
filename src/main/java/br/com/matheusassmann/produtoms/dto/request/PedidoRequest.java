package br.com.matheusassmann.produtoms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {

    private UUID id;

    @NotNull(message = "lista de produtos nao pode ser vazia")
    private List<ProdutoPedidoRequest> produtos;
}
