package principal;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import servico.ServicoCategoriaImpl;
import servico.ServicoMovimentacaoImpl;
import servico.ServicoProdutoImpl;

public class Principal {

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1099);
            System.out.println("Registro RMI criado na porta 1099.");

            ServicoProdutoImpl servicoProduto = new ServicoProdutoImpl();
            ServicoCategoriaImpl servicoCategoria = new ServicoCategoriaImpl();
            ServicoMovimentacaoImpl servicoMovimentacao = new ServicoMovimentacaoImpl();

            Naming.rebind("rmi://localhost:1099/ProdutoService", servicoProduto);
            Naming.rebind("rmi://localhost:1099/CategoriaService", servicoCategoria);
            Naming.rebind("rmi://localhost:1099/MovimentacaoService", servicoMovimentacao);

            System.out.println("Servidor RMI  ativo.");
            System.out.println("Servicos disponiveis.");

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor RMI:");
            e.printStackTrace();
        }
    }
}
