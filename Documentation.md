# APIRestfull do Banco
A API Restful do Banco foi desenvolvida para demonstrar habilidades em Java, Spring e seus ecossistemas, seguindo os melhores padrões de código. A API oferece recursos para gerenciamento de contas e transferências bancárias.
## Endpoints

### Contas

#### Obter todas as contas

Retorna uma lista paginada de todas as contas.

- **URL:** `/api/contas`
- **Método:** `GET`
- **Parâmetros de Paginação:**
  - `page` (opcional): número da página a ser retornada (padrão: 0)
  - `size` (opcional): número de itens por página (padrão: 10)
  - `sort` (opcional): campo para ordenação (padrão: "id")
  - `direction` (opcional): direção da ordenação (ASC ou DESC) (padrão: ASC)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Lista paginada de objetos `ContaDTO`

#### Obter conta por ID

Retorna os detalhes de uma conta específica com base no ID.

- **URL:** `/api/contas/{id}`
- **Método:** `GET`
- **Parâmetros de Caminho:**
  - `id`: ID da conta a ser obtida
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Objeto `ContaDTO`

#### Obter conta por nome do responsável

Retorna os detalhes de uma conta específica com base no nome do responsável.

- **URL:** `/api/contas`
- **Método:** `GET`
- **Parâmetros de Consulta:**
  - `nomeResponsavel`: Nome do responsável da conta
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Objeto `ContaDTO`

#### Salvar conta

Cria uma nova conta.

- **URL:** `/api/contas`
- **Método:** `POST`
- **Corpo:**
  - Objeto `ContaDTO`
- **Resposta de Sucesso:**
  - Código: 200 (OK)

#### Deletar conta

Deleta uma conta com base no ID.

- **URL:** `/api/contas/{id}`
- **Método:** `DELETE`
- **Parâmetros de Caminho:**
  - `id`: ID da conta a ser deletada
- **Resposta de Sucesso:**
  - Código: 200 (OK)

### Transferências

#### Obter todas as transferências

Retorna uma lista paginada de todas as transferências.

- **URL:** `/api/transferencias`
- **Método:** `GET`
- **Parâmetros de Paginação:**
  - `page` (opcional): número da página a ser retornada (padrão: 0)
  - `size` (opcional): número de itens por página (padrão: 10)
  - `sort` (opcional): campo para ordenação (padrão: "id")
  - `direction` (opcional): direção da ordenação (ASC ou DESC) (padrão: ASC)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Lista paginada de objetos `TransferenciaDTO`

#### Obter transferência por operador e por data

Retorna uma lista paginada de transferências com base no nome do operador e no intervalo de datas.

- **URL:** `/api/transferencias/por-operador-e-data`
- **Método:** `GET`
- **Parâmetros de Paginação:**
  - `page` (opcional): número da página a ser retornada (padrão: 0)
  - `size` (opcional): número de itens por página (padrão: 10)
  - `sort` (opcional): campo para ordenação (padrão: "id")
  - `direction` (opcional): direção da ordenação (ASC ou DESC) (padrão: ASC)
- **Parâmetros de Consulta:**
  - `nomeOperador` (opcional): Nome do operador
  - `dataInicial` (opcional): Data inicial no formato ISO_DATE_TIME (ex: 2023-07-01T00:00:00)
  - `dataFinal` (opcional): Data final no formato ISO_DATE_TIME (ex: 2023-07-15T23:59:59)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Lista paginada de objetos `TransferenciaDTO`

#### Obter transferência por ID

Retorna os detalhes de uma transferência específica com base no ID.

- **URL:** `/api/transferencias/{id}`
- **Método:** `GET`


- **Parâmetros de Caminho:**
  - `id`: ID da transferência a ser obtida
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Objeto `TransferenciaDTO`

#### Realizar transferência

Realiza uma transferência entre contas.

- **URL:** `/api/transferencias/realizar-transferencia`
- **Método:** `POST`
- **Corpo:**
  - Objeto `TransferenciaDTO`
