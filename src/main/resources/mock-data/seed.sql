-- Mock seed data (UUIDs fixos) para MySQL.
-- Ordem pensada para respeitar FKs comuns: usuario -> pedido -> itens/pagamento e produto/categoria -> join tables.

-- USUARIO
-- Senha dos usuarios seedados: password
INSERT INTO usuario (id, nome, email, telefone, senha, roles) VALUES
('11111111-1111-1111-1111-111111111111', 'Ana Silva', 'ana.silva@example.com', '+55 11 99999-0001', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('22222222-2222-2222-2222-222222222222', 'Bruno Souza', 'bruno.souza@example.com', '+55 11 99999-0002', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'USER'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Admin Sistema', 'admin@example.com', '+55 11 99999-9999', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');

-- CATEGORIA
INSERT INTO categoria (id, nome) VALUES
('c1111111-1111-1111-1111-111111111111', 'Eletronicos'),
('c2222222-2222-2222-2222-222222222222', 'Casa'),
('c3333333-3333-3333-3333-333333333333', 'Livros'),
('c4444444-4444-4444-4444-444444444444', 'Games');

-- PRODUTO
INSERT INTO produto (id, descricao, preco, img_url) VALUES
('e1111111-1111-1111-1111-111111111111', 'Mouse sem fio', 79.90, 'https://example.com/img/mouse.jpg'),
('e2222222-2222-2222-2222-222222222222', 'Teclado mecanico', 219.90, 'https://example.com/img/teclado.jpg'),
('e3333333-3333-3333-3333-333333333333', 'Lampada inteligente', 59.90, 'https://example.com/img/lampada.jpg'),
('e4444444-4444-4444-4444-444444444444', 'Livro: Java para Web', 129.00, 'https://example.com/img/livro-java.jpg'),
('e5555555-5555-5555-5555-555555555555', 'Controle para PC', 159.90, 'https://example.com/img/controle.jpg'),
('e6666666-6666-6666-6666-666666666666', 'Cafeteira', 249.90, 'https://example.com/img/cafeteira.jpg');

-- PEDIDO
INSERT INTO pedido (id, id_user, momento, status, client_id) VALUES
('d1111111-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', '2026-04-10', 'PAGO', '11111111-1111-1111-1111-111111111111'),
('d2222222-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', '2026-04-12', 'AGUARDANDO_PAGAMENTO', '22222222-2222-2222-2222-222222222222'),
('d3333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', '2026-04-15', 'ENVIADO', '11111111-1111-1111-1111-111111111111');

-- PRODUTO x CATEGORIA (tb_produto_categoria)
INSERT INTO tb_produto_categoria (produto_id, categoria_id) VALUES
('e1111111-1111-1111-1111-111111111111', 'c1111111-1111-1111-1111-111111111111'),
('e2222222-2222-2222-2222-222222222222', 'c1111111-1111-1111-1111-111111111111'),
('e3333333-3333-3333-3333-333333333333', 'c1111111-1111-1111-1111-111111111111'),
('e3333333-3333-3333-3333-333333333333', 'c2222222-2222-2222-2222-222222222222'),
('e4444444-4444-4444-4444-444444444444', 'c3333333-3333-3333-3333-333333333333'),
('e5555555-5555-5555-5555-555555555555', 'c4444444-4444-4444-4444-444444444444'),
('e5555555-5555-5555-5555-555555555555', 'c1111111-1111-1111-1111-111111111111'),
('e6666666-6666-6666-6666-666666666666', 'c2222222-2222-2222-2222-222222222222');

-- ITENS DO PEDIDO (tb_pedido_item) - PK composta (pedido_id, produto_id)
INSERT INTO tb_pedido_item (pedido_id, produto_id, quantidade, preco) VALUES
('d1111111-1111-1111-1111-111111111111', 'e1111111-1111-1111-1111-111111111111', 2, 79.90),
('d1111111-1111-1111-1111-111111111111', 'e4444444-4444-4444-4444-444444444444', 1, 129.00),
('d2222222-2222-2222-2222-222222222222', 'e6666666-6666-6666-6666-666666666666', 1, 249.90),
('d3333333-3333-3333-3333-333333333333', 'e2222222-2222-2222-2222-222222222222', 1, 219.90),
('d3333333-3333-3333-3333-333333333333', 'e5555555-5555-5555-5555-555555555555', 1, 159.90);

-- PAGAMENTO
-- @MapsId (Pagamento.id == Pedido.id). Normalmente a tabela "pagamento" fica com colunas (id, momento),
-- onde "id" e' PK e FK para pedido(id).
INSERT INTO pagamento (id, momento) VALUES
('d1111111-1111-1111-1111-111111111111', '2026-04-10');


