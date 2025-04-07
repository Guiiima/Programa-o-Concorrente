package Logger;
import Models.Veiculo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
public class ProduçaoLogger {

    private static final Semaphore logMutex = new Semaphore(1, true);
    private static final String LOG_PRODUCAO_FILE = "log_producao.txt";
    private static final String LOG_VENDA_FILE = "log_venda.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void logProducao(Veiculo veiculo, int posicaoEsteira) {
        try {
            logMutex.acquire();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_PRODUCAO_FILE, true))) {
                String timestamp = dateFormat.format(new Date());
                String logEntry = String.format(
                        "[%s] PRODUCAO - ID: %d | Cor: %s | Tipo: %s | Estação: %d | Funcionário: %d | Posição Esteira: %d%n",
                        timestamp,
                        veiculo.getId(),
                        veiculo.getCor(),
                        veiculo.getTipo(),
                        veiculo.getEstacaoProducaoId(),
                        veiculo.getFuncionarioId(),
                        posicaoEsteira
                );
                writer.write(logEntry);
            } catch (IOException e) {
                System.err.println("Erro ao escrever no log de produção: " + e.getMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            logMutex.release();
        }
    }

}
