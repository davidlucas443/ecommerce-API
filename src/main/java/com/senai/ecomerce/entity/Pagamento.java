package com.senai.ecomerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {

    @Id
    private UUID id;

    private LocalDate momento;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId //Faz com que o id do pedido seja o mesmo do pagamento
    private Pedido pedido;
}
