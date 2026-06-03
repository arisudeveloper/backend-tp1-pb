package br.edu.infnet.sistema_delivery.controller;

import br.edu.infnet.sistema_delivery.dto.PedidoRequest;
import br.edu.infnet.sistema_delivery.dto.PedidoResponse;
import br.edu.infnet.sistema_delivery.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return pedidoService.listarTodos();
    }

    @PostMapping
    public PedidoResponse criar(@RequestBody PedidoRequest request) {
        return pedidoService.criarPedido(request);
    }
}