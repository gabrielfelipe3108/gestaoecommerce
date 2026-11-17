package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import logica.*;

public class TelaFuncionario extends JFrame {
    private Funcionario funcionario;
    private Estoque estoque;
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabela;

    public TelaFuncionario(Funcionario funcionario, Estoque estoque) {
        this.funcionario = funcionario;
        this.estoque = estoque;

        setTitle("Área do Funcionário - " + funcionario.getNome());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel Superior (Informações do Funcionário)
        JPanel painelTopo = new JPanel(new GridLayout(2, 1));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        JLabel lblNome = new JLabel("Funcionário: " + funcionario.getNome() + " (Matrícula: " + funcionario.getMatricula() + ")");
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel lblSub = new JLabel("Gerenciamento de Produtos e Inventário do Estoque");
        
        painelTopo.add(lblNome);
        painelTopo.add(lblSub);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela Central (Listagem do Estoque)
        String[] colunas = {"Código", "Tipo", "Nome", "Preço Base", "Preço Final", "Qtd Estoque"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Apenas leitura
            }
        };
        tabelaEstoque = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEstoque);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Inventário Atual"));
        add(scrollPane, BorderLayout.CENTER);

        // Painel Inferior (Botões de Ação)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnCadFisico = new JButton("Cadastrar Físico");
        JButton btnCadDigital = new JButton("Cadastrar Digital");
        JButton btnRemover = new JButton("Remover Unidades");
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnVoltar = new JButton("Voltar");

        painelBotoes.add(btnCadFisico);
        painelBotoes.add(btnCadDigital);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVoltar);
        
        add(painelBotoes, BorderLayout.SOUTH);

        // Carrega os dados iniciais
        atualizarTabela();

        // --- AÇÕES DOS BOTÕES (EVENTOS) ---
        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnCadFisico.addActionListener(e -> abrirDialogoCadastrarFisico());
        btnCadDigital.addActionListener(e -> abrirDialogoCadastrarDigital());
        btnRemover.addActionListener(e -> abrirDialogoRemoverUnidades());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Produto> produtos = funcionario.listarProdutos();

        for (Produto p : produtos) {
            int qtd = funcionario.consultarQuantidade(p.getCodigo());
            String tipo = (p instanceof ProdutoFisico) ? "Físico" : "Digital";
            Object[] linha = {
                p.getCodigo(),
                tipo,
                p.getNome(),
                String.format("R$ %.2f", p.getPrecoBase()),
                String.format("R$ %.2f", p.calcularPrecoFinal()),
                qtd
            };
            modeloTabela.addRow(linha);
        }
    }

    private void abrirDialogoCadastrarFisico() {
        JTextField txtCodigo = new JTextField(5);
        JTextField txtNome = new JTextField(15);
        JTextField txtPreco = new JTextField(10);
        JTextField txtPeso = new JTextField(10);
        JTextField txtDimensoes = new JTextField(10);
        JTextField txtQtd = new JTextField(5);

        JPanel painelForm = new JPanel(new GridLayout(0, 1, 5, 5));
        painelForm.add(new JLabel("Código (inteiro):")); painelForm.add(txtCodigo);
        painelForm.add(new JLabel("Nome do Produto:")); painelForm.add(txtNome);
        painelForm.add(new JLabel("Preço Base (R$):")); painelForm.add(txtPreco);
        painelForm.add(new JLabel("Peso (kg):")); painelForm.add(txtPeso);
        painelForm.add(new JLabel("Dimensões (ex: 45x15x4 cm):")); painelForm.add(txtDimensoes);
        painelForm.add(new JLabel("Quantidade Inicial:")); painelForm.add(txtQtd);

        int resultado = JOptionPane.showConfirmDialog(this, painelForm, "Cadastrar Produto Físico",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim().replace(',', '.'));
                double peso = Double.parseDouble(txtPeso.getText().trim().replace(',', '.'));
                String dimensoes = txtDimensoes.getText().trim();
                int qtd = Integer.parseInt(txtQtd.getText().trim());

                ProdutoFisico pf = new ProdutoFisico(codigo, nome, preco, peso, dimensoes);
                funcionario.cadastrarProduto(pf, qtd);
                
                JOptionPane.showMessageDialog(this, "Produto físico cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirDialogoCadastrarDigital() {
        JTextField txtCodigo = new JTextField(5);
        JTextField txtNome = new JTextField(15);
        JTextField txtPreco = new JTextField(10);
        JTextField txtTamanho = new JTextField(10);
        JTextField txtLink = new JTextField(15);
        JTextField txtQtd = new JTextField(5);

        JPanel painelForm = new JPanel(new GridLayout(0, 1, 5, 5));
        painelForm.add(new JLabel("Código (inteiro):")); painelForm.add(txtCodigo);
        painelForm.add(new JLabel("Nome do Produto:")); painelForm.add(txtNome);
        painelForm.add(new JLabel("Preço Base (R$):")); painelForm.add(txtPreco);
        painelForm.add(new JLabel("Tamanho do Arquivo (MB):")); painelForm.add(txtTamanho);
        painelForm.add(new JLabel("Link de Download:")); painelForm.add(txtLink);
        painelForm.add(new JLabel("Quantidade de Licenças:")); painelForm.add(txtQtd);

        int resultado = JOptionPane.showConfirmDialog(this, painelForm, "Cadastrar Produto Digital",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText().trim());
                String nome = txtNome.getText().trim();
                double preco = Double.parseDouble(txtPreco.getText().trim().replace(',', '.'));
                double tamanho = Double.parseDouble(txtTamanho.getText().trim().replace(',', '.'));
                String link = txtLink.getText().trim();
                int qtd = Integer.parseInt(txtQtd.getText().trim());

                ProdutoDigital pd = new ProdutoDigital(codigo, nome, preco, tamanho, link);
                funcionario.cadastrarProduto(pd, qtd);
                
                JOptionPane.showMessageDialog(this, "Produto digital cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void abrirDialogoRemoverUnidades() {
        int linhaSelecionada = tabelaEstoque.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um produto na tabela para remover unidades.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeProduto = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        int qtdAtual = funcionario.consultarQuantidade(codigo);

        String inputQtd = JOptionPane.showInputDialog(this,
                "Produto: " + nomeProduto + "\nQuantidade atual em estoque: " + qtdAtual + "\nDigite a quantidade a remover:",
                "Remover Unidades",
                JOptionPane.QUESTION_MESSAGE);

        if (inputQtd != null && !inputQtd.trim().isEmpty()) {
            try {
                int qtdRemover = Integer.parseInt(inputQtd.trim());
                funcionario.removerUnidades(codigo, qtdRemover);
                
                JOptionPane.showMessageDialog(this, "Unidades removidas com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover unidades: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}