package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Categoria;
import modelo.Tamanho;
import java.util.ArrayList;
import java.util.List;
import util.Conexao;

// Classe responsável pelo acesso e manipulação dos dados da tabela categoria no banco MySQL.
// A CategoriaDAO implementa as operações CRUD utilizando JDBC.
// Cada método estabelece sua própria conexão através da classe Conexao,
//onde executa o comando SQL necessário e trata as exceções relacionadas a banco de dados.
// As operações realizadas incluem:
// Inserção de novas categorias;
// Exclusão pelo ID;
// Listagem completa das categorias;
// Busca de uma categoria específica pelo ID.
// É utilizada a classe Tamanho} para conversão segura do campo ENUM armazenado no banco de dados.
public class CategoriaDAO {

    // Insere uma nova categoria no banco de dados. 
    // Após a inserção, o ID gerado automaticamente é atribuído ao objeto Categoria recebido como parâmetro.
    public void inserir(Categoria categoria) {
        String sql = "INSERT INTO categoria(nome, tamanho, embalagem) VALUES (?, ?, ?)";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho().name());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGerado = rs.getInt(1);
                    categoria.setIdCategoria(idGerado);
                    System.out.println("ID gerado: " + idGerado);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir categoria " + e.getMessage());
        }
    }

    // Atualiza os dados de uma categoria existente.
    public void alterar(Categoria categoria) {
        String sql = "UPDATE categoria SET nome = ?, tamanho = ?, embalagem = ? WHERE idCategoria = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getTamanho().name());
            stmt.setString(3, categoria.getEmbalagem());
            stmt.setInt(4, categoria.getIdCategoria());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao alterar categoria " + e.getMessage());
        }
    }

    // Remove uma categoria do banco de dados com base no seu ID.
    public void apagar(int idCategoria) {
        String sql = "DELETE FROM categoria WHERE idCategoria = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao remover categoria " + e.getMessage());
        }
    }

    // Lista todas as categorias cadastradas no banco de dados.
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                categoria.setTamanho(Tamanho.valueOf(rs.getString("tamanho")));
                categoria.setEmbalagem(rs.getString("embalagem"));
                lista.add(categoria);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
        }

        return lista;
    }

    // Busca uma categoria específica pelo ID.
    public Categoria buscarPorId(int idCategoria) {
        String sql = "SELECT * FROM categoria WHERE idCategoria = ?";

        Conexao factory = new Conexao();
        try (Connection conexao = factory.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("idCategoria"));
                    categoria.setNome(rs.getString("nome"));
                    String tamanhoStr = rs.getString("tamanho");
                    if (tamanhoStr != null && !tamanhoStr.isEmpty()) {
                        categoria.setTamanho(Tamanho.valueOf(tamanhoStr));
                    }
                    categoria.setEmbalagem(rs.getString("embalagem"));
                    return categoria;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar categoria por ID: " + e.getMessage());
        }

        return null;
    }
}
