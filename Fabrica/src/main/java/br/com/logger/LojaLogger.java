package br.com.logger;

import br.com.model.Veiculo;

public class LojaLogger {
    public static void logCompra(Veiculo veiculo, int posicaoEsteira, int lojaId) {
        System.out.printf("[LOJA-COMPRA] ID Veículo: %d | LojaController: %d | Posição Esteira: %d%n",
                veiculo.id(), lojaId, posicaoEsteira);
    }
}
