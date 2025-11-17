package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Classe utilitária responsável pelo gerenciamento de conexões com o banco de dados MySQL.
// Esta classe centraliza a lógica de conexão, criação e encerramento de recursos JDBC utilizados pelos DAOs da aplicação.
// Seu uso garante padronização, simplificação do código de acesso a dados e redução de duplicidade.
// Os métodos fornecidos permitem:
// Obter uma conexão ativa com o banco;
// Fechar recursos JDBC de forma segura (Connection, PreparedStatement e ResultSet);
// Tratar erros comuns de banco de dados de maneira consistente.
public class Conexao {

    // URL de conexão JDBC com o banco de dados MySQL utilizado pela aplicação.
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/ControleEstoqueA3";
    // Usuário da base de dados.
    private static final String USUARIO = "a3prog";
    // Senha do usuário da base de dados.
    private static final String SENHA = "unisul@prog3";

    // Estabelece e retorna uma conexão ativa com o banco de dados.
    // Este método carrega o driver JDBC e utiliza o DriverManager para criar uma nova conexão com o MySQL.
    // Caso algum erro ocorra uma RuntimeException é lançada com a causa original.
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }

    // Fecha uma conexão com o banco de dados, caso não seja nula.
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    // Fecha uma conexão e um PreparedStatement, caso existam.
    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar PreparedStatement: " + e.getMessage());
            }
        }
    }

    // Fecha uma conexão, um PreparedStatement e um ResultSet.
    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
            }
        }
    }
}
