package br.com.matheusassmann.produtoms.controller;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import br.com.matheusassmann.produtoms.dto.request.ProdutoRequest;
import br.com.matheusassmann.produtoms.dto.response.ProdutoResponse;
import br.com.matheusassmann.produtoms.service.ProdutoService;
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
@RequestMapping("api/v1/produtos")
@Api(value = "Produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping()
    @ApiOperation(value = "Insert() Produto")
    public ResponseEntity<ProdutoResponse> insert(@RequestBody @Valid ProdutoRequest produtoRequest, UriComponentsBuilder uriBuilder) {
        Produto product = service.save(produtoRequest);
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(Produto.toResponse(product));
    }

    @GetMapping()
    @ApiOperation(value = "ListAll() Produtos")
    public ResponseEntity<Page<ProdutoResponse>> findAll(Pageable pageable) {
        Page<ProdutoResponse> response = service.findAll(pageable)
                .map(Produto::toResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "FindById() Produto")
    public ResponseEntity<ProdutoResponse> findById(@PathVariable UUID id) {
        Produto product = service.findById(id);
        return ResponseEntity.ok().body(Produto.toResponse(product));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update() Produto")
    public ResponseEntity<ProdutoResponse> update(@RequestBody @Valid ProdutoRequest productRequest, @PathVariable UUID id, UriComponentsBuilder uriBuilder) {
        Produto obj = service.update(Produto.from(productRequest), id);
        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(productRequest.getId()).toUri();
        return ResponseEntity.created(uri).body(Produto.toResponse(obj));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete() Produto")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
