package com.cristianoaf81.cursomc;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		List<Categoria> items = this.categoriaRepository.findAll();
		if (items.size() == 0) {
			this.categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		}
	}

}
