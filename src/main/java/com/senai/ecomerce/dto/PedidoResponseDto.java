package com.senai.ecomerce.dto;

import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.enums.StatusDoPedido;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PedidoResponseDto {

    private UUID id;
    private UUID idUser;
    private LocalDate momento;
    private StatusDoPedido status;
    private List<ItemDoPedidoResponseDto> items = new ArrayList<>();
    private Double total = 0.0;

    public PedidoResponseDto(Pedido pedido) {
        this.id = pedido.getId();
        this.idUser = pedido.getIdUser();
        this.momento = pedido.getMomento();
        this.status = pedido.getStatus();
    }
}
