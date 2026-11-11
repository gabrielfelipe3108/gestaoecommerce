public abstract class Produto {// classe Produto
    private int codigo; // atributo privado codigo
    private String nome; // atributo privado nome
    private double precoBase; // atributo privado precoBase

    public Produto(int codigo, String nome, double precoBase) { // construtor com validação
        if (codigo <= 0) { // validação do codigo
            throw new IllegalArgumentException("Código deve ser maior que zero."); // exceção se inválido
        }
        if (nome == null || nome.trim().isEmpty()) { // validação do nome
            throw new IllegalArgumentException("Nome não pode ser vazio."); // exceção se inválido
        }
        if (precoBase < 0) { // validação do precoBase
            throw new IllegalArgumentException("Preço base não pode ser negativo."); // exceção se inválido
        }
        this.codigo = codigo; // atribuição do codigo
        this.nome = nome; // atribuição do nome
        this.precoBase = precoBase; // atribuição do precoBase
    }

    public int getCodigo() { // getter para codigo
        return codigo; // retorna o codigo
    }

    public String getNome() { // getter para nome
        return nome; // retorna o nome
    }

    public void setNome(String nome) { // setter para nome com validação
        if (nome == null || nome.trim().isEmpty()) { // validação do nome
            throw new IllegalArgumentException("Nome não pode ser vazio."); // exceção se inválido
        }
        this.nome = nome; // atribuição do nome
    }

    public double getPrecoBase() { // getter para precoBase
        return precoBase; // retorna o precoBase
    }

    public void setPrecoBase(double precoBase) { // setter para precoBase com validação
        if (precoBase < 0) { // validação do precoBase
            throw new IllegalArgumentException("Preço base não pode ser negativo."); // exceção se inválido
        }
        this.precoBase = precoBase; // atribuição do precoBase
    }

    public abstract double calcularPrecoFinal(); // método abstrato para calcular preço final
}