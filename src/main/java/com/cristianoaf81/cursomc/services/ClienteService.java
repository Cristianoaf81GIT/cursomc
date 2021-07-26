package com.cristianoaf81.cursomc.services;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristianoaf81.cursomc.domain.Cliente;
import com.cristianoaf81.cursomc.repositories.ClienteRepository;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		Supplier<ObjectNotFoundException> exceptionSupplier = () -> {
			String raw = "Objeto não encontrado para o id: %d";
			raw += ", tipo %s";
			String message = String
					.format(raw, id, Cliente.class.getName());			
			return new ObjectNotFoundException(message);
		};
		return obj.orElseThrow(exceptionSupplier);
	}
}
