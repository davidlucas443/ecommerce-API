package com.senai.ecomerce.controller;

import com.senai.ecomerce.Service.PagamentoService;
import com.senai.ecomerce.Service.UsuarioService;
import com.senai.ecomerce.dto.PagamentoDto;
import com.senai.ecomerce.entity.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping
    public ResponseEntity<PagamentoDto> create(@RequestBody PagamentoDto dto){
        PagamentoDto novoPagamento = pagamentoService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
    }

}
