package com.senai.ecomerce.Service;

import com.senai.ecomerce.dto.PagamentoDto;
import com.senai.ecomerce.dto.UsuarioDto;
import com.senai.ecomerce.entity.Pagamento;
import com.senai.ecomerce.entity.Usuario;
import com.senai.ecomerce.repositories.PagamentoRepository;
import com.senai.ecomerce.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PagamentoDto create(PagamentoDto dto){
        Pagamento pagamento = new Pagamento();
        pagamento.setMomento(dto.getMomento());
        pagamentoRepository.save(pagamento);
        return dto;
    }
}
