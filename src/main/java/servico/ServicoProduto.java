package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Produto;

public interface ServicoProduto extends Remote {

    void salvarProduto(Produto p) throws RemoteException;

    List<Produto> listarProdutos() throws RemoteException;

    Produto buscarProdutoPorId(int id) throws RemoteException;

    void excluirProduto(int id) throws RemoteException;

    List<Produto> listarProdutosAbaixoDoMinimo() throws RemoteException;

    List<Produto> listarProdutosAcimaDoMaximo() throws RemoteException;

    List<Produto> listarProdutosPorCategoria(String nomeCategoria) throws RemoteException;

    double calcularValorTotalEstoque() throws RemoteException;
}