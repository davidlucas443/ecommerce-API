package com.senai.ecomerce.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode //usado para gerar automaticamente os métodos equals e hashCode
@Embeddable
public class ItemDoPedidoPK implements Serializable //indica que um objeto da classe pode ser convertido em uma sequência de bytes (serialização).
// salvo em arquivo, enviado pela rede, armazenado em sessão, transferido entre serviços

{
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
