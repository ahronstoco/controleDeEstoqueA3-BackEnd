package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.CategoriaDAO;
import modelo.Categoria;

// Implementação da interface ServicoCategoria, responsável por disponibilizar
//serviços remotos para manipulação de categorias utilizando Java RMI.
// Esta classe atua como a camada de serviço do back-end, servindo de ponte
//entre o cliente e a camada de persistência CategoriaDAO.
// Todas as operações CRUD relacionadas à entidade Categoria são expostas como métodos remotos.
// Por estender UnicastRemoteObject, esta classe pode ser registrada no RMI Registry e acessada remotamente pelos clientes.
public class ServicoCategoriaImpl extends UnicastRemoteObject implements ServicoCategoria {

    // DAO responsável por operações de persistência da entidade Categoria.
    private final CategoriaDAO categoriaDAO;

    // Construtor padrão. Inicializa o DAO e imprime mensagem indicando que o serviço foi iniciado.
    public ServicoCategoriaImpl() throws RemoteException {
        super();
        this.categoriaDAO = new CategoriaDAO();
        System.out.println("ServicoCategoriaImpl iniciado.");
    }

    // Salva uma categoria no sistema.
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

    // Retorna uma lista contendo todas as categorias cadastradas no banco.
    @Override
    public List<Categoria> listarCategorias() throws RemoteException {
        return categoriaDAO.listar();
    }

    // Busca uma categoria pelo seu ID.
    @Override
    public Categoria buscarCategoriaPorId(int id) throws RemoteException {
        return categoriaDAO.buscarPorId(id);
    }

    // Exclui uma categoria do sistema com base no seu ID.
    @Override
    public void excluirCategoria(int id) throws RemoteException {
        categoriaDAO.apagar(id);
        System.out.println("Categoria removida (ID: " + id + ")");
    }
}
