package EsteiraCircular;

import Models.Veiculo;

import java.util.concurrent.Semaphore;

public class EsteiraCircular {
    private final Veiculo[] buffer;
    private final int capacidade;
    private int inicio = 0;
    private int fim = 0;
    private int count = 0;
    private Semaphore mutex = new Semaphore(1, true);
    private Semaphore itens = new Semaphore(0, true);
    private Semaphore espacos;

    public EsteiraCircular(int capacidade) {
        this.capacidade = capacidade;
        this.buffer = new Veiculo[capacidade];
        this.espacos = new Semaphore(capacidade, true);
    }

    public int adicionarVeiculo(Veiculo veiculo) {
        try {
            espacos.acquire();
            mutex.acquire();

            buffer[fim] = veiculo;
            int posicao = fim;
            fim = (fim + 1) % capacidade;
            count++;

            mutex.release();
            itens.release();

            return posicao;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    public Veiculo removerVeiculo() {
        try {
            itens.acquire();
            mutex.acquire();

            Veiculo veiculo = buffer[inicio];
            inicio = (inicio + 1) % capacidade;
            count--;

            mutex.release();
            espacos.release();

            return veiculo;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}