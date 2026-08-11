package br.edu.infnet.sistema_delivery.service;

import br.edu.infnet.sistema_delivery.dto.RestauranteHistoricoResponse;
import br.edu.infnet.sistema_delivery.model.Restaurante;
import br.edu.infnet.sistema_delivery.repository.RestauranteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HistoricoServiceTest {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionTemplate tx;

    @AfterEach
    void limparBanco() {
        if (tx == null) tx = new TransactionTemplate(transactionManager);
        tx.executeWithoutResult(status -> restauranteRepository.deleteAll());
    }

    @Test
    void deveRegistrarRevisoesCriadoEAtualizado() {
        tx = new TransactionTemplate(transactionManager);

        Long id = tx.execute(status -> {
            Restaurante r = new Restaurante();
            r.setNome("pizzaria1");
            r.setCozinha("Italiana");
            r.setAtivo(true);
            return restauranteRepository.save(r).getId();
        });

        tx.executeWithoutResult(status -> {
            Restaurante r = restauranteRepository.findById(id).orElseThrow();
            r.setNome("pizzaria1 renovada");
            restauranteRepository.save(r);
        });

        List<RestauranteHistoricoResponse> historico =
                historicoService.historicoDoRestaurante(id);

        assertThat(historico).hasSize(2);
        assertThat(historico.get(0).getTipoOperacao()).isEqualTo("CRIADO");
        assertThat(historico.get(0).getNome()).isEqualTo("pizzaria1");
        assertThat(historico.get(1).getTipoOperacao()).isEqualTo("ATUALIZADO");
        assertThat(historico.get(1).getNome()).isEqualTo("pizzaria1 renovada");
    }

    @Test
    void deveRetornarHistoricoVazioParaRestauranteInexistente() {
        List<RestauranteHistoricoResponse> historico =
                historicoService.historicoDoRestaurante(9999L);
        assertThat(historico).isEmpty();
    }
}