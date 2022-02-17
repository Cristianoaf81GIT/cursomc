package com.cristianoaf81.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.cristianoaf81.cursomc.domain.ItemPedido;
import com.cristianoaf81.cursomc.domain.PagamentoComBoleto;
import com.cristianoaf81.cursomc.domain.Pedido;
import com.cristianoaf81.cursomc.domain.Produto;
import com.cristianoaf81.cursomc.domain.enums.EstadoPagamento;
import com.cristianoaf81.cursomc.repositories.ItemPedidoRepository;
import com.cristianoaf81.cursomc.repositories.PagamentoRepository;
import com.cristianoaf81.cursomc.repositories.PedidoRepository;
import com.cristianoaf81.cursomc.repositories.ProdutoRepository;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    private String errorMessage = "Objeto não encontrado id: %d tipo: %s";
    private String resourceName = PedidoService.class.getName();

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> opt = repository.findById(id);
        if (opt.isEmpty()) {
            errorMessage = String.format(errorMessage, id, resourceName);
            throw new ObjectNotFoundException(errorMessage);
        }
        return opt.get();
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            Optional<Produto> p = produtoRepository.findById(ip.getProduto().getId());
            if (!p.isEmpty()) {
                ip.setProduto(p.get());
                ip.setPreco(ip.getProduto().getPreco());
                ip.setPedido(obj);
            }

        }
        itemPedidoRepository.saveAll(obj.getItens());
        // emailService.sendOrderConfirmationEmail(obj);
        return obj;
    }
}
