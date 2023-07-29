package com.cristianoaf81.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianoaf81.cursomc.domain.Estado;
import com.cristianoaf81.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repo;

    public List<Estado> findAll() {
        return this.repo.findAllByOrderByNome();
    }
    
}
