package com.cristianoaf81.cursomc;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cristianoaf81.cursomc.domain.Categoria;
import com.cristianoaf81.cursomc.domain.Cidade;
import com.cristianoaf81.cursomc.domain.Cliente;
import com.cristianoaf81.cursomc.domain.Endereco;
import com.cristianoaf81.cursomc.domain.Estado;
import com.cristianoaf81.cursomc.domain.Produto;
import com.cristianoaf81.cursomc.domain.enums.TipoCliente;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;
import com.cristianoaf81.cursomc.repositories.CidadeRepository;
import com.cristianoaf81.cursomc.repositories.ClienteRepository;
import com.cristianoaf81.cursomc.repositories.EnderecoRepository;
import com.cristianoaf81.cursomc.repositories.EstadoRepository;
import com.cristianoaf81.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		List<Categoria> items = this.categoriaRepository.findAll();
		// cidades e estados
		List<Estado> estados = this.estadoRepository.findAll();
		List<Cidade> cidades = this.cidadeRepository.findAll();
		// clientes
		List<Cliente> clientes = this.clienteRepository.findAll();
		// enderecos
		List<Endereco> enderecos = this.enderecoRepository.findAll(); 

		// estado
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		// cidade
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		// relacionamentos estado/cidade
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		// clientes
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		// enderecos
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		if (items.size() == 0) {
			cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
			cat2.getProdutos().addAll(Arrays.asList(p2));
			p1.getCategorias().addAll(Arrays.asList(cat1));
			p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
			p3.getCategorias().addAll(Arrays.asList(cat1));
			this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
			this.produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		}

		if (estados.size() == 0 && cidades.size() == 0) {
			this.estadoRepository.saveAll(Arrays.asList(est1, est2));
			this.cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		}
		
		if (clientes.size() == 0) {
			clienteRepository.saveAll(Arrays.asList(cli1));
		}
		
		if (enderecos.size() == 0) {
			enderecoRepository.saveAll(Arrays.asList(e1,e2));
		}
	}

}