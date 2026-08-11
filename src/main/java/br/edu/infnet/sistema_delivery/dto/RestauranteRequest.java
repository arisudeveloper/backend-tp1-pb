package br.edu.infnet.sistema_delivery.dto;

public class RestauranteRequest {
    private String nome;
    private String cozinha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCozinha() {
        return cozinha;
    }

    public void setCozinha(String cozinha) {
        this.cozinha = cozinha;
    }
}