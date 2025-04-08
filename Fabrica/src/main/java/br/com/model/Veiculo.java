package br.com.model;

//import br.com.Common.ENUM.*;

import br.com.ENUM.Cor;
import br.com.ENUM.TipoVeiculo;

public record Veiculo(int id, Cor cor, TipoVeiculo tipo, int estacaoProducaoId, int funcionarioId) {
}
