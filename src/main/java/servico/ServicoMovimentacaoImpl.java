package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
import modelo.Produto;

// Implementação da interface ServicoMovimentacao, responsável por disponibilizar via
// RMI os serviços de registro e consulta de movimentações de estoque.
// Esta classe representa a camada de serviço do back-end e funciona como intermediária entre
//o cliente RMI e a camada de persistência MovimentacaoDAO e ProdutoDAO.
// A classe permite registrar entradas e saídas de produtos e consultar
//todas as movimentações realizadas no sistema.
// Por estender UnicastRemoteObject, o objeto é exportado para uso remoto e registrado no RMI Registry.
public class ServicoMovimentacaoImpl extends UnicastRemoteObject implements ServicoMovimentacao {

    // DAO responsável por operações de persistência de movimentações.
    private final MovimentacaoDAO movimentacaoDAO;
    // DAO responsável por operações de persistência relacionadas a produtos.
    private final ProdutoDAO produtoDAO;

    // Construtor padrão. Inicializa os DAOs utilizados nas operações de
    //movimentação e exibe uma mensagem indicando que o serviço está ativo.
    public ServicoMovimentacaoImpl() throws RemoteException {
        super();
        this.movimentacaoDAO = new MovimentacaoDAO();
        this.produtoDAO = new ProdutoDAO();
        System.out.println("ServicoMovimentacaoImpl iniciado.");
    }

    // Registra uma movimentação de estoque (entrada ou saída) no sistema.
    @Override
    public void registrarMovimentacao(int idProduto, String tipo, int quantidade, String observacao) throws RemoteException {
        try {
            MovimentacaoDAO dao = new MovimentacaoDAO();
            dao.inserirMovimentacao(idProduto, tipo, quantidade, observacao);
        } catch (Exception e) {
            throw new RemoteException("Erro ao registrar movimentação", e);
        }
    }

    // Retorna uma lista contendo todas as movimentações já registradas no sistema.
    @Override
    public List<Movimentacao> listarTodasMovimentacoes() throws RemoteException {
        return movimentacaoDAO.listar();
    }
}
