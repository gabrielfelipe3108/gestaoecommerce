public class Distribuidor extends Pessoa {
    private String cnpj;

    public Distribuidor(String nome, String cnpj, String email) {
        super(nome, null, email); // Distribuidor usa CNPJ no lugar de CPF
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void fornecerProdutos(Estoque estoque, Produto produto, int quantidade) {
    estoque.adicionarEstoque(produto, quantidade);
}