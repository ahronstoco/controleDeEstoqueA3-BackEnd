package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Movimentacao;
import util.Conexao;
import javax.swing.JOptionPane;

// Classe responsável pela manipulação dos dados da tabela movimentacao no banco de dados.
// A MovimentacaoDAO executa operações de inserção, listagem, exclusão e consulta filtrada por produto.
// Cada operação utiliza conexões fornecidas pela classe Conexao.
// Uma movimentação representa uma alteração ocorrida no estoque, podendo ser de entrada ou saída,
//contendo data, quantidade e observação.
// A classe também possui um método privado atualizarEstoque, responsável por recalcular o
//estoque do produto após uma movimentação.
public class MovimentacaoDAO {

    // Insere uma nova movimentação na tabela movimentacao.
    // A data da movimentação é definida automaticamente pelo MySQL através da função CURDATE().
    public void inserirMovimentacao(int idProduto, String tipo, int quantidade, String observacao) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = new Conexao().getConnection();
            stmt = con.prepareStatement(
                    "INSERT INTO movimentacao (idProduto, tipo, quantidade, data, observacao) VALUES (?, ?, ?, CURDATE(), ?)"
            );

            stmt.setInt(1, idProduto);
            stmt.setString(2, tipo);
            stmt.setInt(3, quantidade);
            stmt.setString(4, observacao);

            stmt.executeUpdate();

            System.out.println("Movimentacao registrada.");

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir movimentacao: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar conexao: " + ex.getMessage());
            }
        }
    }

    // Lista todas as movimentações cadastradas no banco de dados.
    public List<Movimentacao> listar() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
            con = new Conexao().getConnection();
            stmt = con.prepareStatement("SELECT * FROM movimentacao ORDER BY data DESC");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Movimentacao mov = new Movimentacao();
                mov.setIdMovimentacao(rs.getInt("idMovimentacao"));
                mov.setIdProduto(rs.getInt("idProduto"));
                mov.setTipo(rs.getString("tipo"));
                mov.setQuantidade(rs.getInt("quantidade"));
                mov.setData(rs.getDate("data"));
                mov.setObservacao(rs.getString("observacao"));

                movimentacoes.add(mov);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao listar movimentacoes: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexao: " + ex.getMessage());
            }
        }
        return movimentacoes;
    }

    // Busca todas as movimentações associadas a um produto específico.
    public List<Movimentacao> buscarPorProduto(int idProduto) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
            con = new Conexao().getConnection();
            stmt = con.prepareStatement("SELECT * FROM movimentacao WHERE idProduto = ? ORDER BY data DESC");
            stmt.setInt(1, idProduto);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Movimentacao mov = new Movimentacao();
                mov.setIdMovimentacao(rs.getInt("idMovimentacao"));
                mov.setIdProduto(rs.getInt("idProduto"));
                mov.setTipo(rs.getString("tipo"));
                mov.setQuantidade(rs.getInt("quantidade"));
                mov.setData(rs.getDate("data"));
                mov.setObservacao(rs.getString("observacao"));

                movimentacoes.add(mov);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar movimentacoes: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexao: " + ex.getMessage());
            }
        }

        return movimentacoes;
    }

    // Exclui uma movimentação da tabela movimentacao.
    public void excluir(int idMovimentacao) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = new Conexao().getConnection();
            stmt = con.prepareStatement("DELETE FROM movimentacao WHERE idMovimentacao = ?");
            stmt.setInt(1, idMovimentacao);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimentacao excluida.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir movimentacao: " + ex.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar conexao: " + ex.getMessage());
            }
        }
    }

    // Atualiza o estoque de um produto com base em uma movimentação.
    // Se a movimentação for do tipo entrada, o estoque aumenta.
    // Se for do tipo saída, o estoque diminui, sem permitir valores negativos.
    private void atualizarEstoque(Movimentacao mov) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = new Conexao().getConnection();

            stmt = con.prepareStatement("SELECT estoque FROM produto WHERE idProduto = ?");
            stmt.setInt(1, mov.getIdProduto());
            rs = stmt.executeQuery();

            if (rs.next()) {
                int estoqueAtual = rs.getInt("estoque");
                int novoEstoque;

                if (mov.getTipo().equalsIgnoreCase("entrada")) {
                    novoEstoque = estoqueAtual + mov.getQuantidade();
                } else if (mov.getTipo().equalsIgnoreCase("saida")) {
                    novoEstoque = Math.max(estoqueAtual - mov.getQuantidade(), 0);
                } else {
                    throw new SQLException("Tipo de movimentacao invalido: " + mov.getTipo());
                }

                stmt.close();
                stmt = con.prepareStatement("UPDATE produto SET estoque = ? WHERE idProduto = ?");
                stmt.setInt(1, novoEstoque);
                stmt.setInt(2, mov.getIdProduto());
                stmt.executeUpdate();

            } else {
                throw new SQLException("Produto com ID " + mov.getIdProduto() + " nao encontrado.");
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
