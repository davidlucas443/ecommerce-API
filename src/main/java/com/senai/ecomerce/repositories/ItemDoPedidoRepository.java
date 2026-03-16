package com.senai.ecomerce.repositories;

import com.senai.ecomerce.entity.ItemDoPedido;
import com.senai.ecomerce.entity.ItemDoPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido, ItemDoPedidoPK> {
}

