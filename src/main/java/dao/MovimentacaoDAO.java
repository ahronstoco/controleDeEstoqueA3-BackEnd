package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Movimentacao;
import util.ConnectionFactory;
import javax.swing.JOptionPane;

public class MovimentacaoDAO {

    public void inserir(Movimentacao mov) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(
                    "INSERT INTO movimentacao (idProduto, tipo, quantidade, data, observacao) VALUES (?, ?, ?, ?, ?)"
            );

            stmt.setInt(1, mov.getIdProduto());
            stmt.setString(2, mov.getTipo());
            stmt.setInt(3, mov.getQuantidade());
            stmt.setDate(4, new java.sql.Date(mov.getData().getTime()));
            stmt.setString(5, mov.getObservacao());

            stmt.executeUpdate();

            atualizarEstoque(mov);

            JOptionPane.showMessageDialog(null, "Movimentacao registrada.");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir movimentacao: " + ex.getMessage());
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

    public List<Movimentacao> listar() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
            con = new ConnectionFactory().getConnection();
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

    public List<Movimentacao> buscarPorProduto(int idProduto) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
            con = new ConnectionFactory().getConnection();
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

    public void excluir(int idMovimentacao) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = new ConnectionFactory().getConnection();
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

    private void atualizarEstoque(Movimentacao mov) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = new ConnectionFactory().getConnection();

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
