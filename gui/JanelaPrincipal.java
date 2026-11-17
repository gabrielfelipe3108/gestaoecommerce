package gui;

import java.awt.*;
import javax.swing.*;
import logica.*;

public class JanelaPrincipal extends JFrame {
    private Estoque estoque;
    private Cliente clienteExemplo;
    private Funcionario funcExemplo;

    public JanelaPrincipal() {
        // Configurações básicas da janela principal
        setTitle("Sistema de E-Commerce - Menu Principal");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new BorderLayout(10, 10));

        // 1. Inicialização da Lógica (mesmos dados da versão de terminal)
        estoque = new Estoque();
        clienteExemplo = new Cliente("Ana Souza", "123.456.789-00", "ana@email.com", "Rua das Flores, 100");
        funcExemplo = new Funcionario("Carlos Silva", "987.654.321-11", "carlos@email.com", "FUNC-42", estoque);

        // Cadastrando produtos iniciais de exemplo no estoque compartilhado
        funcExemplo.cadastrarProduto(new ProdutoFisico(1, "Teclado Mecanico", 250.00, 0.9, "45x15x4 cm"), 10);
        funcExemplo.cadastrarProduto(new ProdutoDigital(2, "Curso de Java", 120.00, 850.0, "https://exemplo.com/curso-java"), 100);

        // Título superior
        JLabel lblTitulo = new JLabel("GESTAO DE E-COMMERCE", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel central com os botões do menu
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new GridLayout(3, 1, 10, 10));
        panelBotoes.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnCliente = new JButton("Acessar como Cliente (Comprar)");
        JButton btnFuncionario = new JButton("Acessar como Funcionario");
        JButton btnSair = new JButton("Sair");

        // Estilização básica dos botões
        btnCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        btnFuncionario.setFont(new Font("Arial", Font.PLAIN, 14));
        btnSair.setFont(new Font("Arial", Font.PLAIN, 14));

        panelBotoes.add(btnCliente);
        panelBotoes.add(btnFuncionario);
        panelBotoes.add(btnSair);

        add(panelBotoes, BorderLayout.CENTER);

        // 2. Ações dos botões (Eventos)
        btnCliente.addActionListener(e -> abrirTelaCliente());
        btnFuncionario.addActionListener(e -> abrirTelaFuncionario());
        btnSair.addActionListener(e -> System.exit(0));
    }

    private void abrirTelaCliente() {
        // Abre a Tela do Cliente em formato gráfico reutilizando a lógica existente
        TelaCliente telaCliente = new TelaCliente(clienteExemplo, estoque);
        telaCliente.setVisible(true);
    }

    private void abrirTelaFuncionario() {
        TelaFuncionario telaFuncionario = new TelaFuncionario(funcExemplo, estoque);
        telaFuncionario.setVisible(true);
    }

    public static void main(String[] args) {
        // Garante que a interface gráfica rode na thread segura do Swing (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            new JanelaPrincipal().setVisible(true);
        });
    }
}