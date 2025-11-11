package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Produto;
import modelo.Categoria;
import modelo.Movimentacao;

public interface ServicoEstoque extends Remote {

    void salvarProduto(Produto p) throws RemoteException;

    List<Produto> listarProdutos() throws RemoteException;

    Produto buscarProdutoPorId(int id) throws RemoteException;

    void excluirProduto(int id) throws RemoteException;

    void salvarCategoria(Categoria c) throws RemoteException;

    List<Categoria> listarCategorias() throws RemoteException;

    Categoria buscarCategoriaPorId(int id) throws RemoteException;

    void excluirCategoria(int id) throws RemoteException;

    void registrarMovimentacao(Movimentacao m) throws RemoteException;

    List<Movimentacao> listarMovimentacoesPorProduto(int idProduto) throws RemoteException;

    List<Produto> listarProdutosAbaixoDoMinimo() throws RemoteException;

    List<Produto> listarProdutosAcimaDoMaximo() throws RemoteException;

    List<Produto> listarProdutosPorCategoria(String nomeCategoria) throws RemoteException;

    double calcularValorTotalEstoque() throws RemoteException;
}
