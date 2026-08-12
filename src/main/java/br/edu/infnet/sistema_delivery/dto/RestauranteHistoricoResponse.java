package br.edu.infnet.sistema_delivery.dto;

import java.time.LocalDateTime;

public class RestauranteHistoricoResponse {

    private Integer revisao;
    private String tipoOperacao;
    private LocalDateTime dataHora;

    private Long id;
    private String nome;
    private String cozinha;
    private Boolean ativo;

    public Integer getRevisao() { return revisao; }
    public void setRevisao(Integer revisao) { this.revisao = revisao; }

    public String getTipoOperacao() { return tipoOperacao; }
    public void setTipoOperacao(String tipoOperacao) { this.tipoOperacao = tipoOperacao; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCozinha() { return cozinha; }
    public void setCozinha(String cozinha) { this.cozinha = cozinha; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}