package com.cristianoaf81.cursomc.services;

import java.util.Optional;
import java.util.function.Supplier;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;
import com.cristianoaf81.cursomc.services.exceptions.DataIntegrityException;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		Supplier<ObjectNotFoundException> exceptionSupplier = () -> {
			String raw = "Objeto não encontrado para o id: %d";
			raw += ", tipo %s";
			String message = String
					.format(raw, id, Categoria.class.getName());			
			return new ObjectNotFoundException(message);
		};
		return obj.orElseThrow(exceptionSupplier);
	}

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return repo.save(obj);
    }

    public void delete(Integer id) {
		find(id);
		// Categoria cat = find(id);
		// if (cat.getProdutos().size() > 0 ){
		// 	throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associados");
		// } else {
		// 	repo.deleteById(id);
		// }
		try {
			repo.deleteById(id);
		}catch( DataIntegrityViolationException | ConstraintViolationException  e ) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associados");
		}
    }
}
