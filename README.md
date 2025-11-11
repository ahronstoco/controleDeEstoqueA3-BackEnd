# 🖥️ Back-end - Sistema de Controle de Estoque.
**Disciplina: Sistemas Distribuídos e Mobile – A3**

Este repositório contém a camada **back-end** do sistema de controle de estoque.
**Link para o front-end:** https://github.com/ahronstoco/controleDeEstoqueA3-FrontEnd

---

## ✅ Requisitos Funcionais e Não Funcionais

<details>
  <summary><strong>➕ Funcionais</strong></summary>

- RF01 - O backend deve permitir o cadastro, atualização, listagem e exclusão de produtos, com os atributos: nome, preço unitário, unidade, categoria, estoque atual, estoque mínimo e máximo.

- RF02 - O backend deve permitir o cadastro, atualização, listagem e exclusão de categorias, com os atributos: nome, tamanho (enum) e embalagem.

- RF03 - O backend deve registrar movimentações de estoque (entrada e saída), atualizando automaticamente a quantidade disponível no banco de dados.

- RF04 - O backend deve calcular e retornar alertas para produtos com estoque abaixo do mínimo ou acima do máximo.

- RF05 - O backend deve disponibilizar consultas e relatórios de produtos filtrados por categoria, quantidade e nível de estoque.

- RF06 - O backend deve validar os dados recebidos antes de inserir ou atualizar registros no banco (por exemplo, nome não nulo, preço positivo e quantidade válida).

- RF07 - O backend deve expor os métodos de negócio via RMI, permitindo que o frontend consuma os serviços remotamente.

- RF08 - O backend deve permitir o cálculo do valor total em estoque, considerando preço e quantidade de cada produto.

- RF09 - O backend deve registrar logs informativos no console sobre as operações executadas (cadastro, atualização, movimentação e remoção).

</details>

<details>
  <summary><strong>➖ Não Funcionais</strong></summary>
  
- RNF01 - O backend deve ser desenvolvido na linguagem Java, seguindo o padrão de arquitetura em camadas (modelo, DAO, serviço).
- 
- RNF02 - O backend deve utilizar MySQL como banco de dados relacional, acessado via JDBC.
- 
- RNF03 - A comunicação entre backend e frontend deve ser realizada através de Java RMI (Remote Method Invocation).
- 
- RNF04 - O código-fonte deve seguir boas práticas de orientação a objetos, com tratamento de exceções e reaproveitamento de código.
- 
- RNF05 - O backend deve garantir persistência e integridade dos dados, mantendo as restrições de chave estrangeira e consistência nas movimentações.
- 
- RNF06 - O sistema deve possuir mensagens de log claras para depuração e monitoramento das operações no servidor.
- 
- RNF07 - O backend deve ser independente da interface gráfica, funcionando de forma autônoma e acessível apenas por meio das interfaces RMI.
- 
- RNF08 - O backend deve ser compatível com execução local (desktop), podendo ser iniciado via terminal ou dentro da IDE NetBeans.
  
</details>

---

## 🛠 Tecnologias Utilizadas

- Java 21  
- Apache NetBeans IDE 25  
- MySQL 8.0
- JDBC 8.0.33
- Maven 3.9.9
- RMI Registry

---

## 🗄️ Configuração do Banco de Dados

Para executar o sistema corretamente, utilize as seguintes credenciais de acesso ao MySQL:

- **Usuário:** `a3prog`  
- **Senha:** `unisul@prog3`

Antes de iniciar o sistema, é necessário criar o banco de dados.  
O script SQL para criação da base de dados e tabelas está disponível neste repositório:

📄 [`ControleEstoqueA3.sql`](ControleEstoqueA3.sql)

---

## 👨‍💻 Desenvolvedores do Projeto

| Nome                          | Matrícula      | GitHub                     |
|-------------------------------|----------------|----------------------------|
| Ahron Stoco Simões           | 10724261491    | [ahronstoco](https://github.com/ahronstoco)|
| Eduardo Souza Jeremias       | 10724262460    | [eduardosjeremias](https://github.com/eduardosjeremias) |

---

Feito por estudantes da **Unisul** — 2025.2
