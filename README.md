# Tax Calc API
Uma API para um dos desafios do Catalisa T6 que gerencia cálculos de impostos no Brasil.

## Índice

1. [Introdução](#introdução)
2. [Pré-requisitos](#pré-requisitos)
3. [Instalação](#instalação)
4. [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
5. [Variáveis de Ambiente](#variáveis-de-ambiente)
6. [Testando a Aplicação](#testando-a-aplicação)
7. [Endpoints](#endpoints)
    - [Endpoints de Usuários](#endpoints-de-usuários)
    - [Endpoints de Impostos](#endpoints-de-impostos)
    - [Endpoints de Cálculo](#endpoints-de-cálculo)
8. [Estrutura de Dados](#estrutura-de-dados)
    - [UserEntity](#userentity)
    - [TaxEntity](#taxentity)
9. [Cobertura de Testes](#cobertura-de-testes)


# Introdução

A **Tax Calc API** é uma API RESTful desenvolvida para gerenciar cálculos de impostos no Brasil, 
sendo um dos desafios do programa Catalisa T6. A API permite o registro e autenticação de usuários, 
além de operações relacionadas a impostos, como criação, consulta, atualização e exclusão. 
A aplicação foi construída utilizando **Spring Boot** como framework principal, 
**JPA (Java Persistence API)** para persistência de dados e **PostgreSQL** como banco de dados.

A segurança da aplicação é garantida por meio do **Spring Boot Security**, com autenticação e 
autorização baseadas em **JWT (JSON Web Token)**. Isso assegura que apenas usuários autenticados 
possam acessar os endpoints, e que operações sensíveis sejam restritas a usuários com permissões específicas,
como o papel de ADMIN.

Este documento fornece todas as informações necessárias para configurar, instalar e utilizar a API, 
incluindo detalhes sobre os endpoints disponíveis, estrutura de dados, segurança com tokens JWT 
e cobertura de testes.

---
## Pré-requisitos

Antes de começar, certifique-se de que você possui os seguintes requisitos instalados no seu ambiente:

- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior
- [**Maven**](https://maven.apache.org/download.cgi) para gerenciamento de dependências
- [**PostgreSQL**](https://www.postgresql.org/download/) como banco de dados
- Ferramenta para testar APIs, como [**Postman**](https://www.postman.com/downloads/) ou [**cURL**](https://curl.se/download.html)

> **Sugestão:** Utilize a IDE [**IntelliJ IDEA**](https://www.jetbrains.com/idea/download/) com os plugins:
> - [**.env files**](https://plugins.jetbrains.com/plugin/9525--env-files) (para variáveis de ambiente)
> - [**RestfulBox - API**](https://plugins.jetbrains.com/plugin/14723-restfulbox--api) (para testes de API)

---
## Instalação

1. Clone este repositório:
   ```bash
   git clone https://github.com/eduardo-lourenzo/teste-calculadora.git
   cd tax-calc-api

Certifique-se de que o Maven está configurado corretamente no seu ambiente.

Compile o projeto e baixe as dependências:


```bash
mvn clean install
```

---
## Configuração do Banco de Dados

1. Certifique-se de que o PostgreSQL está instalado e em execução no seu ambiente. 
2. Crie o banco de dados necessário: 
3. ```bash
   sql CREATE DATABASE tax_calc;
   ```
Configure as variáveis de ambiente necessárias (veja a seção Variáveis de Ambiente).

---
## Variáveis de Ambiente

As seguintes variáveis de ambiente são necessárias para o funcionamento do projeto:

| Variável         | Descrição | Exemplo |
|------------------|------------------------------------|---------------| 
| `DB_HOST`        | Endereço do host do banco de dados | `localhost` | 
| `DB_PORT`        | Porta do banco de dados | `5432` | 
| `DB_NAME` | Nome do banco de dados | `tax_calc` | 
| `DB_USERNAME`    | Nome de usuário do banco de dados | `admin` | 
| `DB_PASSWORD`    | Senha do banco de dados | `admin` | 
| `JWT_SECRET_KEY` | Chave secreta para geração de JWT | `secret` | 

---
## Testando a Aplicação

Após configurar o banco de dados e as variáveis de ambiente, 
inicie a aplicação com o seguinte comando: 
```bash mvn spring-boot:run```

A aplicação será iniciada e estará disponível no endereço padrão: http://localhost:8080

Agora, você pode interagir com os dados utilizando os endpoints disponíveis.

**Nota:** Certifique-se de que o banco de dados está em execução e que as variáveis de ambiente estão configuradas corretamente antes de iniciar a aplicação.

---
### Endpoints

### **Endpoints de Usuários**
| Método HTTP | Endpoint | Descrição |
|-------------|-------------------|-------------------------------|
| POST | `/users/register` | Registra um novo usuário. |
| POST | `/users/login` | Realiza login e retorna um token. |

### **Endpoints de Impostos**
| Método HTTP | Endpoint | Descrição | 
|-------------|-------------------|-------------------------------| 
| GET | `/taxes` | Retorna todos os impostos. | 
| POST | `/taxes` | Cria um novo imposto. | 
| GET | `/taxes/{id}` | Retorna um imposto pelo ID. | 
| PUT | `/taxes/{id}` | Atualiza um imposto pelo ID. | 
| DELETE | `/taxes/{id}` | Remove um imposto pelo ID. | 

### **Endpoints de Cálculo**
| Método HTTP | Endpoint | Descrição | 
|-------------|-------------------|-------------------------------| 
| POST | `/calc` | Realiza o cálculo de impostos. | 
> Para mais detalhes, consulte a [documentação Swagger](http://localhost:8080/swagger-ui.html).

---
## Estrutura de Dados

### **UserEntity**
| Campo      | Tipo   | Exemplo   |
|------------|--------|-----------|
| `id`       | Long   | 1         |
| `username` | String | admin     |
| `password` | String | senha123  |
| `role`     | Enum   | ADMIN     |

### **TaxEntity**
| Campo        | Tipo       | Exemplo               |
|--------------|------------|-----------------------|
| `id`         | Long       | 1                     |
| `name`       | String     | ICMS                  |
| `description`| String     | Imposto estadual      |
| `aliquot`    | BigDecimal | 0.18                  |

## Cobertura de Testes

A aplicação possui cobertura de testes automatizados utilizando **JUnit** e **JaCoCo**. Para gerar o relatório de cobertura, execute o comando:

```bash 
mvn test
```

Após a execução, o relatório será gerado no diretório target/site/jacoco/index.html. Você pode abrir o arquivo no navegador para visualizar os detalhes da cobertura de testes.

Exemplo de Caminho do Relatório:

```tax-calc-api/target/site/jacoco/index.html```

Certifique-se de que todos os testes foram executados corretamente antes de verificar o relatório.