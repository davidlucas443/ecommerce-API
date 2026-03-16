package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.ItemDoPedidoRequestDto;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public PedidoResponseDto create(PedidoRequestDto dto) {

        Usuario cliente = usuarioRepository.findById(dto.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setIdUser(dto.getIdUser());
        pedido.setMomento(LocalDate.now());
        pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);

        for (ItemDoPedidoRequestDto itemDto : dto.getItems()) {
            Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            ItemDoPedido item = new ItemDoPedido(pedido, produto, itemDto.getQuantidade(), produto.getPreco());
            pedido.getItems().add(item);
        }

        pedidoRepository.save(pedido);

        return new PedidoResponseDto(pedido);
    }
}
