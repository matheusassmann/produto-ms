package br.com.matheusassmann.produtoms.repository;

import br.com.matheusassmann.produtoms.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    @Query("SELECT p FROM Pedido p WHERE p.situacaoPedido IS NOT 'CANCELADO' AND p.id = :id")
    Optional<Pedido> findByIdAndSituacaoPedidoIsNotCancelado(UUID id);

}
