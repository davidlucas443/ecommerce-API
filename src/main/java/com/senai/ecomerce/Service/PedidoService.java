package com.senai.ecomerce.Service;

import com.senai.ecomerce.dto.PedidoDto;
import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.enums.StatusDoPedido;
import com.senai.ecomerce.repositories.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoDto create(PedidoDto dto){
        Pedido pedido = new Pedido();
        pedido.setMomento(LocalDate.now());
        pedido.setStatus(StatusDoPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.save(pedido);
        return dto;
    }
}
