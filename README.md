# Cartly

Marketplace em Java desenvolvido para o Trabalho Final de Programação Orientada a Objetos (UTFPR).

## Integrantes

- Gabriel Schilachta dos Reis
- Gustavo dos Santos Nunes
- Gabriel Felipe Silva Borges

Linguagem: Java

## Proposta

Lojas virtuais que vendem produtos físicos e digitais juntos precisam tratar regras diferentes para cada tipo. O produto físico tem peso, dimensões, frete e controle de estoque. O produto digital, como um e-book ou um software, não tem frete, mas precisa de tamanho de arquivo e link de download. Tratar os dois como se fossem iguais gera erro no preço final e no estoque.

O Cartly é voltado para pequenos e médios lojistas que vendem os dois tipos de produto e precisam organizar o catálogo, o estoque e o cálculo de preço, e também para os clientes que compram na plataforma.

As principais funcionalidades são o cadastro de produtos físicos e digitais, o controle de estoque, o carrinho de compras que calcula o total de qualquer tipo de produto e a aplicação de regras de desconto e frete.

## Funcionalidades

- Cadastro de produtos físicos e digitais
- Controle de estoque (adicionar, remover e consultar quantidades)
- Fornecimento de produtos pelo distribuidor
- Carrinho de compras com cálculo do total
- Finalização de compra com baixa no estoque

## Regra de frete

- Produto físico: R$ 10,00 fixos mais R$ 2,00 por kg
- Produto digital: sem frete

## Modelagem

- `Pessoa` (abstrata): `Cliente`, `Funcionario` e `Distribuidor`
- `Produto` (abstrata): `ProdutoFisico` e `ProdutoDigital`, cada um com seu cálculo de preço final
- `CarrinhoDeCompras` e `Estoque` trabalham com qualquer tipo de `Produto`
- Previstos: interface `Vendivel` para regras de desconto e exceções do domínio, como `ProdutoForaDeEstoqueException` e `DownloadExpiradoException`

O diagrama de classes está em `DiagramaDeClasses.jpg`.

## Como executar

Com o JDK instalado, na pasta dos arquivos `.java`:

```
javac -encoding UTF-8 *.java
java Main
```

O sistema abre um menu no terminal com as áreas do cliente, do funcionário e do distribuidor.