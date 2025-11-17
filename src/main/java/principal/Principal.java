package principal;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import servico.ServicoCategoriaImpl;
import servico.ServicoMovimentacaoImpl;
import servico.ServicoProdutoImpl;

// Classe principal responsável por iniciar o servidor RMI da aplicação.
// Este componente representa o ponto de entrada do back-end. Ele executa as seguintes operações essenciais:
// Inicializa o registro RMI na porta 1099;
// Instancia as implementações dos serviços remotos (Produto, Categoria e Movimentação;
// Realiza o binding dessas instâncias no registro, tornando-as acessíveis ao cliente;
// Exibe mensagens de status no console para auxiliar no monitoramento e depuração.
// Após sua execução bem-sucedida, o back-end estará apto a receber chamadas remotas provenientes do front-end através dos serviços.
public class Principal {

// Método principal responsável por inicializar o servidor RMI.
    public static void main(String[] args) {
        try {

            // Inicializa o registro RMI na porta 1099.
            LocateRegistry.createRegistry(1099);
            System.out.println("Registro RMI criado na porta 1099.");

            // Instancia os serviços remotos.
            ServicoProdutoImpl servicoProduto = new ServicoProdutoImpl();
            ServicoCategoriaImpl servicoCategoria = new ServicoCategoriaImpl();
            ServicoMovimentacaoImpl servicoMovimentacao = new ServicoMovimentacaoImpl();

            // Publica os serviços no registro RMI.
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
