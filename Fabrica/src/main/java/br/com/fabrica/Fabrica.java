package br.com.fabrica;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.esteira.EsteiraCircular;
import br.com.logger.FabricaLogger;
import br.com.loja.Loja;
import br.com.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class Fabrica {
    private static final int TAMANHO_ESTEIRA = 40;
    private static final int NUM_ESTACOES_PRODUCAO = 4;
//    private static final int NUM_LOJAS = 3;

    private static final EsteiraCircular esteira = new EsteiraCircular(TAMANHO_ESTEIRA);
    private static final EstoquePecas estoque = new EstoquePecas();

    private final ExecutorService executor;

    public Fabrica() {
        this.executor = Executors.newFixedThreadPool(NUM_ESTACOES_PRODUCAO);

        for (int i = 0; i < NUM_ESTACOES_PRODUCAO; i++) {
            executor.execute(new EstacaoProducao(i));
        }

//        for (int i = 0; i < NUM_LOJAS; ++i) {
//            executor.execute(new LojaController(i));
//        }
    }

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
}
