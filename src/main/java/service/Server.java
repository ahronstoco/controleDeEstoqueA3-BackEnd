package Service;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1099);
            System.out.println("Registro RMI criado na porta 1099.");

            EstoqueServiceImpl service = new EstoqueServiceImpl();
            Naming.rebind("rmi://localhost:1099/EstoqueService", service);

            System.out.println("Servidor RMI  ativo.");
            System.out.println("Serviço disponível.");

        } catch (Exception e) {
            System.err.println("Erro ao iniciar o servidor RMI:");
            e.printStackTrace();
        }
    }
}
