package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.ProdutoDAO;
import modelo.Produto;

public class ServicoProdutoImpl extends UnicastRemoteObject implements ServicoProduto {

    private final ProdutoDAO produtoDAO;

    public ServicoProdutoImpl() throws RemoteException {
        super();
        this.produtoDAO = new ProdutoDAO();
        System.out.println("ServicoProdutoImpl iniciado com sucesso.");
    }

    @Override
    public void salvarProduto(Produto p) throws RemoteException {
        if (p.getIdProduto() == null || p.getIdProduto() == 0) {
            produtoDAO.inserir(p);
            System.out.println("Produto cadastrado: " + p.getNome());
        } else {
            produtoDAO.alterar(p);
            System.out.println("Produto atualizado: " + p.getNome());
        }
    }

    @Override
    public List<Produto> listarProdutos() throws RemoteException {
        return produtoDAO.listar();
    }

    @Override
    public Produto buscarProdutoPorId(int id) throws RemoteException {
        return produtoDAO.buscarPorId(id);
    }

    @Override
    public void excluirProduto(int id) throws RemoteException {
        produtoDAO.apagar(id);
        System.out.println("Produto removido (ID: " + id + ")");
    }

    @Override
    public List<Produto> listarProdutosAbaixoDoMinimo() throws RemoteException {
        return produtoDAO.listarFaltaProduto();
    }

    @Override
    public List<Produto> listarProdutosAcimaDoMaximo() throws RemoteException {
        return produtoDAO.listarExcessoProdutos();
    }

    @Override
    public List<Produto> listarProdutosPorCategoria(String nomeCategoria) throws RemoteException {
        return produtoDAO.listarPorCategoria(nomeCategoria);
    }

    @Override
    public double calcularValorTotalEstoque() throws RemoteException {
        List<Produto> produtos = produtoDAO.listar();
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPrecoUnitario() * p.getQuantidadeEstoque();
        }
        return total;
    }

    @Override
    public List<Produto> listarPrecos() throws RemoteException {
        return produtoDAO.listarPrecos();
    }

    @Override
    public List<Produto> listarBalanco() throws RemoteException {
        return produtoDAO.listarBalanco();
    }

    @Override
    public List<Produto> listarFaltaProduto() throws RemoteException {
        return produtoDAO.listarFaltaProduto();
    }

    @Override
    public List<Produto> listarExcessoProdutos() throws RemoteException {
        return produtoDAO.listarExcessoProdutos();
    }

    @Override
    public List<String[]> listarProdutosPorCategoria() throws RemoteException {
        return produtoDAO.listarPorCategoria();
    }

    @Override
    public void aplicarDesconto(Integer idProduto, double percentual) throws RemoteException {
        produtoDAO.aplicarDesconto(idProduto, percentual);
    }

    @Override
    public void atualizarEstoque(Integer idProduto, int novaQuantidade) throws RemoteException {
        produtoDAO.atualizarEstoque(idProduto, novaQuantidade);
    }

    @Override
    public List<Produto> buscarProdutoPorNome(String nome) throws RemoteException {
        return produtoDAO.buscarPorNome(nome);
    }
}

