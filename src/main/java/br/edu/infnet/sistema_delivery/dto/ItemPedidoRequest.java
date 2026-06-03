package br.edu.infnet.sistema_delivery.dto;

public class ItemPedidoRequest {
    private String nomePrato;
    private Double precoUnitario;
    private Integer quantidade;

    public String getNomePrato() { return nomePrato; }
    public void setNomePrato(String nomePrato) { this.nomePrato = nomePrato; }
    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}