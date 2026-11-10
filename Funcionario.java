public class Funcionario extends Pessoa{
    private String matricula;

    public Funcionario(String nome, String cpf, String email, String matricula) {
        super(nome, cpf, email);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void gerenciarProdutos() {
        // Lógica de negócio para gerenciar produtos
    }
}