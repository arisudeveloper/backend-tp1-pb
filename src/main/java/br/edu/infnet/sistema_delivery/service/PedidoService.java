package br.edu.infnet.sistema_delivery.service;

import br.edu.infnet.sistema_delivery.dto.*;
import br.edu.infnet.sistema_delivery.model.ItemPedido;
import br.edu.infnet.sistema_delivery.model.Pedido;
import br.edu.infnet.sistema_delivery.model.Restaurante;
import br.edu.infnet.sistema_delivery.repository.PedidoRepository;
import br.edu.infnet.sistema_delivery.repository.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final RestauranteRepository restauranteRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         RestauranteRepository restauranteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::transformarEmResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoResponse criarPedido(PedidoRequest request) {
        Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurante não encontrado: id=" + request.getRestauranteId()));

        Pedido pedido = new Pedido();
        pedido.setRestaurante(restaurante);
        pedido.setStatus("CRIADO");

        for (ItemPedidoRequest itemReq : request.getItens()) {
            ItemPedido item = new ItemPedido();
            item.setNomePrato(itemReq.getNomePrato());
            item.setPrecoUnitario(itemReq.getPrecoUnitario());
            item.setQuantidade(itemReq.getQuantidade());
            pedido.adicionarItem(item);
        }

        pedido.calcularValorTotal();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return transformarEmResponse(pedidoSalvo);
    }

    private PedidoResponse transformarEmResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setRestauranteId(pedido.getRestaurante().getId());
        response.setValorTotal(pedido.getValorTotal());
        response.setStatus(pedido.getStatus());

        List<ItemPedidoResponse> itensResp = pedido.getItens().stream().map(item -> {
            ItemPedidoResponse iResp = new ItemPedidoResponse();
            iResp.setId(item.getId());
            iResp.setNomePrato(item.getNomePrato());
            iResp.setPrecoUnitario(item.getPrecoUnitario());
            iResp.setQuantidade(item.getQuantidade());
            return iResp;
        }).collect(Collectors.toList());

        response.setItens(itensResp);
        return response;
    }
}