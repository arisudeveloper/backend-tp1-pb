package br.edu.infnet.sistema_delivery.service;

import br.edu.infnet.sistema_delivery.dto.RestauranteRequest;
import br.edu.infnet.sistema_delivery.dto.RestauranteResponse;
import br.edu.infnet.sistema_delivery.model.Restaurante;
import br.edu.infnet.sistema_delivery.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public List<RestauranteResponse> listarTodos() {
        return restauranteRepository.findAll()
                .stream()
                .map(this::transformarEmResponse)
                .collect(Collectors.toList());
    }

    public RestauranteResponse salvar(RestauranteRequest request) {
        if (request.getNome() == null || request.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do restaurante é obrigatório.");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(request.getNome());
        restaurante.setCozinha(request.getCozinha());

        Restaurante restauranteSalvo = restauranteRepository.save(restaurante);

        return transformarEmResponse(restauranteSalvo);
    }

    private RestauranteResponse transformarEmResponse(Restaurante restaurante) {
        RestauranteResponse response = new RestauranteResponse();
        response.setId(restaurante.getId());
        response.setNome(restaurante.getNome());
        response.setCozinha(restaurante.getCozinha());
        response.setAtivo(restaurante.getAtivo());
        return response;
    }
}