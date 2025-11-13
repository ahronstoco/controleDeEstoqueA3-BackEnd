package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Categoria;

public interface ServicoCategoria extends Remote {

    void salvarCategoria(Categoria c) throws RemoteException;

    List<Categoria> listarCategorias() throws RemoteException;

    Categoria buscarCategoriaPorId(int id) throws RemoteException;

    void excluirCategoria(int id) throws RemoteException;
}