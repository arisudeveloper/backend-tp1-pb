package br.edu.infnet.sistema_delivery.repository;

import br.edu.infnet.sistema_delivery.model.Restaurante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class RestauranteRepositoryTest {

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Restaurante novoRestaurante(String nome, String cozinha) {
        Restaurante r = new Restaurante();
        r.setNome(nome);
        r.setCozinha(cozinha);
        r.setAtivo(true);
        return r;
    }

    @Test
    void deveSalvarERecuperarRestaurante() {
        Restaurante salvo = restauranteRepository.save(novoRestaurante("pizzaria1", "Italiana"));

        assertThat(salvo.getId()).isNotNull();

        Optional<Restaurante> encontrado = restauranteRepository.findById(salvo.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("pizzaria1");
        assertThat(encontrado.get().getCozinha()).isEqualTo("Italiana");
        assertThat(encontrado.get().getAtivo()).isTrue();
    }

    @Test
    void deveAtualizarRestaurante() {
        Restaurante salvo = restauranteRepository.save(novoRestaurante("japonesa1", "Japonesa"));

        salvo.setNome("japonesa2");
        restauranteRepository.save(salvo);

        Restaurante recuperado = restauranteRepository.findById(salvo.getId()).orElseThrow();
        assertThat(recuperado.getNome()).isEqualTo("japonesa2");
    }

    @Test
    void deveDeletarRestaurante() {
        Restaurante salvo = restauranteRepository.save(novoRestaurante("chinesa1", "Chinesa"));
        Long id = salvo.getId();

        restauranteRepository.deleteById(id);

        assertThat(restauranteRepository.findById(id)).isEmpty();
    }

    @Test
    void deveBuscarPorCozinhaIgnorandoCase() {
        restauranteRepository.save(novoRestaurante("pizzaria1", "Italiana"));
        restauranteRepository.save(novoRestaurante("pizzaria2", "italiana"));
        restauranteRepository.save(novoRestaurante("japonesa1", "Japonesa"));

        List<Restaurante> italianos = restauranteRepository.findByCozinhaIgnoreCase("ITALIANA");
        assertThat(italianos).hasSize(2);
    }

    @Test
    void deveBuscarApenasAtivos() {
        Restaurante ativo = novoRestaurante("pizzaria1", "Italiana"); ativo.setAtivo(true);
        Restaurante inativo = novoRestaurante("pizzaria2", "Italiana"); inativo.setAtivo(false);
        restauranteRepository.save(ativo);
        restauranteRepository.save(inativo);

        List<Restaurante> ativos = restauranteRepository.findByAtivoTrue();
        assertThat(ativos).extracting(Restaurante::getNome).containsExactly("pizzaria1");
    }

    @Test
    void deveBuscarPorNomeContendoTrecho() {
        restauranteRepository.save(novoRestaurante("pizzaria1", "Italiana"));
        restauranteRepository.save(novoRestaurante("pizzaria2", "Italiana"));
        restauranteRepository.save(novoRestaurante("japonesa1", "Japonesa"));

        List<Restaurante> comPizza = restauranteRepository.findByNomeContainingIgnoreCase("pizza");
        assertThat(comPizza).hasSize(2);
    }
}