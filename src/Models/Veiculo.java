package Models;

import ENUM.Cor;
import ENUM.TipoVeiculo;

public class Veiculo {
    private final int id;
    private final Cor cor;
    private final TipoVeiculo tipo;
    private final int estacaoProducaoId;
    private final int funcionarioId;

    public Veiculo(int id, Cor cor, TipoVeiculo tipo, int estacaoProducaoId, int funcionarioId) {
        this.id = id;
        this.cor = cor;
        this.tipo = tipo;
        this.estacaoProducaoId = estacaoProducaoId;
        this.funcionarioId = funcionarioId;
    }

    public int getId() {
        return id;
    }

    public Cor getCor() {
        return cor;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public int getEstacaoProducaoId() {
        return estacaoProducaoId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }
}
