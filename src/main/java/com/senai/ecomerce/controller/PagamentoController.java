package com.senai.ecomerce.controller;

import com.senai.ecomerce.dto.PagamentoRequestDto;
import com.senai.ecomerce.dto.PagamentoResponseDto;
import com.senai.ecomerce.service.PagamentoService;
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
@RequestMapping("pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public List<PagamentoResponseDto> findAll() {
        return pagamentoService.findAll();
    }

    @GetMapping("/{id}")
    public PagamentoResponseDto findById(@PathVariable UUID id) {
        return pagamentoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagamentoResponseDto create(@Valid @RequestBody PagamentoRequestDto dto){
        return pagamentoService.create(dto);
    }

    @PutMapping("/{id}")
    public PagamentoResponseDto update(@PathVariable UUID id, @Valid @RequestBody PagamentoRequestDto dto) {
        return pagamentoService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        pagamentoService.delete(id);
    }
}
