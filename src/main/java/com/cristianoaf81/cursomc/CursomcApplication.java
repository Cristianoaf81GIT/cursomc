package com.cristianoaf81.cursomc;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.domain.Produto;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;
import com.cristianoaf81.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		List<Categoria> items = this.categoriaRepository.findAll();
		
		if (items.size() == 0) {
			cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
			cat2.getProdutos().addAll(Arrays.asList(p2));
			p1.getCategorias().addAll(Arrays.asList(cat1));
			p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
			p3.getCategorias().addAll(Arrays.asList(cat1));
			this.categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
			this.produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		}
	}

}
