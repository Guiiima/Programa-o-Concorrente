package FabricaLogger;

import Models.Veiculo;

public class FabricaLogger {
    public static void logProducao(Veiculo veiculo, int posicaoEsteira) {
        System.out.printf("[FABRICA-PRODUCAO] ID: %d | Cor: %s | Tipo: %s | Estação: %d | Funcionário: %d | Posição Esteira: %d%n",
                veiculo.getId(), veiculo.getCor(), veiculo.getTipo(),
                veiculo.getEstacaoProducaoId(), veiculo.getFuncionarioId(), posicaoEsteira);
    }

    public static void logVenda(Veiculo veiculo, int lojaId) {
        System.out.printf("[FABRICA-VENDA] ID: %d | Loja: %d%n", veiculo.getId(), lojaId);
    }
}

public class LojaLogger {
    public static void logCompra(Veiculo veiculo, int posicaoEsteira, int lojaId) {
        System.out.printf("[LOJA-COMPRA] ID Veículo: %d | Loja: %d | Posição Esteira: %d%n",
                veiculo.getId(), lojaId, posicaoEsteira);
    }
}

public class ClienteLogger {
    public static void logCompra(int clienteId, Veiculo veiculo) {
        System.out.printf("[CLIENTE-COMPRA] Cliente: %d | Veículo: %d | Cor: %s | Tipo: %s%n",
                clienteId, veiculo.getId(), veiculo.getCor(), veiculo.getTipo());
    }
}