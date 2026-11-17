package gui;

import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logica.*;

public class TelaCliente extends JFrame {
    private Cliente cliente;
    private Estoque estoque;
    private JTable tabelaProdutos; //Tabela
    private DefaultTableModel modeloTabela;

    public TelaCliente(Cliente cliente, Estoque estoque) {
        this.cliente = cliente;
        this.estoque = estoque;

        setTitle("Área do Cliente - " + cliente.getNome());
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel Superior (Boas-vindas e endereço)
        JPanel painelTopo = new JPanel(new GridLayout(2, 1));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel lblBemVindo = new JLabel("Bem-vindo(a), " + cliente.getNome() + "!");
        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel lblEndereco = new JLabel("Endereço: " + cliente.getEndereco());
        
        painelTopo.add(lblBemVindo);
        painelTopo.add(lblEndereco);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela Central (Listagem de Produtos do Estoque)
        String[] colunas = {"Código", "Tipo", "Nome", "Preço Final (R$)", "Qtd Disponível"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela apenas para leitura
            }
        };
        tabelaProdutos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis no Estoque"));
        add(scrollPane, BorderLayout.CENTER);

        // Painel Inferior (Botões de Ação)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        JButton btnCarrinho = new JButton("Ver Carrinho / Finalizar");
        JButton btnVoltar = new JButton("Voltar ao Menu");

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnCarrinho);
        painelBotoes.add(btnVoltar);
        
        add(painelBotoes, BorderLayout.SOUTH);

        // Carrega os produtos iniciais na tabela
        atualizarTabela();

        // --- AÇÕES DOS BOTÕES (EVENTOS) ---
        btnAtualizar.addActionListener(e -> atualizarTabela());

        btnAdicionar.addActionListener(e -> adicionarProdutoSelecionado());

        btnCarrinho.addActionListener(e -> abrirGerenciadorCarrinho());

        btnVoltar.addActionListener(e -> dispose()); // Fecha apenas a tela do cliente
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa a tabela
        List<Produto> produtos = estoque.listarProdutos();

        for (Produto p : produtos) {
            int qtd = estoque.consultarEstoque(p.getCodigo());
            String tipo = (p instanceof ProdutoFisico) ? "Físico" : "Digital";
            Object[] linha = {
                p.getCodigo(),
                tipo,
                p.getNome(),
                String.format("R$ %.2f", p.calcularPrecoFinal()),
                qtd
            };
            modeloTabela.addRow(linha);
        }
    }

    private void adicionarProdutoSelecionado() {
        int linhaSelecionada = tabelaProdutos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um produto na tabela.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        Produto produto = estoque.buscarProduto(codigo);
        int qtdDisponivel = estoque.consultarEstoque(codigo);

        if (produto == null) {
            JOptionPane.showMessageDialog(this, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String inputQtd = JOptionPane.showInputDialog(this, 
            "Produto: " + produto.getNome() + "\nEstoque disponível: " + qtdDisponivel + "\nDigite a quantidade desejada:", 
            "Adicionar ao Carrinho", 
            JOptionPane.QUESTION_MESSAGE);

        if (inputQtd != null && !inputQtd.trim().isEmpty()) {
            try {
                int qtdDesejada = Integer.parseInt(inputQtd.trim());
                
                if (qtdDesejada <= 0) {
                    JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else if (qtdDesejada > qtdDisponivel) {
                    JOptionPane.showMessageDialog(this, "Estoque insuficiente! Disponível apenas: " + qtdDisponivel, "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    cliente.getCarrinho().adicionarProduto(produto, qtdDesejada);
                    JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um número inteiro válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirGerenciadorCarrinho() {
        CarrinhoDeCompras carrinho = cliente.getCarrinho();

        if (carrinho.estaVazio()) {
            JOptionPane.showMessageDialog(this, "Seu carrinho de compras está vazio.", "Carrinho", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder resumo = new StringBuilder("--- ITENS NO SEU CARRINHO ---\n\n");
        for (Map.Entry<Produto, Integer> entry : carrinho.getItens().entrySet()) {
            Produto p = entry.getKey();
            int qtd = entry.getValue();
            double subtotal = p.calcularPrecoFinal() * qtd;
            resumo.append(String.format("- %s (Qtd: %d) | Subtotal: R$ %.2f\n", p.getNome(), qtd, subtotal));
        }
        resumo.append(String.format("\nVALOR TOTAL: R$ %.2f", carrinho.calcularTotal()));

        Object[] opcoes = {"Finalizar Compra", "Continuar Comprando"};
        int escolha = JOptionPane.showOptionDialog(this, resumo.toString(), "Meu Carrinho de Compras",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha == 0) { // Finalizar Compra
            try {
                carrinho.finalizarCompra(estoque);
                JOptionPane.showMessageDialog(this, "Compra finalizada com sucesso!\nO estoque foi atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela(); // Atualiza a tabela para refletir a baixa no estoque
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao finalizar compra: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}