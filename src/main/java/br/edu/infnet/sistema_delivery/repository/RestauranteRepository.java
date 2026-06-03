package br.edu.infnet.sistema_delivery.repository;

import br.edu.infnet.sistema_delivery.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}