- **Parâmetros de Consulta:**
  - `numeroContaDestino`: Número da conta de destino
  - `numeroContaOrigem`: Número da conta de origem
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Objeto `TransferenciaDTO`

#### Realizar depósito

Realiza um depósito em uma conta.

- **URL:** `/api/transferencias/realizar-deposito`
- **Método:** `POST`
- **Corpo:**
  - Objeto `TransferenciaDTO`
- **Parâmetros de Consulta:**
  - `numeroContaDestino`: Número da conta de destino
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Objeto `TransferenciaDTO`

#### Realizar saque

Realiza um saque em uma conta.

- **URL:** `/api/transferencias/realizar-saque`
- **Método:** `POST`
- **Corpo:**
  - Objeto `TransferenciaDTO`
- **Parâmetros de Consulta:**
  - `numeroContaDestino`: Número da conta de destino
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Objeto `TransferenciaDTO`

#### Obter transferências por período

Retorna uma lista paginada de transferências dentro de um determinado período de datas.

- **URL:** `/api/transferencias/por-periodo`
- **Método:** `GET`
- **Parâmetros de Paginação:**
  - `page` (opcional): número da página a ser retornada (padrão: 0)
  - `size` (opcional): número de itens por página (padrão: 10)
  - `sort` (opcional): campo para ordenação (padrão: "id")
  - `direction` (opcional): direção da ordenação (ASC ou DESC) (padrão: ASC)
- **Parâmetros de Consulta:**
  - `dataInicial`: Data inicial no formato ISO_DATE_TIME (ex: 2023-07-01T00:00:00)
  - `dataFinal`: Data final no formato ISO_DATE_TIME (ex: 2023-07-15T23:59:59)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Lista paginada de objetos `TransferenciaDTO`

#### Obter transferências por operador

Retorna uma lista paginada de transferências realizadas por um operador específico.

- **URL:** `/api/transferencias/por-operador`
- **Método:** `GET`
- **Parâmetros de Consulta:**
  - `nomeOperador`: Nome do operador
- **Parâmetros de Paginação:**
  - `page` (opcional): número da página a ser retornada (padrão: 0)
  - `size` (opcional): número de itens por página (padrão: 10)
  - `sort` (opcional): campo para ordenação (padrão: "id")
  - `direction` (opcional): direção da ordenação (ASC ou DESC) (padrão: ASC)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Lista paginada de objetos `TransferenciaDTO`

#### Obter transferências por conta

Retorna uma lista paginada de transferências relacionadas a uma determinada conta.

- **URL:** `/api/transferencias/por-conta/{idConta}`
- **Método:** `GET`
- **Parâmetros de Caminho:**
  - `idConta`: ID da conta
- **Parâmetros de Paginação:**
  - `page` (opcional): número da página a ser retornada (padrão: 0)
  - `size` (opcional): número de itens por página (padrão: 10)
  - `sort` (opcional): campo para ordenação (padrão: "id")
  - `direction` (opcional): direção da ordenação (ASC ou DESC) (padrão: ASC)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Lista paginada de objetos `TransferenciaDTO`

#### Obter saldo total por período

Retorna o saldo total das transferências dentro de um determinado período de datas.

- **URL:** `/api/transferencias/saldo-total-por-periodo`
- **Método:** `GET`
- **Parâmetros de Consulta:**
  - `dataInicial`: Data inicial no formato ISO_DATE_TIME (ex: 2023-07-01T00:00:00)
  - `data

Final`: Data final no formato ISO_DATE_TIME (ex: 2023-07-15T23:59:59)
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Saldo total no formato BigDecimal

#### Obter saldo total por operador

Retorna o saldo total de transferências realizadas por um determinado número de conta.

- **URL:** `/api/transferencias/saldo-total-por-operador`
- **Método:** `GET`
- **Parâmetros de Consulta:**
  - `numeroConta`: Número da conta
- **Resposta de Sucesso:**
  - Código: 200 (OK)
  - Corpo: Saldo total no formato BigDecimal
