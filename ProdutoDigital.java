public class ProdutoDigital  extends Produto { // classe ProdutoDigital que herda de Produto
    private double tamanhoArquivoMB; // atributo privado tamanhoArquivoMB
    private String linkDownload; // atributo privado linkDownload

    public ProdutoDigital(int codigo, String nome, double precoBase, double tamanhoArquivoMB, String linkDownload) { // construtor com validação
        super(codigo, nome, precoBase); // chama o construtor da superclasse Produto
        if (tamanhoArquivoMB <= 0) { // validação do tamanhoArquivoMB
            throw new IllegalArgumentException("Tamanho do arquivo deve ser maior que zero."); // exceção se inválido
        }
        this.tamanhoArquivoMB = tamanhoArquivoMB; // atribuição do tamanhoArquivoMB
        this.linkDownload = linkDownload; // atribuição do linkDownload
    }

    public double getTamanhoArquivoMB() { // getter para tamanhoArquivoMB
        return tamanhoArquivoMB; // retorna o tamanhoArquivoMB
    }

    public void setTamanhoArquivoMB(double tamanhoArquivoMB) { // setter para tamanhoArquivoMB com validação
        if (tamanhoArquivoMB <= 0) { // validação do tamanhoArquivoMB
            throw new IllegalArgumentException("Tamanho do arquivo deve ser maior que zero."); // exceção se inválido
        }
        this.tamanhoArquivoMB = tamanhoArquivoMB; // atribuição do tamanhoArquivoMB
    }

    public String getLinkDownload() { // getter para linkDownload
        return linkDownload; // retorna o linkDownload
    }

    public void setLinkDownload(String linkDownload) { // setter para linkDownload
        this.linkDownload = linkDownload; // atribuição do linkDownload
    }

    @Override
    public double calcularPrecoFinal() { // sobrescreve o método calcularPrecoFinal da superclasse Produto
        return getPrecoBase(); // retorna apenas o preço base, sem frete para produtos digitais
    }
}
