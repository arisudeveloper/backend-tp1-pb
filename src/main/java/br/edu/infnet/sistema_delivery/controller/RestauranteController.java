package br.edu.infnet.sistema_delivery.controller;

import br.edu.infnet.sistema_delivery.client.AvaliacaoClient;
import br.edu.infnet.sistema_delivery.dto.AvaliacaoResponse;
import br.edu.infnet.sistema_delivery.dto.RestauranteHistoricoResponse;
import br.edu.infnet.sistema_delivery.dto.RestauranteRequest;
import br.edu.infnet.sistema_delivery.dto.RestauranteResponse;
import br.edu.infnet.sistema_delivery.service.HistoricoService;
import br.edu.infnet.sistema_delivery.service.RestauranteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;
    private final HistoricoService historicoService;
    private final AvaliacaoClient avaliacaoClient;

    public RestauranteController(RestauranteService restauranteService,
                                 HistoricoService historicoService,
                                 AvaliacaoClient avaliacaoClient) {
        this.restauranteService = restauranteService;
        this.historicoService = historicoService;
        this.avaliacaoClient = avaliacaoClient;
    }

    @GetMapping
    public List<RestauranteResponse> listar() {
        return restauranteService.listarTodos();
    }

    @PostMapping
    public RestauranteResponse criar(@RequestBody RestauranteRequest request) {
        return restauranteService.salvar(request);
    }

    @PutMapping("/{id}")
    public RestauranteResponse atualizar(@PathVariable Long id,
                                         @RequestBody RestauranteRequest request) {
        return restauranteService.atualizar(id, request);
    }

    @GetMapping("/{id}/historico")
    public List<RestauranteHistoricoResponse> historico(@PathVariable Long id) {
        return historicoService.historicoDoRestaurante(id);
    }

    @GetMapping("/{id}/avaliacoes")
    public List<AvaliacaoResponse> buscarAvaliacoes(@PathVariable Long id) {
        return avaliacaoClient.buscarPorRestaurante(id);
    }
}