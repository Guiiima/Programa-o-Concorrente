package br.com.fabrica;
import java.util.concurrent.Semaphore;

public class EstoquePecas {
    private static final int CAPACIDADE_MAXIMA = 500;
    private int quantidadeDisponivel;
    private Semaphore semaforoEsteira = new Semaphore(5, true);
    private Semaphore mutex = new Semaphore(1, true);

    public EstoquePecas() {
        quantidadeDisponivel = CAPACIDADE_MAXIMA;
    }

    public int getQuantidadeDisponivel() {
        return this.quantidadeDisponivel;
    }

    public boolean retirarPecas() {
        try {
            semaforoEsteira.acquire();
            mutex.acquire();

            if (quantidadeDisponivel >= 1) {
                --quantidadeDisponivel;
                mutex.release();
                return true;
            }

            mutex.release();
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            semaforoEsteira.release();
        }
    }
}
