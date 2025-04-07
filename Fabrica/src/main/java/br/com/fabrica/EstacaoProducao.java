package br.com.fabrica;

import br.com.ENUM.Cor;
import br.com.ENUM.TipoVeiculo;
import br.com.Models.Funcionario;
import br.com.Models.GeradorId;
import br.com.Models.Veiculo;

import java.util.concurrent.Semaphore;
import java.util.Random;


public class EstacaoProducao implements Runnable {
    private static final int NUM_FUNCIONARIOS = 5;

    private final int id;
    private final Funcionario[] funcionarios;
    private final Semaphore[] ferramentas;
    private final Random random = new Random();


    public EstacaoProducao(int id) {
        this.id = id;
        this.funcionarios = new Funcionario[NUM_FUNCIONARIOS];
        this.ferramentas = new Semaphore[NUM_FUNCIONARIOS];

        for (int i = 0; i < NUM_FUNCIONARIOS; i++) {
            funcionarios[i] = new Funcionario((id * NUM_FUNCIONARIOS) + i);
            ferramentas[i] = new Semaphore(1);
        }
    }

    @Override
    public void run() {
        while (Fabrica.getEstoque() > 0) {
            for (int i = 0; i < NUM_FUNCIONARIOS; ++i) {
                Veiculo veiculo = this.produzirVeiculo(i);
                Fabrica.adicionarVeiculo(veiculo);
            }
        }
    }

    private Veiculo produzirVeiculo(int posicaoFuncionario) {
        if (posicaoFuncionario < 0 || posicaoFuncionario >= funcionarios.length) {
            throw new IllegalArgumentException("ID de funcionário inválido");
        }

        Funcionario funcionario = funcionarios[posicaoFuncionario];
        Semaphore ferramentaEsquerda = ferramentas[posicaoFuncionario];
        Semaphore ferramentaDireita = ferramentas[(posicaoFuncionario + 1) % NUM_FUNCIONARIOS];

        try {
            if (posicaoFuncionario < (funcionarios.length - 1)) {
                ferramentaEsquerda.acquire();
                ferramentaDireita.acquire();
            } else {
                ferramentaDireita.acquire();
                ferramentaEsquerda.acquire();
            }

            if (!Fabrica.retirarPeca()) {
                return null;
            }

            this.dormirAleatoriamente(500, 2000);

            return new Veiculo(
                    GeradorId.getNextId(),
                    Cor.values()[random.nextInt(Cor.values().length)],
                    TipoVeiculo.values()[random.nextInt(TipoVeiculo.values().length)],
                    id,
                    funcionario.id()
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    private void dormirAleatoriamente(long min, long max) {
        try {
            Thread.sleep((long) (random.nextDouble() * (max - min) + min));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
