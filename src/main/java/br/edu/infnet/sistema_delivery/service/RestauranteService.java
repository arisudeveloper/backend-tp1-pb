package br.edu.infnet.sistema_delivery.service;

import br.edu.infnet.sistema_delivery.dto.RestauranteRequest;
import br.edu.infnet.sistema_delivery.dto.RestauranteResponse;
import br.edu.infnet.sistema_delivery.model.Restaurante;
import br.edu.infnet.sistema_delivery.repository.RestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional(readOnly = true)
    public List<RestauranteResponse> listarTodos() {
        return restauranteRepository.findAll()
                .stream()
                .map(this::transformarEmResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestauranteResponse salvar(RestauranteRequest request) {
        if (request.getNome() == null || request.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do restaurante é obrigatório.");
        }

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(request.getNome());
        restaurante.setCozinha(request.getCozinha());

        Restaurante salvo = restauranteRepository.save(restaurante);
        return transformarEmResponse(salvo);
    }

    @Transactional
    public RestauranteResponse atualizar(Long id, RestauranteRequest request) {
        Restaurante r = restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Restaurante não encontrado: id=" + id));

        if (request.getNome() != null && !request.getNome().isEmpty()) {
            r.setNome(request.getNome());
        }
        if (request.getCozinha() != null && !request.getCozinha().isEmpty()) {
            r.setCozinha(request.getCozinha());
        }

        Restaurante salvo = restauranteRepository.save(r);
        return transformarEmResponse(salvo);
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