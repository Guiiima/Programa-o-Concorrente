package br.com.loja;

import br.com.logger.LojaLogger;
import br.com.model.Veiculo;
import br.com.esteira.EsteiraCircular;
import br.com.fabrica.Fabrica;

public class Loja implements Runnable {
    private static final int TAMANHO_ESTEIRA = 30;
    private final int id;
    private final EsteiraCircular esteira;
    private boolean ativa = true;

    public Loja(int id) {
        this.id = id;
        this.esteira = new EsteiraCircular(TAMANHO_ESTEIRA);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void run() {
        while (ativa) {
            if (!Fabrica.getEsteiraCircular().esteiraVazia()) {
                Veiculo veiculo = Fabrica.retirarVeiculo(this);
                if (veiculo != null) {
                    int posicao = esteira.adicionarVeiculo(veiculo);
                    LojaLogger.logCompra(veiculo, posicao, id);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        ativa = false;
                    }
                }
            }
        }
    }

    public Veiculo venderParaCliente() {
        return esteira.removerVeiculo();
    }

    public void encerrar() {
        this.ativa = false;
    }
}