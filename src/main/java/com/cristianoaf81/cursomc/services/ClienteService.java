package com.cristianoaf81.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.cristianoaf81.cursomc.domain.Cliente;
import com.cristianoaf81.cursomc.dto.ClienteDTO;
import com.cristianoaf81.cursomc.repositories.ClienteRepository;
import com.cristianoaf81.cursomc.services.exceptions.DataIntegrityException;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		Supplier<ObjectNotFoundException> exceptionSupplier = () -> {
			String raw = "Objeto não encontrado para o id: %d";
			raw += ", tipo %s";
			String message = String.format(raw, id, Cliente.class.getName());
			return new ObjectNotFoundException(message);
		};
		return obj.orElseThrow(exceptionSupplier);
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new DataIntegrityException("Não é possível excluir, existem entidades relaciondas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDto(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpfOuCnpj(newObj.getCpfOuCnpj());
		newObj.setTipo(newObj.getTipo());
	}
}
