package com.senai.ecomerce.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequestDto {

    @NotBlank
    private String descricao;

    @NotNull
    @DecimalMin("0.0")
    private Double preco;

    private String imgUrl;
}

