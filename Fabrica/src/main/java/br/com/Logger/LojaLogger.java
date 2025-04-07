package br.com.Logger;

import br.com.Models.Veiculo;

public class LojaLogger {
    public static void logCompra(Veiculo veiculo, int posicaoEsteira, int lojaId) {
        System.out.printf("[LOJA-COMPRA] ID Veículo: %d | Loja: %d | Posição Esteira: %d%n",
                veiculo.id(), lojaId, posicaoEsteira);
    }
}
