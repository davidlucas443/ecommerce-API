package com.senai.ecomerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDoPedidoResponseDto {

    private UUID produtoId;
    private String produtoDescricao;
    private Double precoUnitario;
    private Integer quantidade;
    private Double subtotal;
}

