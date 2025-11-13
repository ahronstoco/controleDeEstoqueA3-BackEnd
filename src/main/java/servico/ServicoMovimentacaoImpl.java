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
    public void registrarMovimentacao(Movimentacao m) throws RemoteException {
        Produto p = produtoDAO.buscarPorId(m.getIdProduto());
        if (p == null) {
            throw new RemoteException("Produto não encontrado (ID: " + m.getIdProduto() + ")");
        }

        int novaQtd = p.getQuantidadeEstoque();

        if (m.getTipo().equalsIgnoreCase("ENTRADA")) {
            novaQtd += m.getQuantidade();
        } else if (m.getTipo().equalsIgnoreCase("SAIDA")) {
            novaQtd -= m.getQuantidade();
            if (novaQtd < 0) novaQtd = 0;
        }

        produtoDAO.atualizarEstoque(p.getIdProduto(), novaQtd);
        movimentacaoDAO.inserir(m);

        System.out.println("Movimentação registrada: " + m.getTipo() + " de " + m.getQuantidade()
                + " unid(s) do produto " + p.getNome());
    }

    @Override
    public List<Movimentacao> listarMovimentacoesPorProduto(int idProduto) throws RemoteException {
        return movimentacaoDAO.buscarPorProduto(idProduto);
    }
}