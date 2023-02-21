package br.com.matheusassmann.produtoms.repository;

import br.com.matheusassmann.produtoms.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("SELECT p FROM Produto p WHERE p.situacaoProduto IS NOT 'INATIVO' AND p.id = :id")
    Optional<Produto> findByIdAndSituacaoProdutoIsNotInativo(UUID id);
}
