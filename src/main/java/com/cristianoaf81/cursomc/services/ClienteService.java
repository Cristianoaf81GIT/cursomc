package com.cristianoaf81.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.net.URI;
import java.awt.image.BufferedImage;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cristianoaf81.cursomc.domain.Cidade;
import com.cristianoaf81.cursomc.domain.Cliente;
import com.cristianoaf81.cursomc.domain.Endereco;
import com.cristianoaf81.cursomc.domain.enums.Perfil;
import com.cristianoaf81.cursomc.domain.enums.TipoCliente;
import com.cristianoaf81.cursomc.dto.ClienteDTO;
import com.cristianoaf81.cursomc.dto.ClienteNewDTO;
import com.cristianoaf81.cursomc.repositories.ClienteRepository;
import com.cristianoaf81.cursomc.repositories.EnderecoRepository;
import com.cristianoaf81.cursomc.security.UserSS;
import com.cristianoaf81.cursomc.services.exceptions.AuthorizationException;
import com.cristianoaf81.cursomc.services.exceptions.DataIntegrityException;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;
// import com.cristianoaf81.cursomc.services.S3Service;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
  @Autowired
  private S3Service s3Service; 
  @Autowired
  private ImageService imageService;
  @Value("${img.prefix.client.profile}")
  private String prefix;
  @Value("${img.profile.size}")
  private Integer size;

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		Optional<Cliente> obj = repo.findById(id);
		Supplier<ObjectNotFoundException> exceptionSupplier = () -> {
			String raw = "Objeto não encontrado para o id: %d";
			raw += ", tipo %s";
			String message = String.format(raw, id, Cliente.class.getName());
			return new ObjectNotFoundException(message);
		};
		return obj.orElseThrow(exceptionSupplier);
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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
			throw new DataIntegrityException("Não é possível excluir, existem pedidos relaciondas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

  public Cliente findByEmail(String email) {
    UserSS user = UserService.authenticated();
    if (user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
      throw new AuthorizationException("Acesso negado");
    }

    Cliente obj = repo.findByEmail(email);
    if (obj == null) {
      throw new ObjectNotFoundException(
        "Cliente não encontrado! Id: " + user.getId() + ", Tipo: "+ Cliente.class.getName()
      );
    }

    return obj;
  }

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente fromDto(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}

	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(
				null,
				objDto.getNome(),
				objDto.getEmail(),
				objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()),
				pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}

		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setCpfOuCnpj(newObj.getCpfOuCnpj());
		newObj.setTipo(newObj.getTipo());
	}

  public URI uploadProfilePicture(MultipartFile multipartFile) {
    UserSS user = UserService.authenticated();
    if (user == null) {
      throw new AuthorizationException("Acesso Negado!");
    }
    
    BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
    String filename = prefix + user.getId() + ".jpg";
    jpgImage = imageService.cropSquare(jpgImage);
    jpgImage = imageService.resize(jpgImage, size);

    return s3Service.uploadFile(
        imageService.getInputStream(jpgImage,"jpg"),
        filename,
        "image"
    );      
  }
}
