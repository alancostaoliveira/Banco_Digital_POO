/**
 * Interface que define o contrato de operações bancárias básicas.
 * Garante que qualquer tipo de conta implemente estes métodos essenciais.
 */
public interface IConta {
    boolean sacar(double valor); // Retorna true se o saque for bem-sucedido, false caso contrário
    void depositar(double valor);
    void transferir(double valor, IConta contaDestino);
    void imprimirExtrato();
}