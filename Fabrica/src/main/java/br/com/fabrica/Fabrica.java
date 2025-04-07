package br.com.fabrica;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.EsteiraCircular.EsteiraCircular;
import br.com.Logger.FabricaLogger;
import br.com.Loja.Loja;
import br.com.Models.Veiculo;

public class Fabrica {
    private static final int TAMANHO_ESTEIRA = 40;
    private static final int NUM_ESTACOES_PRODUCAO = 4;
    private static final int NUM_LOJAS = 3;

    private static final EsteiraCircular esteira = new EsteiraCircular(TAMANHO_ESTEIRA);
    private static final EstoquePecas estoque = new EstoquePecas();
//    private final EstacaoProducao[] estacoes;
//    private final Loja[] lojas;
    private final ExecutorService executor;

    public Fabrica() {
//        this.estacoes = new EstacaoProducao[NUM_ESTACOES_PRODUCAO];
//        this.lojas = new Loja[NUM_LOJAS];
        this.executor = Executors.newFixedThreadPool(NUM_ESTACOES_PRODUCAO + NUM_LOJAS);

        // Inicializa estações de produção
        for (int i = 0; i < NUM_ESTACOES_PRODUCAO; i++) {
            executor.execute(new EstacaoProducao(i));
//            estacoes[i] = new EstacaoProducao(i, this.estoque, this.esteira);
        }

        for (int i = 0; i < NUM_LOJAS; ++i) {
            executor.execute(new Loja(i));
        }

//        // Inicializa lojas
//        for (int i = 0; i < lojas.length; i++) {
//            lojas[i] = new Loja(i, this);
//            executor.execute(lojas[i]);
//        }

        // Inicializa funcionários
//        for (EstacaoProducao estacao : estacoes) {
//            for (int i = 0; i < 5; i++) {
//                final int estacaoId = estacao.getId();
//                final int funcionarioId = i;
//                executor.execute(() -> {
//                    while (!Thread.currentThread().isInterrupted()) {
//                        estacao.produzirVeiculo(funcionarioId);
//                    }
//                });
//            }
//        }

    //        // Inicializa clientes
    //        for (int i = 0; i < 20; i++) {
    //            executor.execute(new Cliente(i, lojas));
    //        }
    }

//    public Veiculo comprarVeiculo(Loja loja) {
//        // Tenta comprar de cada estação até conseguir
//        for (EstacaoProducao estacao : estacoes) {
//            Veiculo veiculo = estacao.venderParaLoja();
//            if (veiculo != null) {
//                FabricaLogger.logVenda(veiculo, loja.getId());
//                return veiculo;
//            }
//        }
//        return null;
//    }

    public static void adicionarVeiculo(Veiculo veiculo) {
        int pos = esteira.adicionarVeiculo(veiculo);
        FabricaLogger.logProducao(veiculo, pos);
    }

    public static Veiculo retirarVeiculo(Loja loja) {
        Veiculo veiculo = esteira.removerVeiculo();
        FabricaLogger.logVenda(veiculo, loja.getId());

        return veiculo;
    }

    public static int getEstoque() {
        return estoque.getQuantidadeDisponivel();
    }

    public static boolean retirarPeca() {
        return estoque.retirarPecas();
    }

    public void encerrar() {
        executor.shutdownNow();
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
