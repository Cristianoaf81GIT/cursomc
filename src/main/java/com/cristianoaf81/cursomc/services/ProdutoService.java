package com.cristianoaf81.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.domain.Produto;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;
import com.cristianoaf81.cursomc.repositories.ProdutoRepository;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private String errorMessage = "Objeto não encontrado id: %d tipo: %s";
    private String resourceName = ProdutoService.class.getName();

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> opt = produtoRepository.findById(id);
        if (opt.isEmpty()) {
            errorMessage = String.format(errorMessage, id, resourceName);
            throw new ObjectNotFoundException(errorMessage);
        }
        return opt.get();
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
            String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        // return produtoRepository.search(nome, categorias, pageRequest);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
