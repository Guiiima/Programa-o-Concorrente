package br.com;

import br.com.logger.ClienteLogger;
import br.com.model.Veiculo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Cliente implements Runnable {
    private final int id;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String endpoint = "http://localhost:8080/veiculos/comprar";

    private int carrosComprados = 0;

    public Cliente(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            ResponseEntity<Veiculo> response = restTemplate.getForEntity(endpoint, Veiculo.class);
            Veiculo veiculo = response.getBody();

            if (veiculo != null) {
                carrosComprados++;
                ClienteLogger.logCompra(id, veiculo);
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}