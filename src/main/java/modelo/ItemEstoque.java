package modelo;

import java.io.Serializable;

public abstract class ItemEstoque implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Classe superior contendo somente preço e nome.
    
    private String nome; // Nome do produto.
    private double precoUnitario; // Preço de uma quantidade de cada produto.

    public ItemEstoque() {
        this("",0.0);
    }

    public ItemEstoque(String nome, double precoUnitario) {
        this.nome = nome;
        this.precoUnitario = precoUnitario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}