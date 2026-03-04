package com.senai.ecomerce.controller;

import com.senai.ecomerce.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private com.senai.ecomerce.Service.PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDto> create(@RequestBody PedidoDto dto){
        PedidoDto novoPedido = pedidoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
    }
}
