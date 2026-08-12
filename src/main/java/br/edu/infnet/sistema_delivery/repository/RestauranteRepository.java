package br.edu.infnet.sistema_delivery.repository;

import br.edu.infnet.sistema_delivery.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByCozinhaIgnoreCase(String cozinha);

    List<Restaurante> findByAtivoTrue();

    List<Restaurante> findByNomeContainingIgnoreCase(String trecho);

    long countByCozinhaIgnoreCase(String cozinha);

    @Query("SELECT DISTINCT r FROM Restaurante r, Pedido p WHERE p.restaurante = r")
    List<Restaurante> findComPedidos();
}