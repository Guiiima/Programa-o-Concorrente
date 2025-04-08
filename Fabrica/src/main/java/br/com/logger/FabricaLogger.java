package br.com.logger;

import br.com.model.Veiculo;

public class FabricaLogger {
    public static void logProducao(Veiculo veiculo, int posicaoEsteira) {
        System.out.printf("[FABRICA-PRODUCAO] ID: %d | Cor: %s | Tipo: %s | Estação: %d | Funcionário: %d | Posição Esteira: %d%n",
                veiculo.id(), veiculo.cor(), veiculo.tipo(),
                veiculo.estacaoProducaoId(), veiculo.funcionarioId(), posicaoEsteira);
    }

    public static void logVenda(Veiculo veiculo, int lojaId) {
        System.out.printf("[FABRICA-VENDA] ID: %d | LojaController: %d%n", veiculo.id(), lojaId);
    }
}

