package com.senai.ecomerce.controller;

import com.senai.ecomerce.dto.PedidoRequestDto;
import com.senai.ecomerce.dto.PedidoResponseDto;
import com.senai.ecomerce.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<PedidoResponseDto> findAll() {
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public PedidoResponseDto findById(@PathVariable UUID id) {
        return pedidoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDto create(@Valid @RequestBody PedidoRequestDto dto){
        return pedidoService.create(dto);
    }

    @PutMapping("/{id}")
    public PedidoResponseDto update(@PathVariable UUID id, @Valid @RequestBody PedidoRequestDto dto) {
        return pedidoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        pedidoService.delete(id);
    }
}
