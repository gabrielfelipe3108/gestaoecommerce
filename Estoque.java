/**
 * TAREFA: implementar a classe Estoque (associação "controla" com Produto, 0..*).
 *
 * - Atributos PRIVADOS e final: Map<Integer, Produto> produtos e
 *   Map<Integer, Integer> quantidades (chave = codigo do produto). Usar LinkedHashMap.
 * - void adicionarEstoque(Produto produto, int quantidade):
 *   valida produto != null e quantidade > 0; cadastra o produto se for novo
 *   (putIfAbsent) e soma a quantidade (merge).
 * - void removerEstoque(int codigo, int quantidade):
 *   erro se o código não existir, se quantidade <= 0 ou se quantidade > disponível.
 *   O estoque nunca pode ficar negativo.
 * - int consultarEstoque(int codigo): retorna a quantidade (0 se não existir).
 * - Produto buscarProduto(int codigo): retorna o produto ou null.
 * - List<Produto> listarProdutos(): retorna lista NÃO modificável (cópia).
 * - Erros: IllegalArgumentException com mensagem clara em português.
 * - PROIBIDO usar Scanner ou System.out.
 * faca os comentarios sempre do lado direito e faca um codigo simples para entender e explicar pro meu professor de poo, to comecando em java agora
 */
public class Estoque { // classe Estoque
    private final Map<Integer, Produto> produtos; // mapa de produtos (chave = código do produto)
    private final Map<Integer, Integer> quantidades; // mapa de quantidades (chave = código do produto)

    public Estoque() { // construtor da classe Estoque
        this.produtos = new LinkedHashMap<>(); // inicializa o mapa de produtos
        this.quantidades = new LinkedHashMap<>(); // inicializa o mapa de quantidades
    }

    public void adicionarEstoque(Produto produto, int quantidade) { // método para adicionar estoque
        if (produto == null) { // validação do produto
            throw new IllegalArgumentException("Produto não pode ser nulo."); // exceção se inválido
        }
        if (quantidade <= 0) { // validação da quantidade
            throw new IllegalArgumentException("Quantidade deve ser maior que zero."); // exceção se inválido
        }
        produtos.putIfAbsent(produto.getCodigo(), produto); // cadastra o produto se for novo
        quantidades.merge(produto.getCodigo(), quantidade, Integer::sum); // soma a quantidade existente
    }

    public void removerEstoque(int codigo, int quantidade) { // método para remover estoque
        if (!produtos.containsKey(codigo)) { // validação do código do produto
            throw new IllegalArgumentException("Código do produto não existe."); // exceção se inválido
        }
        if (quantidade <= 0) { // validação da quantidade
            throw new IllegalArgumentException("Quantidade deve ser maior que zero."); // exceção se inválido
        }
        int quantidadeAtual = quantidades.getOrDefault(codigo, 0); // obtém a quantidade atual do produto
        if (quantidade > quantidadeAtual) { // validação da quantidade disponível
            throw new IllegalArgumentException("Quantidade a remover é maior que a disponível."); // exceção se inválido
        }
        quantidades.put(codigo, quantidadeAtual - quantidade); // atualiza a quantidade no estoque
    }

    public int consultarEstoque(int codigo) { // método para consultar estoque
        return quantidades.getOrDefault(codigo, 0); // retorna a quantidade ou 0 se não existir
    }

    public Produto buscarProduto(int codigo) { // método para buscar produto
        return produtos.get(codigo); // retorna o produto ou null se não existir
    }

    public List<Produto> listarProdutos() { // método para listar produtos
        return Collections.unmodifiableList(new ArrayList<>(produtos.values())); // retorna lista não modificável (cópia)
    }
}


