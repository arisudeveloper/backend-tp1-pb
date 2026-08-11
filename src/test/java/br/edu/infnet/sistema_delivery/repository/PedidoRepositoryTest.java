package br.edu.infnet.sistema_delivery.repository;

import br.edu.infnet.sistema_delivery.model.ItemPedido;
import br.edu.infnet.sistema_delivery.model.Pedido;
import br.edu.infnet.sistema_delivery.model.Restaurante;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Restaurante criarRestaurante() {
        Restaurante r = new Restaurante();
        r.setNome("Cantina Teste");
        r.setCozinha("Italiana");
        r.setAtivo(true);
        return restauranteRepository.save(r);
    }

    private Pedido criarPedidoCom(Restaurante r, String status, String nomePrato, double preco, int qtd) {
        Pedido p = new Pedido();
        p.setRestaurante(r);
        p.setStatus(status);

        ItemPedido item = new ItemPedido();
        item.setNomePrato(nomePrato);
        item.setPrecoUnitario(preco);
        item.setQuantidade(qtd);
        p.adicionarItem(item);

        p.calcularValorTotal();
        return pedidoRepository.save(p);
    }

    @Test
    void deveSalvarPedidoComItensECalcularValorTotal() {
        Restaurante r = criarRestaurante();
        Pedido salvo = criarPedidoCom(r, "CRIADO", "Pizza", 45.0, 2);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getRestaurante().getId()).isEqualTo(r.getId());
        assertThat(salvo.getItens()).hasSize(1);
        assertThat(salvo.getValorTotal()).isEqualTo(90.0);
        assertThat(salvo.getDataCriacao()).isNotNull();
    }

    @Test
    void deveBuscarPedidosPorRestaurante() {
        Restaurante r1 = criarRestaurante();
        Restaurante r2 = criarRestaurante();
        criarPedidoCom(r1, "CRIADO", "A", 10.0, 1);
        criarPedidoCom(r1, "CRIADO", "B", 20.0, 1);
        criarPedidoCom(r2, "CRIADO", "C", 30.0, 1);

        List<Pedido> pedidosR1 = pedidoRepository.findByRestauranteId(r1.getId());
        assertThat(pedidosR1).hasSize(2);
    }

    @Test
    void deveBuscarPedidosPorStatus() {
        Restaurante r = criarRestaurante();
        criarPedidoCom(r, "CRIADO", "A", 10.0, 1);
        criarPedidoCom(r, "ENTREGUE", "B", 20.0, 1);

        assertThat(pedidoRepository.findByStatus("CRIADO")).hasSize(1);
        assertThat(pedidoRepository.findByStatus("ENTREGUE")).hasSize(1);
        assertThat(pedidoRepository.countByStatus("CRIADO")).isEqualTo(1);
    }

    @Test
    void deveCalcularFaturamentoDoRestaurante() {
        Restaurante r = criarRestaurante();
        criarPedidoCom(r, "CRIADO", "Pizza", 50.0, 2);
        criarPedidoCom(r, "CRIADO", "Refri", 8.0, 3);

        Double faturamento = pedidoRepository.faturamentoDoRestaurante(r.getId());
        assertThat(faturamento).isEqualTo(124.0);
    }

    @Test
    void deveRemoverItensJuntoComPedido() {
        Restaurante r = criarRestaurante();
        Pedido p = criarPedidoCom(r, "CRIADO", "X", 10.0, 1);
        Long pedidoId = p.getId();

        pedidoRepository.deleteById(pedidoId);

        assertThat(pedidoRepository.findById(pedidoId)).isEmpty();
    }
}