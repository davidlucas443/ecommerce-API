package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.PagamentoRequestDto;
import com.senai.ecomerce.dto.PagamentoResponseDto;
import com.senai.ecomerce.entity.Pagamento;
import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.repositories.PagamentoRepository;
import com.senai.ecomerce.repositories.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public PagamentoResponseDto create(PagamentoRequestDto dto){
        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        Pagamento pagamento = new Pagamento();
        pagamento.setMomento(dto.getMomento());
        pagamento.setPedido(pedido);
        pagamentoRepository.save(pagamento);
        return new PagamentoResponseDto(pagamento);
    }
}
