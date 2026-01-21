# Banco Digital POO - JavaBank Pro

Este é um projeto de um sistema bancário simples desenvolvido em Java, focado na aplicação dos pilares da Programação Orientada a Objetos (POO): Abstração, Encapsulamento, Herança e Polimorfismo.

## Diagrama de Classes UML

```mermaid
classDiagram
    class Banco {
        -String nome
        -List~Conta~ contas
        +Banco(String nome)
        +String getNome()
        +List~Conta~ getContas()
        +void cadastrarConta(Cliente cliente, int tipo)
        +Conta buscarConta(int numero)
    }

    class Cliente {
        -String nome
        -String cpf
        +Cliente(String nome, String cpf)
        +String getNome()
        +String getCpf()
        +void setNome(String nome)
        +void setCpf(String cpf)
    }

    class IConta {
        <<interface>>
        +boolean sacar(double valor)
        +void depositar(double valor)
        +void transferir(double valor, IConta contaDestino)
        +void imprimirExtrato()
    }

    class Conta {
        <<abstract>>
        -static int AGENCIA_PADRAO
        -static int SEQUENCIAL
        #int agencia
        #int numero
        #double saldo
        #Cliente cliente
        +Conta(Cliente cliente)
        +void sacar(double valor)
        +void depositar(double valor)
        +void transferir(double valor, IConta contaDestino)
        #void imprimirInfosComuns()
    }

    class ContaCorrente {
        +ContaCorrente(Cliente cliente)
        +void imprimirExtrato()
    }

    class ContaPoupanca {
        +ContaPoupanca(Cliente cliente)
        +void imprimirExtrato()
    }

    Banco "1" *-- "*" Conta : possui
    Conta "1" *-- "1" Cliente : pertence a
    Conta ..|> IConta : implementa
    ContaCorrente --|> Conta : herda
    ContaPoupanca --|> Conta : herda
```

## 🚀 Funcionalidades

O sistema permite realizar operações bancárias via console (CLI) e persiste os dados em um arquivo de texto (`banco_dados.txt`), simulando um banco de dados real.

### Gestão de Clientes e Contas
- **Cadastrar Conta:** Criação de Conta Corrente (com limite) ou Conta Poupança.
- **Alterar Dados:** Modificação do nome do titular.
- **Excluir Conta:** Remoção lógica da conta do sistema.
- **Relatório:** Listagem de todas as contas cadastradas.

### Operações Financeiras
- **Depósito:** Adicionar fundos a uma conta.
- **Saque:** Retirar fundos (com validação de saldo e limite para Conta Corrente).
- **Transferência:** Mover valores entre contas (origem -> destino).
- **Extrato:** Visualização detalhada do saldo e limites.

## 🛠️ Tecnologias Utilizadas

- **Java (JDK 8+)**: Linguagem principal.
- **Java IO**: Para persistência de dados em arquivo (`FileWriter`, `BufferedReader`).
- **Java Collections**: Uso de `List` e `ArrayList` para gerenciamento em memória.
- **Java Streams**: Para busca otimizada de contas.

## 📂 Estrutura do Projeto

- `Main.java`: Ponto de entrada e menus do sistema.
- `Banco.java`: Gerenciador das contas e persistência de dados.
- `Cliente.java`: Representação do cliente (titular).
- `IConta.java`: Interface que define o contrato das operações.
- `Conta.java`: Classe abstrata com a lógica base.
- `ContaCorrente.java`: Implementação específica com cheque especial.
- `ContaPoupanca.java`: Implementação específica para poupança.

## ▶️ Como Executar

1. Certifique-se de ter o Java instalado.
2. Compile os arquivos na pasta `src`:
   ```bash
   javac *.java
   ```
3. Execute a classe Main:
   ```bash
   java Main
   ```