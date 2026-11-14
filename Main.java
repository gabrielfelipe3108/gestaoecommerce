import java.util.List;
import java.util.Scanner;

public class Main {
    private static final boolean USAR_CORES = true;

    private static final String RESET = USAR_CORES ? "\u001B[0m" : "";
    private static final String NEGRITO = USAR_CORES ? "\u001B[1m" : "";
    private static final String CIANO = USAR_CORES ? "\u001B[36m" : "";
    private static final String AMARELO = USAR_CORES ? "\u001B[33m" : "";
    private static final String VERMELHO = USAR_CORES ? "\u001B[31m" : "";
    private static final String VERDE = USAR_CORES ? "\u001B[32m" : "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        Estoque estoque = new Estoque();

        //Exemplos para conseguir rodar
        Cliente clienteExemplo = new Cliente("Ana Souza", "123.456.789-00", "ana@email.com", "Rua das Flores, 100");
        Funcionario funcExemplo = new Funcionario("Carlos Silva", "987.654.321-11", "carlos@email.com", "FUNC-42", estoque);
        Distribuidor distExemplo = new Distribuidor("Tech Distribuidora LTDA", "12.345.678/0001-99", "contato@techdist.com");

        funcExemplo.cadastrarProduto(new ProdutoFisico(1, "Teclado Mecanico", 250.00, 0.9, "45x15x4 cm"), 10);
        funcExemplo.cadastrarProduto(new ProdutoDigital(2, "Curso de Java", 120.00, 850.0, "https://exemplo.com/curso-java"), 100);

