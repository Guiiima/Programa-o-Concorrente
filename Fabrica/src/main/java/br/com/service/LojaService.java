package br.com.service;

import br.com.loja.Loja;
import br.com.fabrica.Fabrica;
import br.com.model.Veiculo;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LojaService {
    private static final int NUMERO_LOJAS = 3;
    private final Loja[] lojas = new Loja[NUMERO_LOJAS];

    public LojaService() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMERO_LOJAS);

        Fabrica fabrica = new Fabrica();
        for (int i = 0; i < NUMERO_LOJAS; ++i) {
            lojas[i] = new Loja(i);
            executorService.execute(lojas[i]);
        }
    }

    public Veiculo vender() {
        Loja loja = lojas[(int) (Math.random() * lojas.length)];
        return loja.venderParaCliente();
    }
}
