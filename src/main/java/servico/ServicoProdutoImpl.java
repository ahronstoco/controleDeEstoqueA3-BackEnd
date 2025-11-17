package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.ProdutoDAO;
import modelo.Produto;

// Implementação da interface ServicoProduto, responsável por expor via
//RMI os serviços relacionados ao gerenciamento de produtos no sistema.
// Esta classe atua como intermediária entre o cliente RMI e a camada de persistência ProdutoDAO.
// Todas as operações feitas pelo cliente são validadas e repassadas ao DAO para registro no banco de dados.
// Por ser uma classe remota, estende UnicastRemoteObject, tornando sua instância acessível via RMI Registry.
public class ServicoProdutoImpl extends UnicastRemoteObject implements ServicoProduto {

    // DAO responsável pelas operações de persistência de produtos.
    private final ProdutoDAO produtoDAO;

    // Construtor padrão que inicializa o DAO e exporta o objeto para acesso RMI.
    public ServicoProdutoImpl() throws RemoteException {
        super();
        this.produtoDAO = new ProdutoDAO();
        System.out.println("ServicoProdutoImpl iniciado.");
    }

    // Salva um produto no banco de dados.
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

    // Retorna uma lista contendo todos os produtos cadastrados.
    @Override
    public List<Produto> listarProdutos() throws RemoteException {
        return produtoDAO.listar();
    }

    // Busca um produto no banco pelo seu identificador.
    @Override
    public Produto buscarProdutoPorId(int id) throws RemoteException {
        return produtoDAO.buscarPorId(id);
    }

    // Exclui um produto do sistema com base no seu ID.
    @Override
    public void excluirProduto(int id) throws RemoteException {
        produtoDAO.apagar(id);
        System.out.println("Produto removido (ID: " + id + ")");
    }

    // Retorna uma lista de produtos cujo estoque está abaixo da quantidade mínima.
    @Override
    public List<Produto> listarProdutosAbaixoDoMinimo() throws RemoteException {
        return produtoDAO.listarFaltaProduto();
    }

    // Retorna uma lista de produtos cujo estoque ultrapassou a quantidade máxima.
    @Override
    public List<Produto> listarProdutosAcimaDoMaximo() throws RemoteException {
        return produtoDAO.listarExcessoProdutos();
    }

    // Lista produtos pertencentes a uma categoria específica.
    @Override
    public List<Produto> listarProdutosPorCategoria(String nomeCategoria) throws RemoteException {
        return produtoDAO.listarPorCategoria(nomeCategoria);
    }

    // Calcula o valor total do estoque, multiplicando preço unitário pela quantidade disponível de cada produto.
    @Override
    public double calcularValorTotalEstoque() throws RemoteException {
        List<Produto> produtos = produtoDAO.listar();
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPrecoUnitario() * p.getQuantidadeEstoque();
        }
        return total;
    }

    // Lista todos os produtos com seus respectivos preços.
    @Override
    public List<Produto> listarPrecos() throws RemoteException {
        return produtoDAO.listarPrecos();
    }

    // Lista informações relacionadas ao balanço físico-financeiro.
    @Override
    public List<Produto> listarBalanco() throws RemoteException {
        return produtoDAO.listarBalanco();
    }

    // Retorna uma lista de produtos cujo estoque está abaixo da quantidade mínima (forma alternativa).
    @Override
    public List<Produto> listarFaltaProduto() throws RemoteException {
        return produtoDAO.listarFaltaProduto();
    }

    // Retorna uma lista de produtos cujo estoque ultrapassou a quantidade máxima(forma alternativa).
    @Override
    public List<Produto> listarExcessoProdutos() throws RemoteException {
        return produtoDAO.listarExcessoProdutos();
    }

    // Retorna um relatório de quantidade de produtos por categoria.
    @Override
    public List<String[]> listarProdutosPorCategoria() throws RemoteException {
        return produtoDAO.listarPorCategoria();
    }

    // Aplica um desconto percentual ao preço de um produto.
    @Override
    public void aplicarDesconto(Integer idProduto, double percentual) throws RemoteException {
        produtoDAO.aplicarDesconto(idProduto, percentual);
    }

    // Atualiza a quantidade de estoque de um produto.
    @Override
    public void atualizarEstoque(Integer idProduto, int novaQuantidade) throws RemoteException {
        produtoDAO.atualizarEstoque(idProduto, novaQuantidade);
    }

    // Busca produtos cujo nome corresponda total ou parcialmente ao termo informado.
    @Override
    public List<Produto> buscarProdutoPorNome(String nome) throws RemoteException {
        return produtoDAO.buscarPorNome(nome);
    }
}
