# AutoManager

##  Tecnologias Utilizadas

| Tecnologia | Versão | 
|------------|--------|
| **Java** | 17 | 
| **Spring Boot** | 2.6.4 |
| **Spring Data JPA** | 2.6.4 | 
| **Spring HATEOAS** | 2.6.4 | 
| **H2 Database** | Runtime | 
| **Lombok** | Latest | 
| **Maven** | 3.8+ | 

---

## Pré-requisitos

- **Java JDK 17** ou superior
- **Maven 3.8+**
- **Postman**, **Insomnia** ou **Thunder Client** (para testar a API)

---

## Instalação e Execução

### Clone o Repositório

```bash
git clone https://github.com/seu-usuario/atvii-autobots-microservico-spring.git
cd atvii-autobots-microservico-spring/automanager
```

### Compile o Projeto

```bash
mvn clean install
```

### Execute a Aplicação

```bash
mvn spring-boot:run
```

###  Acesse a Aplicação

http://localhost:8080

## Endpoints

### Cliente

| Método | Endpoint |
|--------|----------|
| GET | `/cliente/{id}` |
| GET | `/cliente/clientes` |
| POST | `/cliente/cadastro` |
| PUT | `/cliente/atualizar` |
| DELETE | `/cliente/excluir` |

### Documento

| Método | Endpoint |
|--------|----------|
| GET | `/documento/{id}` |
| GET | `/documento/documentos` |
| POST | `/documento/cadastro` |
| PUT | `/documento/atualizar` |
| DELETE | `/documento/excluir` |

### Endereço

| Método | Endpoint |
|--------|----------|
| GET | `/endereco/{id}` |
| GET | `/endereco/enderecos` |
| POST | `/endereco/cadastro` |
| PUT | `/endereco/atualizar` |
| DELETE | `/endereco/excluir` |

### Telefone

| Método | Endpoint |
|--------|----------|
| GET | `/telefone/{id}` |
| GET | `/telefone/telefones` |
| POST | `/telefone/cadastro` |
| PUT | `/telefone/atualizar` |
| DELETE | `/telefone/excluir` |