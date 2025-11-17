package modelo;

import java.io.Serializable;

// Representa um produto cadastrado no sistema de controle de estoque.
// A classe estende ItemEstoque, herdando os atributos básicos de identificação do item (nome e preço unitário).
// Além disso, incorpora informações específicas de controle de inventário, como unidade de medida,
//categoria associada e limites de quantidade.
// A implementação de Serializable permite que instâncias de Produto sejam transmitidas via RMI ou armazenadas de forma persistente.
public class Produto extends ItemEstoque implements Serializable {

    private static final long serialVersionUID = 1L; // Versão da classe para controle de serialização.

    private String unidade; // Unidade de medida do produto (ex.: Kg, L, Unid).
    private Integer idProduto; // Identificador único do produto.
    private Categoria categoria; // Categoria à qual o produto pertence.
    private int quantidadeEstoque; // Quantidade atual disponível em estoque.
    private int quantidadeMinima; // Quantidade mínima recomendada para evitar falta.
    private int quantidadeMaxima; // Quantidade máxima permitida para evitar excesso.

    // Construtor padrão que inicializa o produto com valores default.
    public Produto() {
        this("", 0.0, "", new Categoria(), 0, 0, 0);
    }

    // Construtor utilizado para criação rápida de produtos com unidade e categoria.
    public Produto(String unidade, Categoria categoria) {
        this.unidade = unidade;
        this.categoria = categoria;
    }

    // Construtor completo da classe Produto.
    public Produto(String nome, double precoUnitario, String unidade, Categoria categoria, int quantidadeEstoque,
            int quantidadeMinima, int quantidadeMaxima) {
        super(nome, precoUnitario);
        this.unidade = unidade;
        this.categoria = categoria;
        this.quantidadeEstoque = quantidadeEstoque;
        this.quantidadeMinima = quantidadeMinima;
        this.quantidadeMaxima = quantidadeMaxima;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public int getQuantidadeMaxima() {
        return quantidadeMaxima;
    }

    public void setQuantidadeMaxima(int quantidadeMaxima) {
        this.quantidadeMaxima = quantidadeMaxima;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
}
