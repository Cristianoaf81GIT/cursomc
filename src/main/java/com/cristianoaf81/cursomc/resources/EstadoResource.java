package com.cristianoaf81.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cristianoaf81.cursomc.domain.Cidade;
import com.cristianoaf81.cursomc.domain.Estado;
import com.cristianoaf81.cursomc.dto.CidadeDTO;
import com.cristianoaf81.cursomc.dto.EstadoDTO;
import com.cristianoaf81.cursomc.services.CidadeService;
import com.cristianoaf81.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {
    @Autowired
    private EstadoService service;
    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = service.findAll();        
        List<EstadoDTO> listDto = list
            .stream()
            .map(x -> new EstadoDTO(x))
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/{estadoId}/cidades",method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(x -> new CidadeDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }
}
