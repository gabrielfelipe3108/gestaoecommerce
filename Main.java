import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

       //Exemplos para conseguir rodar
        Cliente clienteExemplo = new Cliente("Ana Souza", "123.456.789-00", "ana@email.com", "Rua das Flores, 100");
        Funcionario funcExemplo = new Funcionario("Carlos Silva", "987.654.321-11", "carlos@email.com", "FUNC-42", new Estoque());
        Distribuidor distExemplo = new Distribuidor("Tech Distribuidora LTDA", "12.345.678/0001-99", "contato@techdist.com");

        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE LOJA - MENU PRINCIPAL     ");
            System.out.println("========================================");
            System.out.println("1. Acessar como Cliente (Comprar)");
            System.out.println("2. Acessar como Funcionario (Gerenciar Estoque/Produtos)");
            System.out.println("3. Ver Informacoes do Distribuidor");
            System.out.println("0. Sair");
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
                        exibirInfoDistribuidor(distExemplo);
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema. Ate logo!");
                        break;
                    default:
                        System.out.println("Opcao invalida! Tente novamente.");
                }
            } else {
                System.out.println("Por favor, digite um número valido.");
                scanner.nextLine(); // Descarta entrada inválida
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenuCliente(Scanner scanner, Cliente cliente) {
        System.out.println("\n--- AREA DO CLIENTE ---");
        System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
        System.out.println("Endereço cadastrado: " + cliente.getEndereco());
        System.out.println("[Simulação] Simulando realização de compra...");
        cliente.realizarCompra();
        System.out.println("Ação executada com sucesso!");
    }

    private static void exibirMenuFuncionario(Scanner scanner, Funcionario func) {
        System.out.println("\n--- AREA DO FUNCIONÁRIO ---");
        System.out.println("Funcionario: " + func.getNome() + " (Matricula: " + func.getMatricula() + ")");
        System.out.println("1. Gerenciar Produtos no Estoque");
        System.out.print("Pressione Enter para retornar ao menu principal...");
        scanner.nextLine();
    }

    private static void exibirInfoDistribuidor(Distribuidor dist) {
        System.out.println("\n--- DADOS DO DISTRIBUIDOR ---");
        System.out.println("Razao Social: " + dist.getNome());
        System.out.println("CNPJ: " + dist.getCnpj());
        System.out.println("E-mail: " + dist.getEmail());
    }
}