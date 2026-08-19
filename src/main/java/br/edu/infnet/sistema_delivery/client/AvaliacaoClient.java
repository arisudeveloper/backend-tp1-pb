
package br.edu.infnet.sistema_delivery.client;

import br.edu.infnet.sistema_delivery.dto.AvaliacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-avaliacao", url = "http://localhost:8082")
public interface AvaliacaoClient {

    @GetMapping("/avaliacoes/restaurante/{restauranteId}")
    List<AvaliacaoResponse> buscarPorRestaurante(@PathVariable("restauranteId") Long restauranteId);
}