package br.com.matheusassmann.produtoms.controller;

import br.com.matheusassmann.produtoms.dto.request.AplicaDescontoRequest;
import br.com.matheusassmann.produtoms.dto.request.CriaPedidoRequest;
import br.com.matheusassmann.produtoms.dto.response.PedidoResponse;
import br.com.matheusassmann.produtoms.mapper.PedidoMapper;
import br.com.matheusassmann.produtoms.service.PedidoService;
import io.swagger.annotations.Api;
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
    public ResponseEntity<PedidoResponse> insert(@RequestBody @Valid CriaPedidoRequest criaPedidoRequest, UriComponentsBuilder uriBuilder) {
        PedidoResponse response = PedidoMapper.INSTANCE.toPedidoResponse(service.save(criaPedidoRequest));
        URI uri = uriBuilder.path("/pedidos/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/finalizarPedido/{id}")
    public ResponseEntity<Void> finalizar(@PathVariable UUID id){
        service.finalizar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/aplicarDesconto")
    public ResponseEntity<Void> aplicarDesconto(@RequestBody @Valid AplicaDescontoRequest aplicaDescontoRequest){
        service.aplicarDesconto(aplicaDescontoRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<Page<PedidoResponse>> findAll(Pageable pageable) {
        Page<PedidoResponse> response = service.findAll(pageable)
                .map(PedidoMapper.INSTANCE::toPedidoResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> findById(@PathVariable UUID id) {
        PedidoResponse response = PedidoMapper.INSTANCE.toPedidoResponse(service.findById(id));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponse> update(@RequestBody @Valid CriaPedidoRequest criaPedidoRequest, @PathVariable UUID id, UriComponentsBuilder uriBuilder) {
        PedidoResponse response = PedidoMapper.INSTANCE.toPedidoResponse(service.update(criaPedidoRequest, id));
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
