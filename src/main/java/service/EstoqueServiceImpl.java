package Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.math.BigDecimal;

import dao.ProdutoDAO;
import dao.CategoriaDAO;
import dao.MovimentacaoDAO;
import modelo.*;

public class EstoqueServiceImpl extends UnicastRemoteObject implements EstoqueService {

    private final ProdutoDAO produtoDAO;
    private final CategoriaDAO categoriaDAO;
    private final MovimentacaoDAO movimentacaoDAO;

    public EstoqueServiceImpl() throws RemoteException {
        super();
        this.produtoDAO = new ProdutoDAO();
        this.categoriaDAO = new CategoriaDAO();
        this.movimentacaoDAO = new MovimentacaoDAO();
        System.out.println("Serviço EstoqueServiceImpl instanciado com sucesso.");
    }

    @Override
    public void salvarProduto(Produto p) throws RemoteException {
        if (p.getIdProduto() == null) {
            produtoDAO.salvar(p);
            System.out.println("Produto cadastrado: " + p.getNome());
        } else {
            produtoDAO.atualizar(p);
            System.out.println("Produto atualizado: " + p.getNome());
        }
    }

    @Override
    public List<Produto> listarProdutos() throws RemoteException {
        return produtoDAO.listarTodos();
    }

    @Override
    public Produto buscarProdutoPorId(int id) throws RemoteException {
        return produtoDAO.buscarPorId(id);
    }

    @Override
    public void excluirProduto(int id) throws RemoteException {
        produtoDAO.excluir(id);
        System.out.println("Produto removido (ID: " + id + ")");
    }

    @Override
    public void salvarCategoria(Categoria c) throws RemoteException {
        if (c.getIdCategoria() == null) {
            categoriaDAO.salvar(c);
            System.out.println("Categoria cadastrada: " + c.getNome());
        } else {
            categoriaDAO.atualizar(c);
            System.out.println("Categoria atualizada: " + c.getNome());
        }
    }

    @Override
    public List<Categoria> listarCategorias() throws RemoteException {
        return categoriaDAO.listarTodos();
    }

    @Override
    public Categoria buscarCategoriaPorId(int id) throws RemoteException {
        return categoriaDAO.buscarPorId(id);
    }

    @Override
    public void excluirCategoria(int id) throws RemoteException {
        categoriaDAO.excluir(id);
        System.out.println("Categoria removida (ID: " + id + ")");
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
            if (novaQtd < 0) {
                novaQtd = 0;
            }
        }

        produtoDAO.atualizarEstoque(p.getIdProduto(), novaQtd);
        movimentacaoDAO.registrar(m);

        System.out.println("Movimentação registrada: " + m.getTipo() + " de " + m.getQuantidade()
                + " unid(s) do produto " + p.getNome());
    }

    @Override
    public List<Movimentacao> listarMovimentacoesPorProduto(int idProduto) throws RemoteException {
        return movimentacaoDAO.listarPorProduto(idProduto);
    }

    @Override
    public List<Produto> listarProdutosAbaixoDoMinimo() throws RemoteException {
        return produtoDAO.listarAbaixoDoMinimo();
    }

    @Override
    public List<Produto> listarProdutosAcimaDoMaximo() throws RemoteException {
        return produtoDAO.listarAcimaDoMaximo();
    }

    @Override
    public List<Produto> listarProdutosPorCategoria(String nomeCategoria) throws RemoteException {
        return produtoDAO.listarPorCategoria(nomeCategoria);
    }

    @Override
    public double calcularValorTotalEstoque() throws RemoteException {
        List<Produto> produtos = produtoDAO.listarTodos();
        double total = 0;
        for (Produto p : produtos) {
            BigDecimal valor = p.getPrecoUnitario();
            total += valor.doubleValue() * p.getQuantidadeEstoque();
        }
        return total;
    }
}
