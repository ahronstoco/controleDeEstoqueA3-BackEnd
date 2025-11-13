package servico;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import dao.CategoriaDAO;
import modelo.Categoria;

public class ServicoCategoriaImpl extends UnicastRemoteObject implements ServicoCategoria {

    private final CategoriaDAO categoriaDAO;

    public ServicoCategoriaImpl() throws RemoteException {
        super();
        this.categoriaDAO = new CategoriaDAO();
        System.out.println("ServicoCategoriaImpl iniciado com sucesso.");
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
}
