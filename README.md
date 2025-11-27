# Desafio AcademiaDev - Plataforma de Cursos (Clean Architecture)

Este projeto consiste em um protótipo de sistema de gerenciamento de cursos online (CLI), desenvolvido como parte do desafio técnico da minha faculdade IFSP campus Guarulhos. O objetivo principal foi implementar uma solução robusta utilizando **Java Puro** (sem frameworks) seguindo estritamente os princípios da **Clean Architecture**.

## 🏗️ Visão Geral da Arquitetura

O projeto foi estruturado em camadas concêntricas, respeitando a **Regra de Dependência**: o código fonte das camadas internas não tem conhecimento das camadas externas.

### Estrutura de Pacotes
```text
src/main/java/br/com/academiadev
├── domain               # O CORAÇÃO (Regras de Negócio e Entidades)
├── application          # OS CASOS DE USO (Orquestração e Interfaces)
├── infrastructure       # O MUNDO EXTERNO (UI, Banco em Memória, CSV)
└── main                 # PONTO DE ENTRADA (Injeção de Dependência)
```

## Justificativa de Design (Design Rationale)

Conforme solicitado no desafio, abaixo estão as justificativas para as decisões arquiteturais tomadas:

### 1. Regra de Dependência e Camada Domain Pura
A camada `domain` é o núcleo da aplicação e não possui dependências de nenhuma outra camada ou biblioteca externa.

As Entidades (`Student`, `Course`) não são anêmicas; elas possuem regras de negócio intrínsecas.

**Exemplo:** A lógica de validação de matrícula (`student.canEnroll()`) reside na entidade `Student`, garantindo que a regra de negócio esteja protegida e centralizada.

### 2. Application: Abstração via Interfaces
A camada `application` contém os Casos de Uso (`EnrollStudentUseCase`, `GenerateReportsUseCase`). Ela define o que o sistema faz, mas não como os dados são persistidos.

Utilizamos o Princípio da Inversão de Dependência (DIP): Os Casos de Uso dependem apenas de interfaces (`UserRepository`, `TicketRepository`), definidos na própria camada `application`.

### 3. Infrastructure: Isolamento de Detalhes
Todos os detalhes técnicos e interações com o mundo externo foram isolados nesta camada:

- **Persistência em Memória:** As implementações concretas (`UserRepositoryInMemory`) utilizam `HashMap` e `Queue` para simular um banco de dados.
- **CSV com Reflection:** A classe `GenericCsvExporter` utiliza Java Reflection para gerar CSVs dinamicamente. Este detalhe técnico está confinado em `infrastructure.utils`, sendo invisível para as regras de negócio.
- **UI:** A interação com o usuário via terminal (`ConsoleController`) é tratada aqui.

### 4. Injeção de Dependência Manual (Main.java)
Para evitar acoplamento com frameworks de IoC (como Spring) e cumprir os requisitos do desafio, a Injeção de Dependência foi realizada manualmente na classe `Main`.

A classe `Main` atua como a **Composition Root**.

Ela instancia as implementações de infraestrutura (ex: `new UserRepositoryInMemory()`) e as injeta nos construtores dos Casos de Uso.

Isso prova que a aplicação é agnóstica à persistência: para trocar o banco de dados, bastaria alterar o `Main`, sem tocar em nenhuma linha de lógica de negócio.

---

## Funcionalidades Implementadas

- **Gestão de Matrículas:** Validação de regras de planos (Basic vs Premium).
- **Relatórios Analíticos:** Uso intensivo de Java Streams API para filtrar e processar dados.
- **Sistema de Suporte:** Implementação de fila de atendimento (FIFO) utilizando `Queue`.
- **Exportação de Dados:** Exportação dinâmica de relatórios para CSV utilizando Reflection.
- **Interface de Linha de Comando (CLI):** Menus interativos com login simulado.

## Autores

* **Pedro Henrique Vital Guimarães**
* GitHub: [@PHVital](https://github.com/PHVital)
* LinkedIn: [Pedro Henrique Vital Guimarães](https://www.linkedin.com/in/pedro-henrique-vital-guimar%C3%A3es/)
* **Nicollas Bergo Calheiros de Melo**
* GitHub: [@Zneziz](https://github.com/Zneziz)