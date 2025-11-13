package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Movimentacao;

public interface ServicoMovimentacao extends Remote {

    void registrarMovimentacao(Movimentacao m) throws RemoteException;

    List<Movimentacao> listarMovimentacoesPorProduto(int idProduto) throws RemoteException;
}

