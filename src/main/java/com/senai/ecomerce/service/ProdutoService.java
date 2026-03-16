package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.ProdutoRequestDto;
import com.senai.ecomerce.dto.ProdutoResponseDto;
import com.senai.ecomerce.entity.Produto;
import com.senai.ecomerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoResponseDto create(ProdutoRequestDto dto) {
        Produto produto = new Produto();
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setImgUrl(dto.getImgUrl());

        produtoRepository.save(produto);
        return new ProdutoResponseDto(produto);
    }
}
