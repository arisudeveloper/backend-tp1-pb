package br.edu.infnet.sistema_delivery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomePrato;
    private Double precoUnitario;
    private Integer quantidade;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomePrato() { return nomePrato; }
    public void setNomePrato(String nomePrato) { this.nomePrato = nomePrato; }

    public Double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(Double precoUnitario) { this.precoUnitario = precoUnitario; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}