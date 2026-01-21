/**
 * Classe que representa o Cliente do banco.
 */
public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // Getters e Setters (Encapsulamento para proteger os dados)
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
