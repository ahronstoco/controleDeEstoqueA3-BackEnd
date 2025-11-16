package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Movimentacao;

public interface ServicoMovimentacao extends Remote {

    void registrarMovimentacao(int idProduto, String tipo, int quantidade, String observacao) throws RemoteException;

    List<Movimentacao> listarTodasMovimentacoes() throws RemoteException;
}
