package br.edu.infnet.sistema_delivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "restaurante")
@Audited
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do restaurante é obrigatório")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank(message = "O tipo de cozinha é obrigatório")
    @Column(nullable = false, length = 60)
    private String cozinha;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCozinha() { return cozinha; }
    public void setCozinha(String cozinha) { this.cozinha = cozinha; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}