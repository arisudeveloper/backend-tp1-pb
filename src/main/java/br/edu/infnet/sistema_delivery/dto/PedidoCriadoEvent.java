package br.edu.infnet.sistema_delivery.dto;

import java.io.Serializable;

public class PedidoCriadoEvent implements Serializable {

    private Long pedidoId;
    private Long restauranteId;
    private Double valorTotal;

    public PedidoCriadoEvent() {
    }

    public PedidoCriadoEvent(Long pedidoId, Long restauranteId, Double valorTotal) {
        this.pedidoId = pedidoId;
        this.restauranteId = restauranteId;
        this.valorTotal = valorTotal;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}