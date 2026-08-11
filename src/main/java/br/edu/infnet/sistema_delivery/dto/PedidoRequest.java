package br.edu.infnet.sistema_delivery.dto;

import java.util.List;

public class PedidoRequest {

    private Long restauranteId;
    private List<ItemPedidoRequest> itens;

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public List<ItemPedidoRequest> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequest> itens) {
        this.itens = itens;
    }
}