package com.cristianoaf81.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> find(@PathVariable(value = "id") Integer id) {
		Categoria cat = service.buscar(id);
		if ( cat == null ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(cat);
	}
}
