package com.cristianoaf81.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.services.CategoriaService;


@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Categoria>> listar() {
		Categoria cat1 = new Categoria(1, "informática");
		Categoria cat2 = new Categoria(2, "Escritório");
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1);
		lista.add(cat2);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable(value = "id") Integer id) {
		Categoria cat = service.find(id);		
		return ResponseEntity.ok().body(cat);
	}

    @RequestMapping(method=RequestMethod.POST) 
    public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder 
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();
        return ResponseEntity.created(uri).build();      
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
       obj.setId(id);
       obj = service.update(obj);
       return ResponseEntity.noContent().build();
    }

}
