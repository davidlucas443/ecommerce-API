package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.PagamentoRequestDto;
import com.senai.ecomerce.dto.PagamentoResponseDto;
import com.senai.ecomerce.entity.Pagamento;
import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.enums.StatusDoPedido;
import com.senai.ecomerce.repositories.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public PagamentoResponseDto create(PagamentoRequestDto dto){
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        // `Pagamento` usa `@MapsId`, entao o ID do pagamento e o mesmo do pedido.
        // Se o pedido ja tiver um pagamento persistido, apenas atualizamos (evita violacao de PK).
        Pagamento pagamento = pedido.getPagamento();
        if (pagamento == null) {
            pagamento = new Pagamento();
            pagamento.setPedido(pedido);
            pedido.setPagamento(pagamento);
        }

        pagamento.setMomento(dto.getMomento());
        pedido.setStatus(StatusDoPedido.PAGO);

        // Salva o lado dono (Pedido) e deixa o cascade atualizar/criar o pagamento quando necessario.
        pedidoRepository.save(pedido);
        return new PagamentoResponseDto(pagamento);
    }
}
