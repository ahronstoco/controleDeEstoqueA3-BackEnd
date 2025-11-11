package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.lang.String;
import dao.ProdutoDAO;
import dao.CategoriaDAO;
import dao.MovimentacaoDAO;
import modelo.*;

public class ServicoEstoqueImpl extends UnicastRemoteObject implements ServicoEstoque {

    private final ProdutoDAO produtoDAO;
    private final CategoriaDAO categoriaDAO;
    private final MovimentacaoDAO movimentacaoDAO;

    public ServicoEstoqueImpl() throws RemoteException {
        super();
        this.produtoDAO = new ProdutoDAO();
        this.categoriaDAO = new CategoriaDAO();
        this.movimentacaoDAO = new MovimentacaoDAO();
        System.out.println("Servico EstoqueServiceImpl instanciado com sucesso.");
    }

    @Override
    public void salvarProduto(Produto p) throws RemoteException {
        if (p.getIdProduto() == null) {
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
    public void salvarCategoria(Categoria c) throws RemoteException {
        if (c.getIdCategoria() == 0) {
            categoriaDAO.inserir(c);
            System.out.println("Categoria cadastrada: " + c.getNome());
        } else {
            categoriaDAO.alterar(c);
            System.out.println("Categoria atualizada: " + c.getNome());
        }
    }

    @Override
    public List<Categoria> listarCategorias() throws RemoteException {
        return categoriaDAO.listar();
    }

    @Override
    public Categoria buscarCategoriaPorId(int id) throws RemoteException {
        return categoriaDAO.buscarPorId(id);
    }

    @Override
    public void excluirCategoria(int id) throws RemoteException {
        categoriaDAO.apagar(id);
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
        movimentacaoDAO.inserir(m);

        System.out.println("Movimentacao registrada: " + m.getTipo() + " de " + m.getQuantidade()
                + " unid(s) do produto " + p.getNome());
    }

    @Override
    public List<Movimentacao> listarMovimentacoesPorProduto(int idProduto) throws RemoteException {
        return movimentacaoDAO.buscarPorProduto(idProduto);
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
            double valor = p.getPrecoUnitario();
            total += valor * p.getQuantidadeEstoque();
        }
        return total;
    }
}
