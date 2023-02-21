package br.com.matheusassmann.produtoms.controller;

import br.com.matheusassmann.produtoms.config.interfaces.ApiPageable;
import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.dto.response.ProdutoResponse;
import br.com.matheusassmann.produtoms.mapper.ProdutoMapper;
import br.com.matheusassmann.produtoms.service.ProdutoService;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping()
    public ResponseEntity<ProdutoResponse> insert(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        ProdutoResponse response = ProdutoMapper.INSTANCE.toProdutoResponse(service.save(produtoRequest));
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping()
    @ApiPageable
    public ResponseEntity<Page<ProdutoResponse>> findAll(@ApiIgnore Pageable pageable) {
        Page<ProdutoResponse> response = service.findAll(pageable)
                .map(Produto::toResponse);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable UUID id) {
        Produto product = service.findById(id);
        return ResponseEntity.ok().body(Produto.toResponse(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> update(@RequestBody @Valid ProdutoRequest productRequest, @PathVariable UUID id, UriComponentsBuilder uriBuilder) {
        Produto obj = service.update(Produto.from(productRequest), id);
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(productRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(Produto.toResponse(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
