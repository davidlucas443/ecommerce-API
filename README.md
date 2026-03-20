# Ecommerce API

API REST para gerenciamento de usuarios, produtos, pedidos e pagamentos, desenvolvida com Spring Boot, Spring Web e Spring Data JPA.

## Visao geral

A API permite:

- cadastrar, listar, buscar, atualizar e remover usuarios
- cadastrar, listar, buscar, atualizar e remover produtos
- criar pedidos com um ou mais itens vinculados a um usuario
- registrar pagamento de um pedido

O projeto usa UUID como identificador das entidades principais.

## Stack utilizada

- Java 21
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- H2 para testes
- Maven Wrapper
- Lombok

## Estrutura principal

```text
src/main/java/com/senai/ecomerce
|-- controller
|-- dto
|-- entity
|-- enums
|-- repositories
`-- service
```

## Regras de negocio atuais

- Todo usuario criado recebe `Roles.USER`.
- O email do usuario deve ser unico.
- Um pedido sempre pertence a um usuario existente.
- Um pedido precisa ter pelo menos 1 item.
- Cada item do pedido precisa referenciar um produto existente.
- O total do pedido e calculado com base no preco atual do produto no momento da criacao/atualizacao do item.
- Ao criar um pedido, o status inicial e `AGUARDANDO_PAGAMENTO`.
- Ao criar ou atualizar um pagamento, o status do pedido passa para `PAGO`.
- Pedido com status `PAGO` nao pode ser alterado nem removido.
- Ao remover um pagamento, o pedido volta para `AGUARDANDO_PAGAMENTO`.
- O `id` do pagamento e o mesmo `id` do pedido.

## Modelos principais

### Usuario

- `id`: UUID
- `nome`: String
- `email`: String
- `telefone`: String
- `senha`: String
- `roles`: `ADMIN` ou `USER`

### Produto

- `id`: UUID
- `descricao`: String
- `preco`: Double
- `imgUrl`: String

### Pedido

- `id`: UUID
- `idUser`: UUID
- `momento`: LocalDate
- `status`: `AGUARDANDO_PAGAMENTO`, `PAGO`, `ENVIADO`, `ENTREGUE`, `CANCELADO`
- `items`: lista de itens
- `total`: valor calculado

### Pagamento

- `id`: UUID
- `momento`: LocalDate

## Pre-requisitos

- JDK 21 instalado
- `JAVA_HOME` configurado corretamente
- MySQL acessivel com os dados definidos em `src/main/resources/application.properties`

## Configuracao do ambiente

Atualmente a aplicacao principal esta configurada para usar MySQL em [application.properties](C:\Users\David\Documents\back-end\ecommerce-API\src\main\resources\application.properties).

Exemplo de configuracao:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

Para testes automatizados, o projeto possui configuracao separada com H2 em memoria em [application.properties](C:\Users\David\Documents\back-end\ecommerce-API\src\test\resources\application.properties).

## Como executar

### 1. Validar o Java

```powershell
java -version
```

Se o terminal retornar erro, configure o `JAVA_HOME` e o `PATH`.

Exemplo no Windows:

```powershell
setx JAVA_HOME "C:\Program Files\Java\jdk-21"
setx PATH "%JAVA_HOME%\bin;%PATH%"
```

Feche e abra o terminal depois de executar os comandos acima.

### 2. Subir a aplicacao

Com Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

Ou gerar o pacote:

```powershell
.\mvnw.cmd package
```

Por padrao, a API sobe em:

```text
http://localhost:8080
```

## Endpoints

### Usuario

Base path: `/usuario`

#### Criar usuario

`POST /usuario`

```json
{
  "nome": "David Gomes",
  "email": "david@example.com",
  "telefone": "11999999999",
  "senha": "123456"
}
```

#### Listar usuarios

`GET /usuario`

#### Buscar usuario por id

`GET /usuario/{id}`

#### Atualizar usuario

`PUT /usuario/{id}`

```json
{
  "nome": "David Gomes Atualizado",
  "email": "david.atualizado@example.com",
  "telefone": "11988888888",
  "senha": "654321"
}
```

#### Remover usuario

`DELETE /usuario/{id}`

### Produto

Base path: `/produto`

#### Criar produto

`POST /produto`

```json
{
  "descricao": "Notebook Gamer",
  "preco": 4500.0,
  "imgUrl": "https://example.com/notebook.jpg"
}
```

#### Listar produtos

`GET /produto`

#### Buscar produto por id

`GET /produto/{id}`

#### Atualizar produto

`PUT /produto/{id}`

```json
{
  "descricao": "Notebook Gamer RTX",
  "preco": 4999.9,
  "imgUrl": "https://example.com/notebook-rtx.jpg"
}
```

#### Remover produto

`DELETE /produto/{id}`

### Pedido

Base path: `/pedido`

#### Criar pedido

`POST /pedido`

```json
{
  "idUser": "UUID_DO_USUARIO",
  "items": [
    {
      "produtoId": "UUID_DO_PRODUTO",
      "quantidade": 2
    }
  ]
}
```

#### Listar pedidos

`GET /pedido`

#### Buscar pedido por id

`GET /pedido/{id}`

#### Atualizar pedido

`PUT /pedido/{id}`

```json
{
  "idUser": "UUID_DO_USUARIO",
  "items": [
    {
      "produtoId": "UUID_DO_PRODUTO",
      "quantidade": 3
    }
  ]
}
```

#### Remover pedido

`DELETE /pedido/{id}`

### Pagamento

Base path: `/pagamento`

#### Criar pagamento

`POST /pagamento`

```json
{
  "pedidoId": "UUID_DO_PEDIDO",
  "momento": "2026-03-20"
}
```

#### Listar pagamentos

`GET /pagamento`

#### Buscar pagamento por id

`GET /pagamento/{id}`

#### Atualizar pagamento

Importante: o `id` da URL deve ser igual ao `pedidoId` enviado no body.

`PUT /pagamento/{id}`

```json
{
  "pedidoId": "UUID_DO_PEDIDO",
  "momento": "2026-03-21"
}
```

#### Remover pagamento

`DELETE /pagamento/{id}`

## Fluxo recomendado de teste

1. Criar um usuario
2. Criar um produto
3. Criar um pedido usando `idUser` e `produtoId`
4. Criar um pagamento usando `pedidoId`
5. Consultar os recursos criados

## Respostas e erros comuns

### `400 Bad Request`

Retornado quando:

- campos obrigatorios nao sao enviados
- payload esta mal formatado
- no update de pagamento, o `id` da URL e diferente do `pedidoId` do body

### `404 Not Found`

Retornado quando:

- usuario nao existe
- produto nao existe
- pedido nao existe
- pagamento nao existe

### `409 Conflict`

Retornado quando:

- email de usuario ja esta cadastrado
- usuario ou produto esta em uso e nao pode ser removido
- pedido ja esta pago e nao pode ser alterado ou removido

## Postman

Existe uma collection pronta no arquivo [ecommerce-api.postman_collection.json](C:\Users\David\Documents\back-end\ecommerce-API\ecommerce-api.postman_collection.json).

Ela inclui:

- variavel `baseUrl`
- requests de CRUD para usuario, produto, pedido e pagamento
- captura automatica de `usuarioId`, `produtoId`, `pedidoId` e `pagamentoId`

Para importar:

1. Abra o Postman
2. Clique em `Import`
3. Selecione o arquivo `ecommerce-api.postman_collection.json`

## Testes

Para rodar os testes:

```powershell
.\mvnw.cmd test
```

Para gerar o build:

```powershell
.\mvnw.cmd package
```

## Autor

Projeto desenvolvido para estudo de Spring Boot e modelagem de uma API de ecommerce.
