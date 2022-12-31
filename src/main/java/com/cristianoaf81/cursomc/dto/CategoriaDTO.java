package com.cristianoaf81.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cristianoaf81.cursomc.domain.Categoria;

import org.hibernate.validator.constraints.Length;

public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    public CategoriaDTO() {
    }

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
