package com.senai.ecomerce.service;

import com.senai.ecomerce.dto.ProdutoRequestDto;
import com.senai.ecomerce.dto.ProdutoResponseDto;
import com.senai.ecomerce.entity.Produto;
import com.senai.ecomerce.repositories.ProdutoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PhotoService photoService;

    private final PasswordEncoder passwordEncoder;

    public ProdutoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public List<ProdutoResponseDto> findAll() {
        return produtoRepository.findAll().stream().map(ProdutoResponseDto::new).toList();
    }

    public ProdutoResponseDto findById(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return new ProdutoResponseDto(produto);
    }

    public ProdutoResponseDto create(ProdutoRequestDto dto) throws IOException {
        Produto produto = new Produto();
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setImgUrl(getImgUrl(dto));

        produtoRepository.save(produto);
        return new ProdutoResponseDto(produto);
    }

    public ProdutoResponseDto update(UUID id, ProdutoRequestDto dto) throws IOException {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        String imgUrl = getImgUrl(dto);
        if (imgUrl != null) {
            produto.setImgUrl(imgUrl);
        }

        produtoRepository.save(produto);
        return new ProdutoResponseDto(produto);
    }

    private String getImgUrl(ProdutoRequestDto dto) throws IOException {
        MultipartFile photo = dto.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            return photoService.savePhoto(photo);
        }
        return dto.getImgUrl();
    }

    public void delete(UUID id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        try {
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Produto está em uso e não pode ser removido", ex);
        }
    }
}
