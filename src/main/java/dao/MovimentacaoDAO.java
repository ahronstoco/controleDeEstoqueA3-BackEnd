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

    // Método para registrar uma nova movimentação
    public void inserir(Movimentacao mov) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            // Inserir a movimentação
            stmt = con.prepareStatement(
                    "INSERT INTO movimentacao (idProduto, tipo, quantidade, data, observacao) VALUES (?, ?, ?, ?, ?)"
            );

            stmt.setInt(1, mov.getIdProduto());
            stmt.setString(2, mov.getTipo());
            stmt.setInt(3, mov.getQuantidade());
            stmt.setDate(4, new java.sql.Date(mov.getData().getTime()));
            stmt.setString(5, mov.getObservacao());

            stmt.executeUpdate();

            // Atualiza o estoque do produto de acordo com o tipo da movimentação
            atualizarEstoque(mov);

            JOptionPane.showMessageDialog(null, "Movimentação registrada com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir movimentação: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Movimentacao> listar() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
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
            JOptionPane.showMessageDialog(null, "Erro ao listar movimentações: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return movimentacoes;
    }

    // Método para buscar movimentações de um produto específico
    public List<Movimentacao> buscarPorProduto(int idProduto) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();

        try {
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
            JOptionPane.showMessageDialog(null, "Erro ao buscar movimentações: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return movimentacoes;
    }

    // Método para excluir uma movimentação (opcional)
    public void excluir(int idMovimentacao) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM movimentacao WHERE idMovimentacao = ?");
            stmt.setInt(1, idMovimentacao);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Movimentação excluída com sucesso!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir movimentação: " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
