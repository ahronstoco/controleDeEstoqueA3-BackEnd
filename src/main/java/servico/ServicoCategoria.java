package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Categoria;

// Interface remota responsável pela definição dos serviços relacionados à entidade Categoria que podem ser acessados via RMI.
// Esta interface é utilizada pelo cliente para invocar operações no servidor,
//permitindo comunicação distribuída seguindo o padrão de tecnologia RMI (Remote Method Invocation).
// Todos os métodos lançam RemoteException}, pois operações remotas podem falhar devido a problemas..
public interface ServicoCategoria extends Remote {

    // Salva uma nova categoria.
    void salvarCategoria(Categoria c) throws RemoteException;

    // Retorna a lista de todas as categorias cadastradas.
    List<Categoria> listarCategorias() throws RemoteException;

    // Busca uma categoria com base no seu identificador.
    Categoria buscarCategoriaPorId(int id) throws RemoteException;

    // Exclui uma categoria do banco de dados com base no ID informado.
    void excluirCategoria(int id) throws RemoteException;
}
