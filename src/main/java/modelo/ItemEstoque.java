package modelo;

import java.io.Serializable;

// Classe abstrata que representa um item genérico de estoque.
// Esta classe serve como base para outras entidades relacionadas ao estoque, como Produto.
// Ela define atributos essenciais e comuns entre itens, como nome e preço unitário.
// A classe implementa Serializable para permitir que seus objetos sejam transmitidos através
//de comunicação remota via RMI ou armazenados quando necessário.
public abstract class ItemEstoque implements Serializable {

    private static final long serialVersionUID = 1L; // Versão da classe para controle de serialização.

    private String nome; // Nome do item de estoque.
    private double precoUnitario; // Preço unitário do item.

    // Construtor padrão que inicializa o item com valores default.
    public ItemEstoque() {
        this("", 0.0);
    }

    // Construtor completo da classe.
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
