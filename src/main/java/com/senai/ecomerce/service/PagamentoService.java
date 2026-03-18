package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.PagamentoRequestDto;
import com.senai.ecomerce.dto.PagamentoResponseDto;
import com.senai.ecomerce.entity.Pagamento;
import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.enums.StatusDoPedido;
import com.senai.ecomerce.repositories.PagamentoRepository;
import com.senai.ecomerce.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<PagamentoResponseDto> findAll() {
        return pagamentoRepository.findAll().stream().map(PagamentoResponseDto::new).toList();
    }

    public PagamentoResponseDto findById(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado"));
        return new PagamentoResponseDto(pagamento);
    }

    @Transactional
    public PagamentoResponseDto create(PagamentoRequestDto dto){
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

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

    @Transactional
    public PagamentoResponseDto update(UUID id, PagamentoRequestDto dto) {
        if (!id.equals(dto.getPedidoId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O id da URL deve ser igual ao pedidoId do body");
        }

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        Pagamento pagamento = pedido.getPagamento();
        if (pagamento == null) {
            pagamento = new Pagamento();
            pagamento.setPedido(pedido);
            pedido.setPagamento(pagamento);
        }

        pagamento.setMomento(dto.getMomento());
        pedido.setStatus(StatusDoPedido.PAGO);

        pedidoRepository.save(pedido);
        return new PagamentoResponseDto(pagamento);
    }

    @Transactional
    public void delete(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado"));

        Pedido pedido = pagamento.getPedido();
        if (pedido != null) {
            pedido.setPagamento(null);
            pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);
            pedidoRepository.save(pedido);
        }

        pagamentoRepository.delete(pagamento);
    }
}
