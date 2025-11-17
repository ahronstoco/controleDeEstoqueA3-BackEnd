package modelo;

import java.io.Serializable;
import java.util.Date;

// Classe que representa uma movimentação de estoque.
// Uma movimentação corresponde a qualquer alteração realizada na quantidade de um produto no estoque,
//seja por entrada, saída ou ajustes.
// Cada registro contém informações completas como tipo da operação, quantidade movimentada, data e uma observação.
// A classe implementa Serializable para permitir sua transmissão por RMI e armazenamento persistente quando necessário.
public class Movimentacao implements Serializable {

    private static final long serialVersionUID = 1L; // Versão da classe para controle de serialização.

    private int idMovimentacao; // Identificador único da movimentação.
    private int idProduto; // Identificador do produto relacionado à movimentação.
    private String tipo; // Tipo da movimentação, valores esperados: entrada ou saída.
    private int quantidade; // Quantidade movimentada.
    private Date data; // Data da movimentação.
    private String observacao; // Observação adicional sobre a movimentação.

    // Construtor padrão que inicializa a movimentação com valores default.
    public Movimentacao() {
        this(0, 0, "", 0, new Date(), "");
    }

    // Construtor completo da classe.
    public Movimentacao(int idMovimentacao, int idProduto, String tipo, int quantidade, Date data, String observacao) {
        this.idMovimentacao = idMovimentacao;
        this.idProduto = idProduto;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.data = data;
        this.observacao = observacao;
    }

    public int getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(int idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
