package com.senai.ecomerce.Service;

import com.senai.ecomerce.dto.PedidoDto;
import com.senai.ecomerce.dto.UsuarioDto;
import com.senai.ecomerce.entity.Pedido;
import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.enums.StatusDoPedido;
import com.senai.ecomerce.repositories.PedidoRepository;
import com.senai.ecomerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDto create(UsuarioDto dto){
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setNome(dto.getNome());
        usuarioRepository.save(usuario);
        return dto;
    }

}
