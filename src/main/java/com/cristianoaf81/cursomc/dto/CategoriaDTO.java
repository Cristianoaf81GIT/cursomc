package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

import com.cristianoaf81.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    Integer id;
    private String nome;

    public CategoriaDTO() {}

    public CategoriaDTO(Categoria obj) {
        id = obj.getId();
        nome = obj.getNome();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
