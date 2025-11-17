package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Produto;

// Interface remota responsável pelos serviços relacionados à entidade Produto.
// Permite que clientes acessem e manipulem informações de produtos através de chamadas distribuídas utilizando RMI.
// Esta interface inclui operações de CRUD, consultas especializadas, relatórios e
// regras de negócio como atualização de estoque e aplicação de descontos.
// Todos os métodos declaram RemoteException devido às possibilidades de falha.
public interface ServicoProduto extends Remote {

    // Salva um produto no sistema.
    void salvarProduto(Produto p) throws RemoteException;

    // Retorna uma lista contendo todos os produtos cadastrados.
    List<Produto> listarProdutos() throws RemoteException;

    // Busca um produto pelo seu ID.
    Produto buscarProdutoPorId(int id) throws RemoteException;

    // Exclui um produto do sistema com base no seu ID.
    void excluirProduto(int id) throws RemoteException;

    // Lista produtos cujo estoque está abaixo da quantidade mínima definida.
    List<Produto> listarProdutosAbaixoDoMinimo() throws RemoteException;

    // Lista produtos cujo estoque está acima da quantidade máxima definida.
    List<Produto> listarProdutosAcimaDoMaximo() throws RemoteException;

    // Lista produtos pertencentes a uma categoria específica.
    List<Produto> listarProdutosPorCategoria(String nomeCategoria) throws RemoteException;

    // Calcula o valor total do estoque somando (preçoUnitário × quantidadeEstoque) de todos os produtos cadastrados.
    double calcularValorTotalEstoque() throws RemoteException;

    // Lista produtos exibindo apenas informações de nome, preço e unidade, utilizados no relatório de preços.
    List<Produto> listarPrecos() throws RemoteException;

    // Retorna dados para o relatório de balanço geral do estoque, com nome, quantidade e valor unitário.
    List<Produto> listarBalanco() throws RemoteException;

    // Lista produtos cujo estoque está abaixo da quantidade mínima definida (forma alternativa).
    List<Produto> listarFaltaProduto() throws RemoteException;

    // Lista produtos cujo estoque está acima da quantidade máxima definida (forma alternativa).
    List<Produto> listarExcessoProdutos() throws RemoteException;

    // Retorna um relatório com a quantidade de produtos agrupados por categoria.
    List<String[]> listarProdutosPorCategoria() throws RemoteException;

    // Aplica um desconto percentual ao preço unitário de um produto.
    public void aplicarDesconto(Integer idProduto, double percentual) throws RemoteException;

    // Atualiza a quantidade de estoque de um produto.
    public void atualizarEstoque(Integer idProduto, int novaQuantidade) throws RemoteException;

    // Busca produtos cujo nome corresponde ao texto informado.
    public List<Produto> buscarProdutoPorNome(String nome) throws RemoteException;
}
