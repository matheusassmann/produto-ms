package br.com.matheusassmann.produtoms.controller;

import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
import br.com.matheusassmann.produtoms.dto.response.PedidoResponse;
import br.com.matheusassmann.produtoms.mapper.PedidoMapper;
import br.com.matheusassmann.produtoms.service.PedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/pedidos")
@Api(value = "Pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping("/criarPedido")
    @ApiOperation(value = "Criar Novo Pedido")
    public ResponseEntity<PedidoResponse> insert(@RequestBody @Valid PedidoRequest pedidoRequest, UriComponentsBuilder uriBuilder) {
        PedidoResponse response = PedidoMapper.INSTANCE.toPedidoResponse(service.save(pedidoRequest));
        URI uri = uriBuilder.path("/pedidos/{id}").buildAndExpand("").toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping()
    @ApiOperation(value = "ListAll() Pedidos")
    public ResponseEntity<Page<PedidoResponse>> findAll(Pageable pageable) {
        Page<PedidoResponse> response = service.findAll(pageable)
                .map(Pedido::toResponse);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{id}")
//    @ApiOperation(value = "FindById() Pedido")
//    public ResponseEntity<PedidoResponse> findById(@PathVariable UUID id) {
//        Pedido pedido = service.findById(id);
//        return ResponseEntity.ok().body(Pedido.toResponse(pedido));
//    }

//    @PutMapping("/{id}")
//    @ApiOperation(value = "Update() Pedido")
//    public ResponseEntity<PedidoResponse> update(@RequestBody @Valid PedidoRequest pedidoRequest, @PathVariable UUID id, UriComponentsBuilder uriBuilder) {
//        Pedido obj = service.update(Pedido.from(pedidoRequest), id);
//        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(pedidoRequest.getId()).toUri();
//        return ResponseEntity.created(uri).body(Pedido.toResponse(obj));
//    }

//    @DeleteMapping("/{id}")
//    @ApiOperation(value = "Delete() Pedido")
//    public ResponseEntity<Void> delete(@PathVariable UUID id) {
//        service.delete(id);
//        return ResponseEntity.ok().build();
//    }
}
