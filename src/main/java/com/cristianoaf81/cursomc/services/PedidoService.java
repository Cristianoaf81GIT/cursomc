package com.cristianoaf81.cursomc.services;

import java.util.Optional;

import com.cristianoaf81.cursomc.domain.Pedido;
import com.cristianoaf81.cursomc.repositories.PedidoRepository;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private String errorMessage = "Objeto não encontrado id: %d tipo: %s";
    private String resourceName = PedidoService.class.getName();

    @Autowired
    private PedidoRepository repository;
    
    public Pedido find(Integer id) {
        Optional<Pedido> opt = repository.findById(id);
        if (opt.isEmpty()) {
            errorMessage = String.format(errorMessage, id, resourceName);
            throw new ObjectNotFoundException(errorMessage);
        }
        return opt.get();
    }
}