        do {
            titulo("MENU PRINCIPAL");
            opcao("1", "Acessar como Cliente (Comprar)");
            opcao("2", "Acessar como Funcionario (Gerenciar Estoque/Produtos)");
            opcao("3", "Acessar como Distribuidor");
            opcao("0", "Sair");
            System.out.print("Escolha uma opcao: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer

                switch (opcao) {
                    case 1:
                        exibirMenuCliente(scanner, clienteExemplo);
                        break;
                    case 2:
                        exibirMenuFuncionario(scanner, funcExemplo);
                        break;
                    case 3:
                        exibirMenuDistribuidor(scanner, distExemplo, estoque);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Ate logo!");
                        break;
                    default:
                        erro("Opcao invalida! Tente novamente.");
                }
            } else {
                erro("Por favor, digite um numero valido.");
                scanner.nextLine(); // Descarta entrada invalida
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void titulo(String texto) {
        System.out.println("\n" + CIANO + NEGRITO + texto + RESET);
    }

    private static void opcao(String numero, String texto) {
        System.out.println(AMARELO + numero + RESET + ". " + texto);
    }

    private static void erro(String texto) {
        System.out.println(VERMELHO + texto + RESET);
    }

    private static void sucesso(String texto) {
        System.out.println(VERDE + texto + RESET);
    }

    private static void exibirMenuCliente(Scanner scanner, Cliente cliente) {
        titulo("AREA DO CLIENTE");
        System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
        System.out.println("Endereco cadastrado: " + cliente.getEndereco());
    }

    private static void exibirMenuFuncionario(Scanner scanner, Funcionario func) {
        int opcao = -1;
        while (opcao != 0) {
            titulo("AREA DO FUNCIONARIO");
            System.out.println("Funcionario: " + func.getNome() + " (Matricula: " + func.getMatricula() + ")");
            opcao("1", "Cadastrar produto fisico");
            opcao("2", "Cadastrar produto digital");
            opcao("3", "Listar produtos e estoque");
            opcao("4", "Remover unidades do estoque");
            opcao("0", "Voltar ao menu principal");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = lerInt(scanner);
                switch (opcao) {
                    case 1:
                        cadastrarProdutoFisico(scanner, func);
                        break;
                    case 2:
                        cadastrarProdutoDigital(scanner, func);
                        break;
                    case 3:
                        listarProdutos(func.listarProdutos(), func);
                        break;
                    case 4:
                        System.out.print("Codigo do produto: ");
                        int codigo = lerInt(scanner);
                        System.out.print("Quantidade a remover: ");
                        int qtd = lerInt(scanner);
                        func.removerUnidades(codigo, qtd);
                        sucesso("Unidades removidas. Estoque atual: " + func.consultarQuantidade(codigo));
                        break;
                    case 0:
                        break;
                    default:
                        erro("Opcao invalida! Tente novamente.");
                }
            } catch (IllegalArgumentException e) {
                erro("Erro: " + e.getMessage());
            }
        }
    }

    private static void cadastrarProdutoFisico(Scanner scanner, Funcionario func) {
        System.out.print("Codigo: ");
        int codigo = lerInt(scanner);
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preco base (R$): ");
        double preco = lerDouble(scanner);
        System.out.print("Peso (kg): ");
        double peso = lerDouble(scanner);
        System.out.print("Dimensoes (ex: 30x20x10 cm): ");
        String dimensoes = scanner.nextLine();
        System.out.print("Quantidade inicial em estoque: ");
        int qtd = lerInt(scanner);

        func.cadastrarProduto(new ProdutoFisico(codigo, nome, preco, peso, dimensoes), qtd);
        sucesso("Produto fisico cadastrado com sucesso!");
    }

    private static void cadastrarProdutoDigital(Scanner scanner, Funcionario func) {
        System.out.print("Codigo: ");
        int codigo = lerInt(scanner);
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preco base (R$): ");
        double preco = lerDouble(scanner);
        System.out.print("Tamanho do arquivo (MB): ");
        double tamanho = lerDouble(scanner);
        System.out.print("Link de download: ");
        String link = scanner.nextLine();
        System.out.print("Quantidade de licencas disponiveis: ");
        int qtd = lerInt(scanner);

        func.cadastrarProduto(new ProdutoDigital(codigo, nome, preco, tamanho, link), qtd);
        sucesso("Produto digital cadastrado com sucesso!");
    }

    private static void listarProdutos(List<Produto> produtos, Funcionario func) {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\n" + NEGRITO + "COD | TIPO     | NOME                      | PRECO FINAL | QTD" + RESET);
        for (Produto p : produtos) {
            String tipo = (p instanceof ProdutoFisico) ? "Fisico" : "Digital";
            System.out.printf("%-3d | %-8s | %-25s | R$ %8.2f | %d%n",
                    p.getCodigo(), tipo, p.getNome(), p.calcularPrecoFinal(), func.consultarQuantidade(p.getCodigo()));
        }
    }

    private static void exibirMenuDistribuidor(Scanner scanner, Distribuidor dist, Estoque estoque) {
        int opcao = -1;
        while (opcao != 0) {
            titulo("AREA DO DISTRIBUIDOR");
            opcao("1", "Ver informacoes do distribuidor");
            opcao("2", "Fornecer produtos ao estoque");
            opcao("0", "Voltar ao menu principal");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = lerInt(scanner);
                switch (opcao) {
                    case 1:
                        exibirInfoDistribuidor(dist);
                        break;
                    case 2:
                        System.out.print("Codigo do produto a fornecer: ");
                        int codigo = lerInt(scanner);
                        Produto produto = estoque.buscarProduto(codigo);
                        if (produto == null) {
                            erro("Produto nao encontrado. O funcionario precisa cadastra-lo antes.");
                            break;
                        }
                        System.out.print("Quantidade fornecida: ");
                        int qtd = lerInt(scanner);
                        dist.fornecerProdutos(estoque, produto, qtd);
                        sucesso("Fornecimento registrado. Estoque atual de \"" + produto.getNome()
                                + "\": " + estoque.consultarEstoque(codigo));
                        break;
                    case 0:
                        break;
                    default:
                        erro("Opcao invalida! Tente novamente.");
                }
            } catch (IllegalArgumentException e) {
                erro("Erro: " + e.getMessage());
            }
        }
    }

    private static void exibirInfoDistribuidor(Distribuidor dist) {
        titulo("DADOS DO DISTRIBUIDOR");
        System.out.println("Razao Social: " + dist.getNome());
        System.out.println("CNPJ: " + dist.getCnpj());
        System.out.println("E-mail: " + dist.getEmail());
    }

    private static int lerInt(Scanner scanner) {
        String linha = scanner.nextLine().trim();
        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite um numero inteiro valido.");
        }
    }

    private static double lerDouble(Scanner scanner) {
        String linha = scanner.nextLine().trim().replace(',', '.');
        try {
            return Double.parseDouble(linha);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Digite um numero valido.");
        }
    }
}