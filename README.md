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
- Swagger-UI [aqui](http://localhost:9999/swagger-ui/).
## Observações
- Antes de executar o docker-compose, certifique-se que as portas 5432 e 9999 estão disponíveis.
- Para facilitar os testes, o DB iniciará populado com alguns dados