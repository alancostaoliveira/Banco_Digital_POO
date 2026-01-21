import java.util.Scanner;

/**
 * Classe principal que contém o método main e a interface de usuário via console (CLI).
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco banco = new Banco("JavaBank Pro");
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\n--- BEM-VINDO AO JAVA BANK ---");
            System.out.println("\n---   MENU PRINCIPAL   ---");
            System.out.println("1. Menu Cliente (Cadastros)");
            System.out.println("2. Menu Operações (Saque/Dep/Transf)");
            System.out.println("3. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuCliente(banco, scanner);
                    break;
                case 2:
                    menuOperacoes(banco, scanner);
                    break;
            }
        }
    }

    // Submenu para gerenciamento de clientes e contas (CRUD)
    private static void menuCliente(Banco banco, Scanner scanner) {
        int subOpcao = 0;
        while (subOpcao != 5) {
            System.out.println("\n--- SUBMENU CLIENTE ---");
            System.out.println("1. Cadastrar Conta/Cliente");
            System.out.println("2. Alterar Nome do Cliente");
            System.out.println("3. Excluir Conta");
            System.out.println("4. Relatório Geral");
            System.out.println("5. Voltar");
            System.out.print("Escolha: ");
            subOpcao = scanner.nextInt();

            switch (subOpcao) {
                case 1:
                    System.out.print("Nome: "); scanner.nextLine();
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.next();
                    System.out.print("Tipo (1-Corrente, 2-Poupança): ");
                    int tipo = scanner.nextInt();
                    if (tipo == 1) {
                        System.out.print("Limite Cheque Especial: ");
                        banco.cadastrarConta(new ContaCorrente(new Cliente(nome, cpf), scanner.nextDouble()));
                    } else {
                        banco.cadastrarConta(new ContaPoupanca(new Cliente(nome, cpf)));
                    }
                    break;
                case 2:
                    System.out.print("Número da conta: ");
                    int numAlt = scanner.nextInt();
                    System.out.print("Novo nome: "); scanner.nextLine();
                    banco.alterarCliente(numAlt, scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Número da conta para excluir: ");
                    if (banco.excluirConta(scanner.nextInt())) System.out.println("Excluída!");
                    else System.out.println("Não encontrada.");
                    break;
                case 4:
                    banco.exibirRelatorio();
                    break;
            }
        }
    }

    // Submenu para transações financeiras
    private static void menuOperacoes(Banco banco, Scanner scanner) {
        int subOpcao = 0;
        while (subOpcao != 5) {
            System.out.println("\n--- MENU DE OPERAÇÕES FINANCEIRAS ---");
            System.out.println("1. Depositar");
            System.out.println("2. Sacar");
            System.out.println("3. Transferir");
            System.out.println("4. Ver Extrato");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            subOpcao = scanner.nextInt();

            switch (subOpcao) {
                case 1: // DEPÓSITO
                    System.out.print("Número da Conta para depósito: ");
                    Conta cDep = banco.buscarConta(scanner.nextInt());
                    if (cDep != null) {
                        System.out.print("Valor do Depósito: R$ ");
                        double valorDep = scanner.nextDouble();
                        cDep.depositar(valorDep);
                        banco.salvarDados(); // Persiste a alteração no TXT
                        System.out.println("Depósito realizado!");
                    } else {
                        System.out.println("Erro: Conta não encontrada!");
                    }
                    break;

                case 2: // SAQUE
                    System.out.print("Número da Conta para saque: ");
                    Conta cSaq = banco.buscarConta(scanner.nextInt());
                    if (cSaq != null) {
                        System.out.print("Valor do Saque: R$ ");
                        double valorSaq = scanner.nextDouble();
                        // Tenta sacar e salva apenas se for bem-sucedido (retorno true)
                        if (cSaq.sacar(valorSaq)) {
                            banco.salvarDados(); // Salva o novo saldo no arquivo
                        }
                    } else {
                        System.out.println("Erro: Conta não encontrada!");
                    }
                    break;

                case 3: // TRANSFERÊNCIA
                    System.out.print("Número da SUA Conta (Origem): ");
                    Conta origem = banco.buscarConta(scanner.nextInt());
                    System.out.print("Número da Conta DESTINO: ");
                    Conta destino = banco.buscarConta(scanner.nextInt());

                    if (origem != null && destino != null) {
                        if (origem == destino) {
                            System.out.println("Erro: Não é possível transferir para a mesma conta.");
                        } else {
                            System.out.print("Valor da Transferência: R$ ");
                            double valorTrans = scanner.nextDouble();
                            origem.transferir(valorTrans, destino);
                            banco.salvarDados(); // Salva a alteração nas duas contas
                        }
                    } else {
                        System.out.println("Erro: Uma ou ambas as contas não foram encontradas!");
                    }
                    break;

                case 4: // EXTRATO
                    System.out.print("Número da Conta para ver extrato: ");
                    Conta cExt = banco.buscarConta(scanner.nextInt());
                    if (cExt != null) {
                        cExt.imprimirExtrato();
                    } else {
                        System.out.println("Erro: Conta não encontrada!");
                    }
                    break;

                case 5:
                    System.out.println("Voltando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
}