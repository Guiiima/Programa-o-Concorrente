package br.com.logger;

import br.com.model.Veiculo;

public class ClienteLogger {
    public static void logCompra(int clienteId, Veiculo veiculo) {
        System.out.printf("[CLIENTE-COMPRA] Cliente: %d | Veículo: %d | Cor: %s | Tipo: %s%n",
                clienteId, veiculo.id(), veiculo.cor(), veiculo.tipo());
    }
}
