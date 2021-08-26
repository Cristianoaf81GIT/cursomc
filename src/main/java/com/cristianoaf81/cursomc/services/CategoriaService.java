package com.cristianoaf81.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.dto.CategoriaDTO;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;
import com.cristianoaf81.cursomc.services.exceptions.DataIntegrityException;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		Supplier<ObjectNotFoundException> exceptionSupplier = () -> {
			String raw = "Objeto não encontrado para o id: %d";
			raw += ", tipo %s";
			String message = String.format(raw, id, Categoria.class.getName());
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
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associados");
		}
	}

	public List<Categoria> findAll() {
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria fromDto(CategoriaDTO dto) {
		return new Categoria(dto.getId(), dto.getNome());
	}
}
