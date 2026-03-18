package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.ItemDoPedidoRequestDto;
import com.senai.ecomerce.dto.ItemDoPedidoResponseDto;
import com.senai.ecomerce.dto.PedidoRequestDto;
import com.senai.ecomerce.dto.PedidoResponseDto;
import com.senai.ecomerce.entity.ItemDoPedido;
import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.entity.Produto;
import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.enums.StatusDoPedido;
import com.senai.ecomerce.repositories.PedidoRepository;
import com.senai.ecomerce.repositories.ProdutoRepository;
import com.senai.ecomerce.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public List<PedidoResponseDto> findAll() {
        return pedidoRepository.findAll().stream().map(this::toResponseDto).toList();
    }

    @Transactional
    public PedidoResponseDto findById(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
        return toResponseDto(pedido);
    }

    @Transactional
    public PedidoResponseDto create(PedidoRequestDto dto) {

        Usuario cliente = usuarioRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setIdUser(dto.getIdUser());
        pedido.setMomento(LocalDate.now());
        pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);

        for (ItemDoPedidoRequestDto itemDto : dto.getItems()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

            ItemDoPedido item = new ItemDoPedido(pedido, produto, itemDto.getQuantidade(), produto.getPreco());
            pedido.getItems().add(item);
        }

        pedidoRepository.save(pedido);

        return toResponseDto(pedido);
    }

    @Transactional
    public PedidoResponseDto update(UUID id, PedidoRequestDto dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (pedido.getStatus() == StatusDoPedido.PAGO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido já está pago e não pode ser alterado");
        }

        Usuario cliente = usuarioRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        pedido.setCliente(cliente);
        pedido.setIdUser(dto.getIdUser());

        // Substitui os itens do pedido (orphanRemoval remove os itens antigos).
        pedido.getItems().clear();
        for (ItemDoPedidoRequestDto itemDto : dto.getItems()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
            ItemDoPedido item = new ItemDoPedido(pedido, produto, itemDto.getQuantidade(), produto.getPreco());
            pedido.getItems().add(item);
        }

        pedidoRepository.save(pedido);
        return toResponseDto(pedido);
    }

    @Transactional
    public void delete(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        if (pedido.getStatus() == StatusDoPedido.PAGO) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido já está pago e não pode ser removido");
        }

        try {
            pedidoRepository.delete(pedido);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pedido está em uso e não pode ser removido", ex);
        }
    }

    private PedidoResponseDto toResponseDto(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto(pedido);

        double total = 0.0;
        for (ItemDoPedido item : pedido.getItems()) {
            double precoUnitario = item.getPreco();
            int quantidade = item.getQuantidade();
            double subtotal = precoUnitario * quantidade;
            total += subtotal;

            Produto produto = item.getProduto();
            dto.getItems().add(new ItemDoPedidoResponseDto(
                    produto.getId(),
                    produto.getDescricao(),
                    precoUnitario,
                    quantidade,
                    subtotal
            ));
        }

        dto.setTotal(total);
        return dto;
    }
}
