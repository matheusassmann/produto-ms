package br.com.matheusassmann.produtoms.controller;

import br.com.matheusassmann.produtoms.dto.request.AplicarDescontoRequest;
import br.com.matheusassmann.produtoms.dto.request.FinalizarPedidoRequest;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
import br.com.matheusassmann.produtoms.dto.response.PedidoResponse;
import br.com.matheusassmann.produtoms.mapper.PedidoMapper;
import br.com.matheusassmann.produtoms.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping("/criarPedido")
    public ResponseEntity<PedidoResponse> insert(@RequestBody @Valid PedidoRequest pedidoRequest, UriComponentsBuilder uriBuilder) {
        PedidoResponse response = PedidoMapper.INSTANCE.toPedidoResponse(service.save(pedidoRequest));
        URI uri = uriBuilder.path("/pedidos/{id}").buildAndExpand("").toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/finalizarPedido")
    public ResponseEntity<Void> finalizar(@RequestBody FinalizarPedidoRequest finalizarPedidoRequest){
        service.finalizar(finalizarPedidoRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/aplicarDesconto")
    public ResponseEntity<Void> aplicarDesconto(@RequestBody @Valid AplicarDescontoRequest aplicarDescontoRequest){
        service.aplicarDesconto(aplicarDescontoRequest);
        return ResponseEntity.ok().build();
    }

//    @GetMapping()
//    public ResponseEntity<Page<PedidoResponse>> findAll(Pageable pageable) {
//        Page<PedidoResponse> response = service.findAll(pageable)
//                .map(Pedido::toResponse);
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PedidoResponse> findById(@PathVariable UUID id) {
//        Pedido pedido = service.findById(id);
//        return ResponseEntity.ok().body(Pedido.toResponse(pedido));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PedidoResponse> update(@RequestBody @Valid PedidoRequest pedidoRequest, @PathVariable UUID id, UriComponentsBuilder uriBuilder) {
//        Pedido obj = service.update(Pedido.from(pedidoRequest), id);
//        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(pedidoRequest.getId()).toUri();
//        return ResponseEntity.created(uri).body(Pedido.toResponse(obj));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
