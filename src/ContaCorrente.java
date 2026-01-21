/**
 * Classe ContaCorrente que estende Conta.
 * Possui a característica específica de "Cheque Especial" (limite).
 */
public class ContaCorrente extends Conta {
    private double limite; // Cheque especial

    public ContaCorrente(Cliente cliente, double limite) {
        super(cliente);
        this.limite = limite;
    }

    /**
     * Sobrescrita do método sacar.
     * Permite o saque se o valor for menor ou igual ao (Saldo + Limite).
     */
    @Override
    public boolean sacar(double valor) {
        double saldoDisponivel = this.saldo + this.limite;

        if (valor > 0 && valor <= saldoDisponivel) {
            this.saldo -= valor;
            System.out.println("Saque realizado (uso de limite permitido).");
            return true;
        } else {
            System.out.println("Saldo e Limite insuficientes!");
            return false;
        }
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Corrente ===");
        super.imprimirInfosComuns();
        System.out.println(String.format("Limite Cheque Especial: %.2f", this.limite));
        System.out.println(String.format("Saldo Total Disponível: %.2f", (this.saldo + this.limite)));
    }

    public double getLimite() {
        return limite;
    }
}