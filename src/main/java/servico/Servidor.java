package servico;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servidor {

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1099);
            System.out.println("Registro RMI criado na porta 1099.");

            ServicoEstoqueImpl service = new ServicoEstoqueImpl();
            Naming.rebind("rmi://localhost:1099/EstoqueService", service);

            System.out.println("Servidor RMI  ativo.");
            System.out.println("Servico disponivel.");

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor RMI:");
            e.printStackTrace();
        }
    }
}
