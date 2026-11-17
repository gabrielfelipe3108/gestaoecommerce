package logica;

public class Cliente extends Pessoa {
    private String endereco;
    private CarrinhoDeCompras carrinho;

    public Cliente(String nome, String cpf, String email, String endereco) {
        super(nome, cpf, email);
        this.endereco = endereco;
        this.carrinho = new CarrinhoDeCompras();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public CarrinhoDeCompras getCarrinho() {
        return carrinho;
    }
}