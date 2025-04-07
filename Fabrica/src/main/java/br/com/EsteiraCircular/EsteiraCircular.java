package br.com.EsteiraCircular;

import br.com.Models.Veiculo;

import java.util.concurrent.Semaphore;

public class EsteiraCircular {
    private final Veiculo[] buffer;
    private final int capacidade;
    private int lastConsumed = 0;
    private int lastProduced = 0;
    private final Semaphore mutex;
    private final Semaphore itens;
    private final Semaphore espacos;

    public EsteiraCircular(int capacidade) {
        this.capacidade = capacidade;
        this.buffer = new Veiculo[capacidade];

        this.mutex = new Semaphore(1, true);
        this.itens = new Semaphore(0, true);
        this.espacos = new Semaphore(capacidade, true);
    }

    public int adicionarVeiculo(Veiculo veiculo) {
        try {
            espacos.acquire();
            mutex.acquire();

            buffer[lastProduced] = veiculo;
            int posicao = lastProduced;
            lastProduced = (++lastProduced) % capacidade;
//            count++;

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

            Veiculo veiculo = buffer[lastConsumed];
            lastConsumed = (++lastConsumed) % capacidade;
//            count--;

            mutex.release();
            espacos.release();

            return veiculo;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}