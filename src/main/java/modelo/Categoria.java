package modelo;

import java.io.Serializable;

// Representa uma categoria de produto no sistema.
// Cada categoria possui um nome, um tamanho (definido pelo enum Tamanho),um tipo de embalagem e um identificador único.
// É utilizada para classificar os produtos cadastrados, permitindo melhor organização e consultas filtradas no sistema de estoque.
// Esta classe implementa Serializable para permitir sua transmissão em chamadas remotas via RMI e persistência, quando necessário.
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;  //Versão da classe para controle de serialização.

    private String nome;  // Nome da categoria (ex.: Bebidas, Higiene, Laticínios).
    private Tamanho tamanho; // Tamanho da categoria, definido pelos valores do enum Tamanho.
    private String embalagem; // Tipo de embalagem dos produtos da categoria (ex.: Lata, Vidro, Plástico).
    private int idCategoria; // Identificador único da categoria no banco de dados.

    // Construtor padrão, inicializa a categoria com valores default.
    public Categoria() {
        this("", Tamanho.Pequeno, "", 0);
    }

    // Construtor completo da classe Categoria.
    public Categoria(String nome, Tamanho tamanho, String embalagem, int idCategoria) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
        this.idCategoria = idCategoria;
    }

    // Construtor que cria uma categoria sem ID, útil antes de salvar no banco.
    public Categoria(String nome, Tamanho tamanho, String embalagem) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.embalagem = embalagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public String getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(String embalagem) {
        this.embalagem = embalagem;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
