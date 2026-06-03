package br.edu.infnet.sistema_delivery.service;

import br.edu.infnet.sistema_delivery.dto.*;
import br.edu.infnet.sistema_delivery.model.ItemPedido;
import br.edu.infnet.sistema_delivery.model.Pedido;
import br.edu.infnet.sistema_delivery.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponse> listarTodos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::transformarEmResponse)
                .collect(Collectors.toList());
    }

    public PedidoResponse criarPedido(PedidoRequest request) {
        Pedido pedido = new Pedido();
        pedido.setRestauranteId(request.getRestauranteId());
        pedido.setStatus("CRIADO");

        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoRequest itemReq : request.getItens()) {
            ItemPedido item = new ItemPedido();
            item.setNomePrato(itemReq.getNomePrato());
            item.setPrecoUnitario(itemReq.getPrecoUnitario());
            item.setQuantidade(itemReq.getQuantidade());
            itens.add(item);
        }
        pedido.setItens(itens);

        pedido.calcularValorTotal();

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return transformarEmResponse(pedidoSalvo);
    }

    private PedidoResponse transformarEmResponse(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setRestauranteId(pedido.getRestauranteId());
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