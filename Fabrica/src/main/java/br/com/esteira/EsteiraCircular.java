package br.com.esteira;

import br.com.model.Veiculo;

import java.util.concurrent.Semaphore;

public class EsteiraCircular {
    private final Veiculo[] buffer;
    private final int capacidade;
    private int tail = 0;
    private int head = 0;
    private int count = 0;
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

            buffer[head] = veiculo;
            int posicao = head;
            head = (++head) % capacidade;
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

            Veiculo veiculo = buffer[tail];
            tail = (++tail) % capacidade;
            count--;

            mutex.release();
            espacos.release();

            return veiculo;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public boolean esteiraCheia() {
        return count == capacidade;
    }

    public boolean esteiraVazia() {
        return count == 0;
    }
}