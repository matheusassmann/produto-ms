package br.com.matheusassmann.produtoms.service;

import br.com.matheusassmann.produtoms.domain.model.Pedido;
import br.com.matheusassmann.produtoms.dto.request.PedidoRequest;
import br.com.matheusassmann.produtoms.repository.PedidoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Pedido nao encontrado!"));
    }

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Pedido save(PedidoRequest pedidoRequest) {
        return repository.save(Pedido.from(pedidoRequest));
    }

    public Pedido update(Pedido pedido, UUID id) {
        findById(id);
        pedido.setId(id);
        return repository.save(pedido);
    }

    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }
}
