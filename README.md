# Microserviço de Lista de Desejos (Wishlist)

Microserviço de Wishlist desenvolvido com Spring Boot 4.0.1, MongoDB e arquitetura limpa (Clean Architecture).

## 📋 Funcionalidades

- ✅ Adicionar um produto na Wishlist do cliente
- ✅ Remover um produto da Wishlist do cliente
- ✅ Consultar todos os produtos da Wishlist do cliente
- ✅ Consultar se um determinado produto está na Wishlist do cliente
- ✅ Limite máximo de 20 produtos por Wishlist

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 4.0.1**
- **Spring WebFlux** (Programação reativa)
- **Spring Data MongoDB Reactive**
- **MongoDB**
- **Lombok**
- **Docker & Docker Compose**
- **Springdoc OpenAPI 3.0.0** (Swagger UI)

### Testes

- **Cucumber/BDD** (Behavior-Driven Development)
- **JUnit 5**
- **Testcontainers** (Testes de integração com MongoDB)
- **AssertJ**

## 📦 Pré-requisitos

- Java 17+
- Maven 3.9+
- Docker e Docker Compose

## 🚀 Como Executar

### Opção 1: Usando Docker Compose (Recomendado)

```bash
# Construir e iniciar todos os serviços e deixar os containers rodando em segundo plano
docker compose up --build -d

# Para parar os serviços
docker compose down

# Para parar e remover volumes
docker compose down -v
```

A aplicação estará disponível em: `http://localhost:8080`

### Opção 2: Executar localmente

```bash
# 1. Iniciar MongoDB via Docker
docker compose up mongodb -d

# 2. Compilar o projeto
./mvnw clean install

# 3. Executar a aplicação
./mvnw spring-boot:run
```

## 🧪 Executar Testes (Testes BDD exigem o container do Banco Mongo rodando)

```bash
# Executar todos os testes (incluindo BDD)
./mvnw test

# Executar apenas testes BDD
./mvnw test -Dtest=CucumberRunnerTest
```

Os relatórios de testes BDD serão gerados em: `target/cucumber-reports/`

## 📡 API Endpoints

### 1. Adicionar produto à Wishlist

```http
POST /api/v1/wishlists/{customerId}/products
Content-Type: application/json

{
  "productId": "product-001"
}
```

**Respostas:**
- `201 Created` - Produto adicionado com sucesso
- `400 Bad Request` - Dados inválidos - produto já existe na lista ou limite atingido
- `500 Internal Server Error` - Erro de estado na aplicação

### 2. Remover produto da Wishlist

```http
DELETE /api/v1/wishlists/{customerId}/products/{productId}
```

**Respostas:**
- `200 OK` - Produto removido com sucesso
- `404 Not Found` - Produto não encontrado na wishlist

### 3. Consultar todos os produtos

```http
GET /api/v1/wishlists/{customerId}/products
```

**Resposta:**
```json
[
  {
    "productId": "produto-001"
  },
  {
    "productId": "produto-002"
  }
]
```

### 4. Verificar se produto existe na Wishlist

```http
GET /api/v1/wishlists/{customerId}/products/{productId}/exists
```

**Resposta:**
```json
{
  "exists": true
}
```

## 📊 Health Check

```http
GET /actuator/health
```

## 📚 Documentação da API (Swagger)

A aplicação possui documentação interativa da API usando **Swagger UI** (OpenAPI 3.0).

### Acessar o Swagger UI

Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

Ou o endpoint de documentação JSON:

```
http://localhost:8080/v3/api-docs
```

## 🧪 Executar Testes

### Testes Unitários (sem Docker)
```bash
./mvnw test -Dtest='Wishlist*,Product*'
# ou
./run.sh test-unit
```

### Todos os Testes (requer Docker)
```bash
./mvnw test
# ou
./run.sh test
```

## 🔍 Exemplos de Uso

### Adicionar produto

```bash
curl -X POST http://localhost:8080/api/v1/wishlists/cliente-001/products \
  -H "Content-Type: application/json" \
  -d '{"productId": "produto-123"}'
```

### Listar produtos

```bash
curl http://localhost:8080/api/v1/wishlists/cliente-001/products
```

### Verificar produto

```bash
curl http://localhost:8080/api/v1/wishlists/cliente-001/products/produto-123/exists
```

### Remover produto

```bash
curl -X DELETE http://localhost:8080/api/v1/wishlists/cliente-001/products/produto-123
```

## 🗄️ Banco de Dados

O serviço utiliza MongoDB com a seguinte estrutura:

**Collection: wishlists**
```json
{
  "_id": "ObjectId",
  "customerId": "cliente-001",
  "products": [
    {
      "productId": "produto-001"
    },
    {
      "productId": "produto-002"
    }
  ]
}
```

## 🧪 Cenários de Teste BDD

O projeto inclui testes BDD completos com os seguintes cenários:

- ✅ Adicionar produto em wishlist vazia
- ✅ Adicionar múltiplos produtos
- ✅ Prevenir produtos duplicados
- ✅ Prevenir mais de 20 produtos
- ✅ Remover produto da wishlist
- ✅ Prevenir remoção de produto inexistente
- ✅ Consultar todos os produtos
- ✅ Consultar wishlist vazia
- ✅ Verificar existência de produto

## 🐳 Containers Docker

O projeto fornece:

- **wishlist-app**: Aplicação Spring Boot
- **wishlist-mongodb**: Banco de dados MongoDB

Ambos os containers incluem health checks configurados.

## 📝 Variáveis de Ambiente

| Variável | Descrição                        | Padrão |
|----------|----------------------------------|--------|
| `SPRING_DATA_MONGODB_HOST` | Host do MongoDB                  | localhost |
| `SPRING_DATA_MONGODB_PORT` | Porta do MongoDB                 | 27017 |
| `SPRING_DATA_MONGODB_DATABASE` | Nome do database                 | wishlistdb |
| `SPRING_DATA_MONGODB_USERNAME` | Usuário do MongoDB               | root |
| `SPRING_DATA_MONGODB_PASSWORD` | Senha do MongoDB                 | secret |
| `MONGO_DB_URL` | Url do banco MongoDB para testes | secret |

## 📄 Licença

Este projeto foi desenvolvido como parte de um desafio técnico.

## 👨‍💻 Autor

Berkson Ximenes Soares

---

**Nota:** Este é um microserviço que faz parte de uma arquitetura maior. Não gerencia informações de Produtos ou Clientes, focando exclusivamente na Wishlist.
