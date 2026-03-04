package com.senai.ecomerce.entity;


import com.senai.ecomerce.enums.StatusDoPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID idUser;

    private LocalDate momento;

    @Enumerated(EnumType.STRING)
    private StatusDoPedido status;

    @ManyToOne
    @JoinColumn( name = "client_id")
    private Usuario cliente;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

}
