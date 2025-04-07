package Loja;

import Models.Veiculo;
import EsteiraCircular.EsteiraCircular;
import java.util.concurrent.Semaphore;

public class Loja implements Runnable {
    private final int id;
    private final Fabrica fabrica;
    private final EsteiraCircular esteira;
    private boolean ativa = true;

    public Loja(int id, Fabrica fabrica) {
        this.id = id;
        this.fabrica = fabrica;
        this.esteira = new EsteiraCircular(30); // Capacidade da esteira da loja
    }

    @Override
    public void run() {
        while (ativa) {
            Veiculo veiculo = fabrica.comprarVeiculo(this);
            if (veiculo != null) {
                int posicao = esteira.adicionarVeiculo(veiculo);
                LojaLogger.logCompra(veiculo, posicao, id);
            } else {
                try {
                    Thread.sleep(1000); // Espera antes de tentar novamente
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    ativa = false;
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