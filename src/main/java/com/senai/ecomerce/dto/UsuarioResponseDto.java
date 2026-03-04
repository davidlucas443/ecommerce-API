package com.senai.ecomerce.dto;

import com.senai.ecomerce.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto {

    private UUID id;

    private String nome;

    private String email;

    private String telefone;


    public UsuarioResponseDto (Usuario usuario){
        this.email = usuario.getEmail();
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.telefone = usuario.getTelefone();

    }

}
