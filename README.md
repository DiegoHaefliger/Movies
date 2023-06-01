# Descrição do Projeto
Importação de um arquivo (.CSV) contendo uma lista de filmes juntamente com seus Estúdios, Produtores e se houve premiação. Com o objetivo de realizar a importação do arquivo .CSV para um banco de dados (H2) e identificar qual o Produto que obteve premiações entre o menor e maior intervalo de tempo.


# Recursos utilizado no desenvolvimento do projeto

 - Linguagem: JAVA com Spring Boot
 - Servidor Web: Tomcat 
 - Banco de Dados: H2 (banco em memória) 
 - Versão do Java: 17 
 - Versão do Spring Boot: 2.7.12 
 - Formato de apresentação dos endpoints: JSON 
 - Autenticação de segurança nos endpoints: JWT 
 - Testes unitários: JUnit 5 
 - Controle de versionamento do Banco de Dados: Liquibase
 - Ferramenta utilizada para testar as APIs: Postman

 # Gerenciamento de Banco de Dados (Liquibase)
Para gerenciar o versionamento do banco de dados foi utilizado o Liquibase. As tabelas do sistema foram criadas utilizando essa ferramenta.

# Acesso ao Banco de Dados
- Link de acesso: http://localhost:8080/h2-console
- Driver Class: org.h2.Driver
- JDBC URL: jdbc: h2:~/movie
- User Name: sa
- Password: (vazio, não informar)

# Autenticação das APIs

Para ter acesso aos endpoints primeiro é necessário realizar a autenticação JWT via TOKEN na rota ([login](http://localhost:8080/login)), onde já existe cadastrado no banco de dados um usuário cuja suas credênciais são:

```json
{
        "login":"admin",
        "senha":"admin123"
}
```

Para gerar o token será necessário copiar o código acima e adicionar no '*Body*' do endpoint.
Com a informação do token em mãos será necessário realizar a autenticação no formato '*Bearer Token*' nos endpoints. Tempo de validação do token está configurado para expirar em 10 horas após sua geração, caso esse prazo seja expirado, será necessário gerar um novo token.

# Endpoints


### Token

> **POST**: http://localhost:8080/login

Lembrando que é necessário enviar os dados do usuário no '*Body*' do endpoint.

Exemplo do retorno:

    eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6IklycmlnYWNhbyIsImlkIjo1LCJleHAiOjE2ODU1OTkxNDF9.XNxFR_alXPlqthwCVwJWaxuZGoil-UvyhAy_trNOYL4


### Produtor com menor e maior intervalos entre prêmios.

> GET: http://localhost:8080/movie/awardsinterval

Para consumir esse endpoint é necessário informar o token de autenticação na aba '*Authorization*' selecionando o formato '*Bearer Token*'.

```json
{
    "min": [
        {
            "producer": "Joel Silver",
            "interval": 1,
            "previousWin": 1990,
            "followingWin": 1991
        },
        {
            "producer": "Allan Carr",
            "interval": 1,
            "previousWin": 1980,
            "followingWin": 1981
        }
    ],
    "max": [
        {
            "producer": "Matthew Vaughn",
            "interval": 13,
            "previousWin": 2002,
            "followingWin": 2015
        }
    ]
}
```

### Todos os filmes

> **GET**: http://localhost:8080/movie

Para consumir esse endpoint é necessário informar o token de autenticação na aba '*Authorization*' selecionando o formato '*Bearer Token*'.

Exemplo do retorno:



```json
[
    {
        "title": "Can't Stop the Music",
        "ano": 1980,
        "studios": [
            {
                "id": 237,
                "descricao": "Associated Film Distribution"
            }
        ],
        "producers": [
            {
                "id": 1441,
                "descricao": "Allan Carr"
            }
        ],
        "winner": true
    }
]
```




### Filmes por ano

> GET: http://localhost:8080/movie/year/{year}

Para consumir esse endpoint é necessário informar o ano em que os filmes foram produzidos além do token de autenticação na aba '*Authorization*' selecionando o formato '*Bearer Token*'.

Exemplo do retorno:

```json
[
    {
        "title": "Battlefield Earth",
        "ano": 2000,
        "studios": [
            {
                "id": 248,
                "descricao": "Warner Bros."
            },
            {
                "id": 264,
                "descricao": "Franchise Pictures"
            }
        ],
        "producers": [
            {
                "id": 1573,
                "descricao": "Jonathan D. Krane"
            },
            {
                "id": 1574,
                "descricao": "Elie Samaha"
            },
            {
                "id": 1575,
                "descricao": "John Travolta"
            }
        ],
        "winner": true
    }
]
```

### Total de ganhadores de prêmios em cada ano

> GET: http://localhost:8080/movie/yearswinners

Para consumir esse endpoint é necessário informar o token de autenticação na aba '*Authorization*' selecionando o formato '*Bearer Token*'.

Exemplo do retorno:

```json
[
    {
        "year_movie": 1980,
        "wins": 1
    },
    {
        "year_movie": 1981,
        "wins": 1
    }
]
```

# Testes unitários

Para rodar os testes unitários basta importar o projeto em sua IDE de preferência, clicar com o botão direito do mouse sobre a pasta '*scr/test/java*' e selecionar a opção '*Run Tests in java*', lembrando que essa opção pode variar dependendo da IDE, nesse caso fui utilizado o *Intellij Idea*.

