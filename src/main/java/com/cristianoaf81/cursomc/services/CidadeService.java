package com.cristianoaf81.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianoaf81.cursomc.domain.Cidade;
import com.cristianoaf81.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
    @Autowired
    private CidadeRepository repo;

    public List<Cidade> findByEstado(Integer estadoId) {
        return this.repo.findCidades(estadoId);
    }
    
}
