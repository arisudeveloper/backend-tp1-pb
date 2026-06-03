package br.edu.infnet.sistema_delivery.controller;

import br.edu.infnet.sistema_delivery.dto.RestauranteRequest;
import br.edu.infnet.sistema_delivery.dto.RestauranteResponse;
import br.edu.infnet.sistema_delivery.service.RestauranteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public List<RestauranteResponse> listar() {
        return restauranteService.listarTodos();
    }

    @PostMapping
    public RestauranteResponse criar(@RequestBody RestauranteRequest request) {
        return restauranteService.salvar(request);
    }
}