package br.com.Logger;

import br.com.Models.Veiculo;

public class FabricaLogger {
    public static void logProducao(Veiculo veiculo, int posicaoEsteira) {
        System.out.printf("[FABRICA-PRODUCAO] ID: %d | Cor: %s | Tipo: %s | Estação: %d | Funcionário: %d | Posição Esteira: %d%n",
                veiculo.id(), veiculo.cor(), veiculo.tipo(),
                veiculo.estacaoProducaoId(), veiculo.funcionarioId(), posicaoEsteira);
    }

    public static void logVenda(Veiculo veiculo, int lojaId) {
        System.out.printf("[FABRICA-VENDA] ID: %d | Loja: %d%n", veiculo.id(), lojaId);
    }
}

