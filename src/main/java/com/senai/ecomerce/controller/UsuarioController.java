package com.senai.ecomerce.controller;

import com.senai.ecomerce.dto.UsuarioResponseDto;
import com.senai.ecomerce.service.UsuarioService;
import com.senai.ecomerce.dto.UsuarioRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody UsuarioRequestDto dto){
        return ResponseEntity.ok(usuarioService.create(dto));
    }
}
