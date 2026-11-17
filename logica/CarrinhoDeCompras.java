package logica;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarrinhoDeCompras {
    private final Map<Produto, Integer> itens;

    public CarrinhoDeCompras() {
        this.itens = new LinkedHashMap<>();
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        itens.put(produto, itens.getOrDefault(produto, 0) + quantidade);
    }

    public void removerProduto(Produto produto) {
        itens.remove(produto);
    }

    public Map<Produto, Integer> getItens() {
        return Collections.unmodifiableMap(itens);
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            total += entry.getKey().calcularPrecoFinal() * entry.getValue();
        }
        return total;
    }

    public void finalizarCompra(Estoque estoque) {
        if (estaVazio()) {
            throw new IllegalStateException("O carrinho está vazio.");
        }

        // 1. Validação prévia de estoque para todos os itens do carrinho
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto p = entry.getKey();
            int qtdDesejada = entry.getValue();
            int qtdDisponivel = estoque.consultarEstoque(p.getCodigo());

            if (qtdDesejada > qtdDisponivel) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + p.getNome() 
                    + " (Disponível: " + qtdDisponivel + ", Desejado: " + qtdDesejada + ")");
            }
        }

        // 2. Efetiva a baixa no estoque de fato
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            estoque.removerEstoque(entry.getKey().getCodigo(), entry.getValue());
        }

        // 3. Esvazia o carrinho após a compra bem-sucedida
        itens.clear();
    }
}