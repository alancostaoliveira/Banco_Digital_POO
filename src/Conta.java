/**
 * Classe abstrata que serve de base para os diferentes tipos de conta.
 * Implementa a interface IConta e contém a lógica comum (herança).
 */
public abstract class Conta implements IConta {
    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1; // Contador estático para gerar números únicos

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    public Conta(Cliente cliente) {
        this.agencia = AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
    }

    /**
     * Implementação padrão de saque.
     * Verifica se há saldo suficiente antes de subtrair.
     */
    @Override
    public boolean sacar(double valor) {
        if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
            System.out.println("Saque realizado com sucesso!");
            return true;
        } else {
            System.out.println("Saldo insuficiente para saque.");
            return false;
        }
    }

    @Override
    public void depositar(double valor) {
        this.saldo += valor;
    }

    /**
     * Transfere valores entre contas.
     * Utiliza os métodos sacar() e depositar() para garantir a consistência.
     */
    @Override
    public void transferir(double valor, IConta contaDestino) {
        if (valor > 0 && this.saldo >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
            System.out.println("Transferência concluída!");
        } else {
            System.out.println("Falha na transferência: Saldo insuficiente.");
        }
    }

    // Método auxiliar para imprimir dados comuns a todas as contas
    protected void imprimirInfosComuns() {
        System.out.println(String.format("Titular: %s", this.cliente.getNome()));
        System.out.println(String.format("Agencia: %d", this.agencia));
        System.out.println(String.format("Numero: %d", this.numero));
        System.out.println(String.format("Saldo: %.2f", this.saldo));
    }
}