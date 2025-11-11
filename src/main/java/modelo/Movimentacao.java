package modelo;

import java.io.Serializable;
import java.util.Date;

public class Movimentacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idMovimentacao;
    private int idProduto;
    private String tipo;
    private int quantidade;
    private Date data;
    private String observacao;

    public Movimentacao() {
        this(0, 0, "", 0, new Date(), "");
    }

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
