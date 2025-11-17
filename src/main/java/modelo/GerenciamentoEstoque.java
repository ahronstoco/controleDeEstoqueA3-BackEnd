package modelo;

import java.io.Serializable;

// Representa uma operação de gerenciamento de estoque relacionada a um produto.
// Esta classe modela informações básicas sobre um processo de estoque,
//incluindo o identificador do produto e o tipo de operação realizada como entrada ou saída de itens.
// A classe estende ItemEstoque, herdando nome e preço. 
// Embora seja utilizada principalmente para registrar ou transportar dados sobre movimentações simples.
// Esta classe implementa Serializable} para permitir sua transmissão por RMI e persistência, quando necessário.
public class GerenciamentoEstoque extends ItemEstoque implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idProduto; // Identificador do produto ao qual a operação se refere.
    private String operacao; // Tipo de operação realizada no estoque (entrada ou saída).

    // Construtor padrão que inicializa o objeto com valores padrão.
    public GerenciamentoEstoque() {
        this(0, "");
    }

    // Construtor que inicializa um objeto de gerenciamento de estoque com valores definidos.
    public GerenciamentoEstoque(int idProduto, String operacao) {
        this.idProduto = idProduto;
        this.operacao = operacao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setId(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
