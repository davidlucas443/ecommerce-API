package com.senai.ecomerce.dto;

import com.senai.ecomerce.entity.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponseDto {

    private UUID id;
    private LocalDate momento;

    public PagamentoResponseDto(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.momento = pagamento.getMomento();
    }
}

