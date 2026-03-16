package com.senai.ecomerce.dto;

import com.senai.ecomerce.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDto {

    private UUID id;
    private String descricao;
    private Double preco;
    private String imgUrl;

    public ProdutoResponseDto(Produto produto) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.imgUrl = produto.getImgUrl();
    }
}

