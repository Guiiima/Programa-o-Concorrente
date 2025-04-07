package EstacaoProducao;

import ENUM.Cor;
import ENUM.TipoVeiculo;
import EsteiraCircular.EsteiraCircular;
import EstoquePecas.EstoquePecas;
import Models.Funcionario;
import Models.GeradorId;
import Models.Veiculo;
import Logger.ProduçaoLogger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EstacaoProducao {
    private static final int NUM_FUNCIONARIOS = 5;
    private static final int TAMANHO_ESTEIRA = 40;
    private static final int PECAS_POR_VEICULO = 10;

    private final int id;
    private final Funcionario[] funcionarios;
    private final Lock[] ferramentas;
    private final EsteiraCircular esteira;
    private final EstoquePecas estoque;

    public EstacaoProducao(int id, EstoquePecas estoque) {
        this.id = id;
        this.estoque = estoque;
        this.funcionarios = new Funcionario[NUM_FUNCIONARIOS];
        this.ferramentas = new ReentrantLock[NUM_FUNCIONARIOS];
        this.esteira = new EsteiraCircular(TAMANHO_ESTEIRA);

        for (int i = 0; i < NUM_FUNCIONARIOS; i++) {
            funcionarios[i] = new Funcionario(i);
            ferramentas[i] = new ReentrantLock();
        }
    }

    public EstacaoProducao(int id, Funcionario[] funcionarios, Lock[] ferramentas, EsteiraCircular esteira, EstoquePecas estoque) {
        this.id = id;
        this.funcionarios = funcionarios;
        this.ferramentas = ferramentas;
        this.esteira = esteira;
        this.estoque = estoque;
    }

    public Veiculo produzirVeiculo(int idFuncionario) {
        if (idFuncionario < 0 || idFuncionario >= funcionarios.length) {
            throw new IllegalArgumentException("ID de funcionário inválido");
        }

        Funcionario funcionario = funcionarios[idFuncionario];
        Lock ferramentaEsquerda = ferramentas[idFuncionario];
        Lock ferramentaDireita = ferramentas[(idFuncionario + 1) % NUM_FUNCIONARIOS];

        while (true) {
            ferramentaEsquerda.lock();
            if (ferramentaDireita.tryLock()) {
                break;
            }
            ferramentaEsquerda.unlock();
            dormirAleatoriamente(100, 500);
        }

        try {
            if (!estoque.retirarPecas(PECAS_POR_VEICULO)) {
                return null;
            }

            dormirAleatoriamente(500, 2000);

            Veiculo veiculo = new Veiculo(
                    GeradorId.getNextId(),
                    Cor.values()[(int) (Math.random() * Cor.values().length)],
                    TipoVeiculo.values()[(int) (Math.random() * TipoVeiculo.values().length)],
                    id,
                    idFuncionario
            );

            int posicaoEsteira = esteira.adicionarVeiculo(veiculo);
            ProduçaoLogger.logProducao(veiculo, posicaoEsteira);
            return veiculo;
        } finally {
            ferramentaDireita.unlock();
            ferramentaEsquerda.unlock();
        }
    }

    private void dormirAleatoriamente(long min, long max) {
        try {
            Thread.sleep((long) (Math.random() * (max - min) + min));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Veiculo venderParaLoja() {
        return esteira.removerVeiculo();
    }
}
