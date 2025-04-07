package Logger;

import Models.Veiculo;

public class LojaLogger {
    public static void logCompra(Veiculo veiculo, int posicaoEsteira, int lojaId) {
        System.out.printf("[LOJA-COMPRA] ID Veículo: %d | Loja: %d | Posição Esteira: %d%n",
                veiculo.getId(), lojaId, posicaoEsteira);
    }
}
