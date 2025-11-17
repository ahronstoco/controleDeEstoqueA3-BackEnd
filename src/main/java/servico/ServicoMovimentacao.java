package servico;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import modelo.Movimentacao;

// Interface remota responsável pelos serviços relacionados à entidade Movimentacao,
//permitindo registrar e consultar movimentações de estoque via RMI.
// Esta interface define operações que o cliente pode invocar de forma distribuída para
//realizar entradas, saídas e consultas de movimentações registradas no servidor.
// Todos os métodos declaram RemoteException, já que chamadas remotas podem sofrer falhas.
public interface ServicoMovimentacao extends Remote {

    // Registra uma nova movimentação de estoque.
    void registrarMovimentacao(int idProduto, String tipo, int quantidade, String observacao) throws RemoteException;

    // Lista todas as movimentações registradas no sistema.
    List<Movimentacao> listarTodasMovimentacoes() throws RemoteException;
}
