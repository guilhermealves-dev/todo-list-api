
# TODO List API

Restful API implementada utilizando o framework Spring Boot

Nesta API é possível gerenciar tarefas (TODO-LIST)
## Funcionalidades

- Fazer autenticação
- Cadastrar tarefas
- Listar tarefas
    - Filtrar tarefas com o Status `PENDING` ou `COMPLETED`
    - Ao listar todas as tarefas sem uma filtragem de Status definida, é retornado a lista de tarefas ordenando as com Status `PENDING` primeiro
- Tarefas criadas por um usuário não pode ser vista por outros usuários
- Usuários com perfil de `ADMIN` podem listar, editar, ou apagar as tarefas de qualquer usuário


## Informações da API

- Todas as ações realizadas dentro da API são autenticadas utilizando um `access_token` que tem validade de 5 minutos
- O projeto foi construído utilizando a arquitetura **Hexagonal (Ports and Adapters)**
- A persistência de dados é realizada em um banco de dados relacional H2 do Java
- A fim de reduzir a sobrecarga e acesso constante ao banco de dados, a API se conecta ao `REDIS` para armazenar cache
- Foi configurado o `Actuator` para fornecer informações de `healthcheck` da aplicação
- Para os indicadores de performance da API foi configurado o `Spring Admin` que em conjunto com o `Actuator` conseguem fornecer essas métricas
- Para exibir logs/trace do que esta acontecendo na API foi utilizado o `Slf4j`
- Para a realização dos testes unitários foi utilizado o `JUnit`
- Utilizando o `JaCOCO` podemos visualizar que os testes unitários cobriram **86%** dos códigos na aplicação
## Requisitos para instalação

