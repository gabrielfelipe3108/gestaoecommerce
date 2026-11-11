public class ProdutoFisico extends Produto { // classe ProdutoFisico que herda de Produto
    private double peso; // atributo privado peso
    private String dimensoes; // atributo privado dimensoes

    private static final double FRETE_FIXO = 10.00; // constante para frete fixo
    private static final double FRETE_POR_KG = 2.00; // constante para frete por kg

    public ProdutoFisico(int codigo, String nome, double precoBase, double peso, String dimensoes) { // construtor com validação
        super(codigo, nome, precoBase); // chama o construtor da superclasse Produto
        if (peso <= 0) { // validação do peso
            throw new IllegalArgumentException("Peso deve ser maior que zero."); // exceção se inválido
        }
        this.peso = peso; // atribuição do peso
        this.dimensoes = dimensoes; // atribuição das dimensoes
    }

    public double getPeso() { // getter para peso
        return peso; // retorna o peso
    }

    public void setPeso(double peso) { // setter para peso com validação
        if (peso <= 0) { // validação do peso
            throw new IllegalArgumentException("Peso deve ser maior que zero."); // exceção se inválido
        }
        this.peso = peso; // atribuição do peso
    }

    public String getDimensoes() { // getter para dimensoes
        return dimensoes; // retorna as dimensoes
    }

    public void setDimensoes(String dimensoes) { // setter para dimensoes
        this.dimensoes = dimensoes; // atribuição das dimensoes
    }

    public double calcularFrete() { // método para calcular frete
        return FRETE_FIXO + (FRETE_POR_KG * peso); // calcula o frete baseado no peso e constantes definidas
    }

    @Override
    public double calcularPrecoFinal() { // sobrescreve o método calcularPrecoFinal da superclasse Produto
        return getPrecoBase() + calcularFrete(); // retorna o preço base mais o frete calculado
    }
}