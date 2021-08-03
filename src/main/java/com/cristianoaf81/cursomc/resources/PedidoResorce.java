package com.cristianoaf81.cursomc.resources;

import com.cristianoaf81.cursomc.domain.Pedido;
import com.cristianoaf81.cursomc.services.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("pedidos")
public class PedidoResorce {
    
    @Autowired
    private PedidoService service;


    @GetMapping(value="/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Pedido pedido = service.buscar(id);
        return ResponseEntity.ok().body(pedido);
    }
    
    
}
