import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar as contas e a persistência de dados.
 * Atua como um repositório central.
 */
public class Banco {
    private String nome;
    private List<Conta> contas = new ArrayList<>();
    private static final String ARQUIVO = "banco_dados.txt";

    public Banco(String nome) {
        this.nome = nome;
        carregarDados();
    }

    // Adiciona uma nova conta à lista e salva no arquivo
    public void cadastrarConta(Conta conta) {
        contas.add(conta);
        salvarDados();
    }

    // Remove uma conta pelo número e atualiza o arquivo
    public boolean excluirConta(int numero) {
        Conta conta = buscarConta(numero);
        if (conta != null) {
            contas.remove(conta);
            salvarDados();
            return true;
        }
        return false;
    }

    // Altera o nome do cliente associado a uma conta
    public void alterarCliente(int numero, String novoNome) {
        Conta conta = buscarConta(numero);
        if (conta != null) {
            conta.cliente.setNome(novoNome);
            salvarDados();
        }
    }

    // Busca uma conta na lista usando Streams (Java 8+)
    public Conta buscarConta(int numero) {
        return contas.stream().filter(c -> c.numero == numero).findFirst().orElse(null);
    }

    // Exibe todas as contas carregadas em memória
    public void exibirRelatorio() {
        System.out.println("\n=== RELATÓRIO DE CONTAS CADASTRADAS ===");
        for (Conta c : contas) {
            String tipo = (c instanceof ContaCorrente) ? "Corrente" : "Poupança";
            System.out.println("Nº: " + c.numero + " | Titular: " + c.cliente.getNome() + " | Tipo: " + tipo + " | Saldo: R$" + c.saldo);
        }
    }

    /**
     * Salva o estado atual das contas no arquivo 'banco_dados.txt'.
     * Formato CSV: TIPO;NOME;CPF;NUMERO;SALDO;LIMITE
     */
    void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Conta c : contas) {
                String tipo = (c instanceof ContaCorrente) ? "CC" : "CP";
                double limite = (c instanceof ContaCorrente) ? ((ContaCorrente) c).getLimite() : 0;
                // Formato: TIPO;NOME;CPF;NUMERO;SALDO;LIMITE
                bw.write(tipo + ";" + c.cliente.getNome() + ";" + c.cliente.getCpf() + ";" +
                        c.numero + ";" + c.saldo + ";" + limite);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    /**
     * Lê o arquivo 'banco_dados.txt' e recria os objetos Conta na memória.
     * Executado ao iniciar o Banco.
     */
    private void carregarDados() {
        File file = new File(ARQUIVO);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                Cliente cli = new Cliente(dados[1], dados[2]);
                Conta conta;
                if (dados[0].equals("CC")) {
                    conta = new ContaCorrente(cli, Double.parseDouble(dados[5]));
                } else {
                    conta = new ContaPoupanca(cli);
                }
                conta.numero = Integer.parseInt(dados[3]);
                conta.saldo = Double.parseDouble(dados[4]);
                contas.add(conta);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar arquivo.");
        }
    }

    public String getNome() { return nome; }
}