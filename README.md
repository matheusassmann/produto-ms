![](./images/senior-logo.png)
# Produto-ms
Projeto desenvolvido como parte do processo seletivo da Senior Sistemas.
#### Tecnologias utilizadas:
Para o desenvolvimento desta aplicação, foram utilizadas as seguintes tecnologias:
- Java 11
- Maven
- Docker
- JPQL
- Spring Boot
- JPA

## Como executar?
#### Ferramentas necessárias:
- [Docker for Windows](https://docs.docker.com/desktop/windows/install/ "Download Docker").
- [Postman](https://www.postman.com/downloads/ "Download Postman")
### Docker

- Execute o seguinte comando no prompt de comando/powershell (no diretório raiz do projeto):
    ```bash
    docker-compose up -d
    ```

# Utilizando a API
Endpoints disponíveis na aplicação:
## Produto
### GET
- http://localhost:9999/api/v1/produtos
- http://localhost:9999/api/v1/produtos/{id}
### POST
- http://localhost:9999/api/v1/produtos
```
// Request Body
{
    "nome": "Mousepad",
    "descricao": "Mousepad grande (50x30)",
    "preco": 99.99,
    "isService": false,
    "situacaoProduto": "ATIVO"
}
```
### PUT
- http://localhost:9999/api/v1/produtos/{id}
```
// Request Body
{
    "nome": "Mousepad-Pequeno",
    "descricao": "Mousepad grande (30x25)",
    "preco": 49.99,
    "isService": false,
    "situacaoProduto": "ATIVO"
}
```
### DELETE
- http://localhost:9999/api/v1/produtos/{id}

## Pedido
### GET
- http://localhost/api/v1/pedidos
- http://localhost/api/v1/pedidos/{id}
### POST
- http://localhost/api/v1/pedidos/criarPedido
```
// Request Body
{
    "produtos": [
        {
            "id": "9aac003f-669c-486e-b260-d7a8550e3cdf",
            "quantidade": 1
        },
        {
            "id": "3a76a106-363f-4ab8-98ff-2947ad98c6fd",
            "quantidade": 2
        }
    ]
}
```
- http://localhost/api/v1/pedidos/finalizarPedido/{id}
- http://localhost/api/v1/pedidos/aplicarDesconto
```
// Request Body
{
    "id": "b0c2c961-58e7-4b5c-b842-71f247478966",
    "percentualDesconto": 20.00
}
```
### PUT
- http://localhost/api/v1/pedidos/{id}
```
// Request Body
{
    "produtos": [
        {
            "id": "9aac003f-669c-486e-b260-d7a8550e3cdf",
            "quantidade": 2
        },
        {
            "id": "3a76a106-363f-4ab8-98ff-2947ad98c6fd",
            "quantidade": 3
        },
        {
            "id": "3317fda8-45e8-4687-95b0-1ebfb420aa7f",
            "quantidade": 3
        }
    ]
}
```
### DELETE
- http://localhost/api/v1/pedidos/{id}

## Observações
- Antes de executar o docker-compose, certifique-se que as portas 5432 e 9999 estão disponíveis.
- Para facilitar os testes, o DB iniciará populado com alguns dados.


