package com.senai.ecomerce.controller;

import com.senai.ecomerce.dto.PagamentoRequestDto;
import com.senai.ecomerce.dto.PagamentoResponseDto;
import com.senai.ecomerce.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<PagamentoResponseDto>> findAll() {
        return ResponseEntity.ok(pagamentoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(pagamentoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PagamentoResponseDto> create(@Valid @RequestBody PagamentoRequestDto dto){
        PagamentoResponseDto novoPagamento = pagamentoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDto> update(@PathVariable UUID id, @Valid @RequestBody PagamentoRequestDto dto) {
        return ResponseEntity.ok(pagamentoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        pagamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
