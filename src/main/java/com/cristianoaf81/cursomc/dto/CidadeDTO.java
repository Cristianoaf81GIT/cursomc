package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

import com.cristianoaf81.cursomc.domain.Cidade;

public class CidadeDTO implements Serializable {

    private static final long serialVersionUID = 273636474L;

    private Integer id;
    private String nome;

    public CidadeDTO() {}

    public CidadeDTO(Cidade obj) {
        id = obj.getId();
        nome = obj.getNome();
    }
    
    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
