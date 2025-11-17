package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Produto;
import modelo.Categoria;
import modelo.GerenciamentoEstoque;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

// Classe responsável pelo gerenciamento de operações relacionadas aos produtos na base de dados.
// A classe utiliza a infraestrutura fornecida por Conexao} para obter conexões JDBC.
// Também fornece métodos auxiliares utilizados nos relatórios e no gerenciamento de estoque do sistema.
public class ProdutoDAO {

    // Instância auxiliar para uso interno em operações específicas.
    GerenciamentoEstoque se = new GerenciamentoEstoque();

    // Insere um novo produto no banco de dados.
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, precoUnitario, unidade, quantidadeEstoque, quantidadeMinima, "
                + "quantidadeMaxima, categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setString(7, produto.getCategoria().getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto " + e.getMessage());
        }
    }

    // Atualiza os dados de um produto existente no banco.
    public void alterar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, precoUnitario = ?, unidade = ?, quantidadeEstoque = ?, quantidadeMinima = ?, "
                + "quantidadeMaxima = ?, idProduto = ?, categoria = ? WHERE idProduto = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoUnitario());
            stmt.setString(3, produto.getUnidade());
            stmt.setInt(4, produto.getQuantidadeEstoque());
            stmt.setInt(5, produto.getQuantidadeMinima());
            stmt.setInt(6, produto.getQuantidadeMaxima());
            stmt.setString(7, produto.getCategoria().getNome());
            stmt.setString(8, produto.getCategoria().getTamanho().name());
            stmt.setString(9, produto.getCategoria().getEmbalagem());
            stmt.setInt(10, se.getIdProduto());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao alterar produto " + e.getMessage());
        }
    }

    // Remove um produto do banco de dados com base no ID informado.
    public void apagar(Integer idProduto) {
        String sql = "DELETE FROM produto WHERE idProduto = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (idProduto != null) {
                stmt.setInt(1, idProduto);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover produto " + e.getMessage());
        }
    }

    // Retorna uma lista contendo todos os produtos cadastrados.
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("categoria"));
                produto.setCategoria(categoria);
                lista.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar os produtos: " + e.getMessage());
        }

        return lista;
    }

    // Lista produtos contendo apenas nome, preço e unidade.
    // Utilizado especialmente para geração de relatório de preços.
    public List<Produto> listarPrecos() {
        List<Produto> listarPrecos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                produto.setUnidade(rs.getString("unidade"));
                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("categoria"));
                produto.setCategoria(categoria);
                listarPrecos.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar os preços: " + e.getMessage());
        }
        return listarPrecos;
    }

    // Lista produtos com foco em balanço físico-financeiro.
    public List<Produto> listarBalanco() {
        List<Produto> listaBalanco = new ArrayList<>();
        String sql = "SELECT nome, precoUnitario, quantidadeEstoque FROM produto ORDER BY nome";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                listaBalanco.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar balanco dos produtos: " + e.getMessage());
        }

        return listaBalanco;
    }

    // Lista produtos cujo estoque está abaixo da quantidade mínima.
    public List<Produto> listarFaltaProduto() {
        List<Produto> listaFalta = new ArrayList<>();
        String sql = "SELECT nome, quantidadeMinima, quantidadeEstoque FROM produto WHERE quantidadeEstoque < quantidadeMinima";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setNome(rs.getString("nome"));
                produto.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                listaFalta.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar falta dos produtos: " + e.getMessage());
        }

        return listaFalta;
    }

    // Lista produtos cujo estoque excede a quantidade máxima permitida.
    public List<Produto> listarExcessoProdutos() {
        List<Produto> listaExcesso = new ArrayList<>();
        String sql = "SELECT nome, quantidadeMaxima, quantidadeEstoque FROM produto WHERE quantidadeEstoque > quantidadeMaxima";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setNome(rs.getString("nome"));
                produto.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                listaExcesso.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar excesso dos produtos: " + e.getMessage());
        }

        return listaExcesso;
    }

    // Retorna uma lista com pares categoria e total de produtos.
    public List<String[]> listarPorCategoria() {
        List<String[]> listaCategoria = new ArrayList<>();
        String sql = "SELECT categoria, COUNT(*) AS total FROM produto GROUP BY categoria";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] linha = new String[2];
                linha[0] = rs.getString("categoria");
                linha[1] = String.valueOf(rs.getInt("total"));
                listaCategoria.add(linha);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos por categoria: " + e.getMessage());
        }

        return listaCategoria;
    }

    // Lista todos os produtos pertencentes a uma categoria específica.
    public List<Produto> listarPorCategoria(String nomeCategoria) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE categoria = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, nomeCategoria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));

                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("categoria"));
                produto.setCategoria(categoria);

                lista.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos da categoria " + nomeCategoria + ": " + e.getMessage());
        }

        return lista;
    }

    // Busca um produto pelo ID.
    public Produto buscarPorId(Integer idProduto) {
        String sql = "SELECT * FROM produto WHERE idProduto = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (idProduto != null) {
                stmt.setInt(1, idProduto);
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("categoria"));
                produto.setCategoria(categoria);
                return produto;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
        }
        return null;
    }

    // Busca produtos cujo nome contém o texto informado.
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE nome LIKE ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoUnitario(rs.getDouble("precoUnitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidadeMinima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidadeMaxima"));
                Categoria categoria = new Categoria();
                categoria.setNome(rs.getString("categoria"));
                produto.setCategoria(categoria);
                lista.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produtos por nome: " + e.getMessage());
        }

        return lista;
    }

    // Atualiza a quantidade em estoque de um produto.
    public void atualizarEstoque(Integer idProduto, int novaQuantidade) {
        String sql = "UPDATE produto SET quantidadeEstoque = ? WHERE idProduto = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            if (idProduto != null) {
                stmt.setInt(2, idProduto);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    // Aplica desconto no preço de um produto.
    // O novo preço é calculado multiplicando o valor atual pelo fator (1 - percentual).
    public void aplicarDesconto(Integer idProduto, double percentual) {
        String sql = "UPDATE produto SET precoUnitario = precoUnitario * ? WHERE idProduto = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, 1 - percentual);
            if (idProduto != null) {
                stmt.setInt(2, idProduto);
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao aplicar desconto: " + e.getMessage());
        }
    }
}
