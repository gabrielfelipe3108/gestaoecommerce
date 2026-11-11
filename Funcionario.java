import java.util.List;
public class Funcionario extends Pessoa{
    private String matricula;
    private Estoque estoque;

    public Funcionario(String nome, String cpf, String email, String matricula, Estoque estoque) {
        super(nome, cpf, email);
        this.matricula = matricula;
        this.estoque = estoque;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void cadastrarProduto(Produto produto, int quantidade) {
        if (produto != null && estoque.buscarProduto(produto.getCodigo()) != null) {
            throw new IllegalArgumentException("Já existe um produto com o código " + produto.getCodigo() + ".");
        }
        estoque.adicionarEstoque(produto, quantidade);
    }

    public void removerUnidades(int codigo, int quantidade) {
        estoque.removerEstoque(codigo, quantidade);
    }

    public List<Produto> listarProdutos() {
        return estoque.listarProdutos();
    }

    public int consultarQuantidade(int codigo) {
        return estoque.consultarEstoque(codigo);
    }
}