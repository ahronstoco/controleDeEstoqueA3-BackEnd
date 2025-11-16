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

    List<Produto> listarPrecos() throws RemoteException;

    List<Produto> listarBalanco() throws RemoteException;

    List<Produto> listarFaltaProduto() throws RemoteException;

    List<Produto> listarExcessoProdutos() throws RemoteException;

    List<String[]> listarProdutosPorCategoria() throws RemoteException;

    public void aplicarDesconto(Integer idProduto, double percentual) throws RemoteException;

    public void atualizarEstoque(Integer idProduto, int novaQuantidade) throws RemoteException;

    public List<Produto> buscarProdutoPorNome(String nome) throws RemoteException;
}
