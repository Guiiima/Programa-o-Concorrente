package br.com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int NUMERO_CLIENTES = 20;


    public static void main(String[] args) {
        try(ExecutorService executor = Executors.newFixedThreadPool(NUMERO_CLIENTES)) {
            for (int i = 0; i < NUMERO_CLIENTES; ++i) {
                executor.execute(new Cliente(i));
            }
        }
    }
}