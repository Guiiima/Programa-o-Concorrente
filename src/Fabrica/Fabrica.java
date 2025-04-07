package Fabrica;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import EstacaoProducao.EstacaoProducao;
import EstoquePecas.EstoquePecas;
import Loja.Loja;

public class Fabrica {
    private final EstoquePecas estoque;
    private final EstacaoProducao[] estacoes;
    private final Loja[] lojas;
    private final ExecutorService executor;

    public Fabrica() {
        this.estoque = new EstoquePecas();
        this.estacoes = new EstacaoProducao[4];
        this.lojas = new Loja[3];
        this.executor = Executors.newCachedThreadPool();

        // Inicializa estações de produção
        for (int i = 0; i < estacoes.length; i++) {
            estacoes[i] = new EstacaoProducao(i, estoque);
        }

        // Inicializa lojas
        for (int i = 0; i < lojas.length; i++) {
            lojas[i] = new Loja(i, this);
            executor.execute(lojas[i]);
        }

        // Inicializa funcionários
        for (EstacaoProducao estacao : estacoes) {
            for (int i = 0; i < 5; i++) {
                final int estacaoId = estacao.getId();
                final int funcionarioId = i;
                executor.execute(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        estacao.produzirVeiculo(funcionarioId);
                    }
                });
            }
        }

        // Inicializa clientes
        for (int i = 0; i < 20; i++) {
            executor.execute(new Cliente(i, lojas));
        }
    }

    public Veiculo comprarVeiculo(Loja loja) {
        // Tenta comprar de cada estação até conseguir
        for (EstacaoProducao estacao : estacoes) {
            Veiculo veiculo = estacao.venderParaLoja();
            if (veiculo != null) {
                Logger.logVenda(veiculo, loja.getId());
                return veiculo;
            }
        }
        return null;
    }

    public void encerrar() {
        executor.shutdownNow();
        for (Loja loja : lojas) {
            loja.encerrar();
        }
    }

    public static void main(String[] args) {
        Fabrica fabrica = new Fabrica();

        // Executa por um tempo determinado (ou até interrupção)
        try {
            Thread.sleep(60000); // Executa por 1 minuto
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            fabrica.encerrar();
        }
    }
}
