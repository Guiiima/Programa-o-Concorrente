package Cliente;

import Models.Veiculo;

public class Cliente implements Runnable {
    private final int id;
    private final Loja[] lojas;
    private int carrosComprados = 0;

    public Cliente(int id, Loja[] lojas) {
        this.id = id;
        this.lojas = lojas;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Escolhe uma loja aleatória
            Loja loja = lojas[(int)(Math.random() * lojas.length)];

            Veiculo veiculo = loja.venderParaCliente();
            if (veiculo != null) {
                carrosComprados++;
                ClienteLogger.logCompra(id, veiculo);

                // Tempo aleatório antes da próxima compra
                try {
                    Thread.sleep((long)(Math.random() * 3000 + 1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                // Espera se não há carros disponíveis
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}