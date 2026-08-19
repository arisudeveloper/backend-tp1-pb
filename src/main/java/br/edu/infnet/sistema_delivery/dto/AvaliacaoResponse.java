package br.edu.infnet.sistema_delivery.dto;

import lombok.Data;

@Data
public class AvaliacaoResponse {
    private Long id;
    private Long restauranteId;
    private Integer nota;
    private String comentario;
}