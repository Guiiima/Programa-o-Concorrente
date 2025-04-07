package EstoquePecas;
import java.util.concurrent.Semaphore;

public class EstoquePecas {
    private static final int CAPACIDADE_MAXIMA = 500;
    private int quantidadeDisponivel;
    private Semaphore semaforoEsteira = new Semaphore(5, true);
    private Semaphore mutex = new Semaphore(1, true);

    public EstoquePecas() {
        this.quantidadeDisponivel = CAPACIDADE_MAXIMA;
    }

    public boolean retirarPecas(int quantidade) {
        try {
            semaforoEsteira.acquire();
            mutex.acquire();

            if (quantidadeDisponivel >= quantidade) {
                quantidadeDisponivel -= quantidade;
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

    public void reporPecas(int quantidade) {
        try {
            mutex.acquire();
            quantidadeDisponivel = Math.min(CAPACIDADE_MAXIMA, quantidadeDisponivel + quantidade);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }
}
