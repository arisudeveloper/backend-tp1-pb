package br.edu.infnet.sistema_delivery.repository;

import br.edu.infnet.sistema_delivery.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByRestauranteId(Long restauranteId);

    List<Pedido> findByStatus(String status);

    List<Pedido> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT COALESCE(SUM(p.valorTotal), 0) FROM Pedido p WHERE p.restaurante.id = :restauranteId")
    Double faturamentoDoRestaurante(@Param("restauranteId") Long restauranteId);

    long countByStatus(String status);
}