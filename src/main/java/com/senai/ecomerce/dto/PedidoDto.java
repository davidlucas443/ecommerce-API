package com.senai.ecomerce.dto;

import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.enums.StatusDoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDto {

    private UUID id;

    private LocalDate momento;

    private StatusDoPedido status;


}
