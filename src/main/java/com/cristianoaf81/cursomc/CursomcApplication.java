package com.cristianoaf81.cursomc;

import java.text.SimpleDateFormat;
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
import com.cristianoaf81.cursomc.domain.ItemPedido;
import com.cristianoaf81.cursomc.domain.Pagamento;
import com.cristianoaf81.cursomc.domain.PagamentoComBoleto;
import com.cristianoaf81.cursomc.domain.PagamentoComCartao;
import com.cristianoaf81.cursomc.domain.Pedido;
import com.cristianoaf81.cursomc.domain.Produto;
import com.cristianoaf81.cursomc.domain.enums.EstadoPagamento;
import com.cristianoaf81.cursomc.domain.enums.TipoCliente;
import com.cristianoaf81.cursomc.repositories.CategoriaRepository;
import com.cristianoaf81.cursomc.repositories.CidadeRepository;
import com.cristianoaf81.cursomc.repositories.ClienteRepository;
import com.cristianoaf81.cursomc.repositories.EnderecoRepository;
import com.cristianoaf81.cursomc.repositories.EstadoRepository;
import com.cristianoaf81.cursomc.repositories.PagamentoRepository;
import com.cristianoaf81.cursomc.repositories.PedidoRepository;
import com.cristianoaf81.cursomc.repositories.ProdutoRepository;
import com.cristianoaf81.cursomc.repositories.ItemPedidoRepository;

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
    @Autowired 
    private PedidoRepository pedidoRepository;
    @Autowired 
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

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
        // pedidos
        List<Pedido> pedidos = this.pedidoRepository.findAll();
        // pagamentos 
        List<Pagamento> pagamentos = this.pagamentoRepository.findAll();
        // itens de pedido
        List<ItemPedido> itensPedidos = this.itemPedidoRepository.findAll();

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

        // pedidos
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null,sdf.parse("30/09/2017 10:17"),cli1,e1);
        Pedido ped2 = new Pedido(null,sdf.parse("10/10/2017 18:35"),cli1,e2);
        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO,ped1,6) ;
        ped1.setPagamento(pagto1);
        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),null);
        ped2.setPagamento(pagto2);
        cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
        
        // Items de pedido
        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));        
        p1.getItens().addAll(Arrays.asList(ip1));    
        p2.getItens().addAll(Arrays.asList(ip3));     
        p3.getItens().addAll(Arrays.asList(ip2)); 

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

        if (pedidos.size() == 0) {
           pedidoRepository.saveAll(Arrays.asList(ped1,ped2)); 
        }

        if (pagamentos.size() == 0) {
            pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
        }

        if (itensPedidos.size() == 0) {
            itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
        }
	}

}