- GIT - [Download](https://git-scm.com/downloads)
- Maven - [Download](https://maven.apache.org/download.cgi)
- Docker - [Download](https://docs.docker.com/desktop/#download-and-install)
- Java JDK versão `11.0.14` ou superior - [Download](https://www.oracle.com/java/technologies/downloads/#java11)
- IDE de sua preferência: [NetBeans](https://netbeans.apache.org/download/nb126/nb126.html), [Eclipse](https://www.eclipse.org/downloads/), [Intellij](https://www.jetbrains.com/pt-br/idea/download/)
## Instalação

1) Primeiramente clone o projeto e entre na pasta `todo-list-api`

```bash
  git clone https://github.com/guilhermealves-dev/todo-list-api.git
  cd todo-list-api
```
    
2) Instale as dependências  do projeto utilizado o `maven`

```bash
mvn install
```

3) Agora vamos configurar o `Redis` em um `Docker` para rodar na porta `6379`, para isso basta executar o comando abaixo no terminal

```bash
docker run -it --name redis -p 6379:6379 redis:5.0.3
```

4) Por fim, execute a aplicação que ficará disponivel em seu [http://localhost:8080/](http://localhost:8080/)
## Testes
- Após realizado a instalação, para verificar a cobertura de testes abra o HTML localizado no seguinte diretório
    ```path
    .../todo-list-api/target/site/jacoco/index.html
    ```
- Também existe um plano de teste de carga para o **JMeter**, ele esta localizado no diretório abaixo, abra-o utilizando o **JMeter** e execute os testes
     ```path
    .../todo-list-api/jMeter/Test Plan.jmx
    ```
- Também foi disponibilizado os arquivos da Collection caso prefira rodar os testes no **POSTMAN**
     ```path
    .../todo-list-api/postman/*
    ```
## Indicadores da API
- Actuator
```http
  GET /actuator
```
- Spring Admin
    
    Para acessar a dashboard contendo os dados de performance e métricas da aplicação, basta acessar o seguinte link através de seu navegador: [http://localhost:8080/wallboard](http://localhost:8080/wallboard)
## Documentação da API

#### Autenticação - Retorna `access_token` que deverá ser usado em todas as outras requisições

```http
  POST /oauth/token
```
- Authorization da requisição

| Parâmetro   | Valor    | Descrição                                |
| :---------- | :--------| :--------------------------------------- |
| `client`    | `client` | **Obrigatório**. Nome do cliente da API  |
| `password`  | `123`    | **Obrigatório**. Senha do cliente da API |

- Body da requisição - JSON contendo os parâmetros abaixo

| Parâmetro    | Valor       | Descrição                           |
| :----------- | :---------- | :---------------------------------- |
| `grant_type` | `password`  | **Obrigatório**. Valor fixo         |
| `username`   | `guilherme` | **Obrigatório**. Nome de usuário    |
| `password`   | `123`       | **Obrigatório**. Senha do usuário   |

- Exemplo de resposta
```json
{
    "access_token": "92d2dd27-0d0b-40a3-a70a-698440cd2b3d",
    "token_type": "bearer",
    "refresh_token": "45c01fec-ef20-4eba-ba13-ae3248959d75",
    "expires_in": 114,
    "scope": "password"
}
```

#### Cadastrar Task

```http
  POST /v1/tasks/
```
- Authorization da requisição - **Type:** `Bearer Token`

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `Bearer ee086390-fa84-4678-b714-d7c5f3877461` | **Obrigatório**. `access_token` obtido na rota de Autenticação |

- Body da requisição - JSON contendo os parâmetros abaixo

| Parâmetro   | Valor       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `title`      | `Abastecer o carro` | **Obrigatório**. Título da tarefa a ser cadastrada |
| `description`      | `lembrar de abastecer o carro` | **Obrigatório**. Descrição da tarefa a ser cadastrada |
| `status`      | `0` | **Obrigatório**. Status da tarefa, podendo ser `0` para `PENDING` ou `1` para `COMPLETED` |

- Exemplo de resposta
```json
{
    "idTask": "f3a04517-5738-4fb2-8a07-482047121e4e",
    "user": {
        "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
    },
    "inclusionDate": "2022-03-20T21:22:48.8001796",
    "modificationDate": null,
    "title": "Abastecer o carro",
    "description": "lembrar de abastecer o carro",
    "status": "PENDING"
}
```

#### Pesquisar Task por ID

```http
  GET /v1/tasks/{$idTask}
```

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `{$idTask}` | `0bebc424-6494-43ec-b697-23d568bba7da` | **Obrigatório**. Id da Task |

- Authorization da requisição - **Type:** `Bearer Token`

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `Bearer ee086390-fa84-4678-b714-d7c5f3877461` | **Obrigatório**. `access_token` obtido na rota de Autenticação |

- Exemplo de resposta
```json
{
    "idTask": "4afa141f-76c4-4eb6-b654-f248fd6ed88e",
    "user": {
        "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
    },
    "inclusionDate": "2022-03-20T20:35:13.438159",
    "modificationDate": null,
    "title": "Abastecer o carro",
    "description": "lembrar de abastecer o carro",
    "status": "PENDING"
}
```

#### Listar todas as Tasks

```http
  GET /v1/tasks/
```
- Authorization da requisição - **Type:** `Bearer Token`

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `Bearer ee086390-fa84-4678-b714-d7c5f3877461` | **Obrigatório**. `access_token` obtido na rota de Autenticação |

- Exemplo de resposta
```json
[
    {
        "idTask": "bfb079fd-cfb5-46b4-9b80-09a04d0e38c2",
        "user": {
            "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
        },
        "inclusionDate": "2022-03-18T19:40:55.331247",
        "modificationDate": "2022-03-19T19:40:55.331247",
        "title": "Comprar frutas",
        "description": "Comprar frutas na feira para a dieta",
        "status": "PENDING"
    },
    {
        "idTask": "4afa141f-76c4-4eb6-b654-f248fd6ed88e",
        "user": {
            "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
        },
        "inclusionDate": "2022-03-20T20:35:13.438159",
        "modificationDate": null,
        "title": "Abastecer o carro",
        "description": "lembrar de abastecer o carro",
        "status": "PENDING"
    },
    {
        "idTask": "1c71ba60-dca0-4245-b9de-b5d78ade6f7f",
        "user": {
            "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
        },
        "inclusionDate": "2022-03-20T19:40:55.312246",
        "modificationDate": null,
        "title": "Comprar leite",
        "description": "Comprar leita desnatado para a dieta",
        "status": "COMPLETED"
    }
]
```

#### Listar todas as Tasks filtrando pelo Status

```http
  GET /v1/tasks/?status={$status}
```
| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `{$status}` | `pending` | **Obrigatório**. Status das tarefas podendo ser `pending` ou `completed` - letras maiúsculas ou minúsculas (Case insensitive) |

- Authorization da requisição - **Type:** `Bearer Token`

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `Bearer ee086390-fa84-4678-b714-d7c5f3877461` | **Obrigatório**. `access_token` obtido na rota de Autenticação |

- Exemplo de resposta
```json
[
    {
        "idTask": "1c71ba60-dca0-4245-b9de-b5d78ade6f7f",
        "user": {
            "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
        },
        "inclusionDate": "2022-03-20T19:40:55.312246",
        "modificationDate": null,
        "title": "Comprar leite",
        "description": "Comprar leita desnatado para a dieta",
        "status": "COMPLETED"
    }
]
```

#### Atualizar tarefa

```http
  PATCH /v1/tasks/{$idTask}
```

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `{$idTask}` | `0bebc424-6494-43ec-b697-23d568bba7da` | **Obrigatório**. Id da Task |

- Authorization da requisição - **Type:** `Bearer Token`

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `Bearer ee086390-fa84-4678-b714-d7c5f3877461` | **Obrigatório**. `access_token` obtido na rota de Autenticação |

- Body da requisição - JSON contendo os parâmetros abaixo

| Parâmetro   | Valor       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `title`      | `Abastecer o carro` | **Obrigatório**. Título da tarefa a ser atualizada |
| `description`      | `lembrar de abastecer o carro` | **Obrigatório**. Descrição da tarefa a ser atualizada |
| `status`      | `1` | **Obrigatório**. Status da tarefa, podendo ser `0` para `PENDING` ou `1` para `COMPLETED` |

- Exemplo de resposta
```json
{
    "idTask": "4afa141f-76c4-4eb6-b654-f248fd6ed88e",
    "user": {
        "idUser": "f4eff9cf-e496-427c-9629-bd35edaa2190"
    },
    "inclusionDate": "2022-03-20T20:35:13.438159",
    "modificationDate": "2022-03-20T21:13:20.3753757",
    "title": "Abastecer o carro",
    "description": "lembrar de abastecer o carro",
    "status": "COMPLETED"
}
```

#### Deletar tarefa

```http
  DELETE /v1/tasks/{$idTask}
```

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `{$idTask}` | `0bebc424-6494-43ec-b697-23d568bba7da` | **Obrigatório**. Id da Task |

- Authorization da requisição - **Type:** `Bearer Token`

| Parâmetro   | Valor       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `token` | `Bearer ee086390-fa84-4678-b714-d7c5f3877461` | **Obrigatório**. `access_token` obtido na rota de Autenticação |

- Exemplo de resposta
```http
  STATUS: 202 Accepted
```