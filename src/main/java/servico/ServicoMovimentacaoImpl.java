package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import modelo.Movimentacao;
import modelo.Produto;

public class ServicoMovimentacaoImpl extends UnicastRemoteObject implements ServicoMovimentacao {

    private final MovimentacaoDAO movimentacaoDAO;
    private final ProdutoDAO produtoDAO;

    public ServicoMovimentacaoImpl() throws RemoteException {
        super();
        this.movimentacaoDAO = new MovimentacaoDAO();
        this.produtoDAO = new ProdutoDAO();
        System.out.println("ServicoMovimentacaoImpl iniciado com sucesso.");
    }

    @Override
    public void registrarMovimentacao(int idProduto, String tipo, int quantidade, String observacao) throws RemoteException {
        try {
            MovimentacaoDAO dao = new MovimentacaoDAO();
            dao.inserirMovimentacao(idProduto, tipo, quantidade, observacao);
        } catch (Exception e) {
            throw new RemoteException("Erro ao registrar movimentação", e);
        }
    }

    @Override
    public List<Movimentacao> listarTodasMovimentacoes() throws RemoteException {
        return movimentacaoDAO.listar();
    }
}
