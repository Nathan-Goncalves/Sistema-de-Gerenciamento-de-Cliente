package ordenacao;
import java.io.*;
import java.util.*;

import ordenacao.cms.ArquivoCliente;
import ordenacao.cms.Cliente;

public class OrdenacaoExterna {

    private final ArquivoCliente arquivoCliente;

    public OrdenacaoExterna(ArquivoCliente arquivoCliente) {
        this.arquivoCliente = arquivoCliente;
    }

    /**
     * Ordena um arquivo contendo objetos Cliente utilizando o método de ordenação externa.
     *
     * @param caminhoEntrada   Caminho do arquivo de entrada.
     * @param caminhoSaida     Caminho do arquivo de saída ordenado.
     * @param tamanhoChunk     Número máximo de objetos em memória por vez (chunk).
     */
    public void ordenarArquivo(String caminhoEntrada, String caminhoSaida, int tamanhoChunk) throws IOException, ClassNotFoundException {
        // Passo 1: Dividir o arquivo em chunks ordenados
        List<File> arquivosTemporarios = dividirEOrdenarChunks(caminhoEntrada, tamanhoChunk);

        // Passo 2: Intercalar os arquivos temporários para gerar o arquivo final
        intercalarArquivos(arquivosTemporarios, caminhoSaida);

        // Limpar arquivos temporários
        for (File tempFile : arquivosTemporarios) {
            tempFile.delete();
        }
    }

    private List<File> dividirEOrdenarChunks(String caminhoEntrada, int tamanhoChunk) throws IOException, ClassNotFoundException {
        List<File> arquivosTemporarios = new ArrayList<>();
        arquivoCliente.abrirArquivo(caminhoEntrada, "leitura", Cliente.class);

        try {
            while (true) {
                List<Cliente> chunk = arquivoCliente.leiaDoArquivo(tamanhoChunk);
                if (chunk.isEmpty()) break;

                // Ordenar o chunk em memória
                chunk.sort(Comparator.comparing(Cliente::getNome)); // Altere o critério de ordenação se necessário

                // Escrever o chunk ordenado em um arquivo temporário
                File tempFile = File.createTempFile("chunk_", ".dat");
                ArquivoCliente tempArquivoCliente = new ArquivoCliente();
                tempArquivoCliente.abrirArquivo(tempFile.getAbsolutePath(), "escrita", Cliente.class);
                tempArquivoCliente.escreveNoArquivo(chunk);
                tempArquivoCliente.fechaArquivo();

                arquivosTemporarios.add(tempFile);
            }
        } finally {
            arquivoCliente.fechaArquivo();
        }

        return arquivosTemporarios;
    }

    private void intercalarArquivos(List<File> arquivosTemporarios, String caminhoSaida) throws IOException, ClassNotFoundException {
        PriorityQueue<ClienteReader> pq = new PriorityQueue<>(
            Comparator.comparing(reader -> reader.peek().getNome()) // Comparar pelo atributo desejado
        );
    
        // Inicializar os leitores para cada arquivo temporário
        for (File file : arquivosTemporarios) {
            ClienteReader reader = new ClienteReader(file);
            if (reader.hasNext()) {
                pq.add(reader);
            }
        }
    
        ArquivoCliente arquivoSaida = new ArquivoCliente();
        arquivoSaida.abrirArquivo(caminhoSaida, "escrita", Cliente.class);
    
        try {
            while (!pq.isEmpty()) {
                ClienteReader reader = pq.poll();
                Cliente cliente = reader.poll();
    
                // Escrever o cliente no arquivo final
                arquivoSaida.escreveNoArquivo(Collections.singletonList(cliente));
    
                if (reader.hasNext()) {
                    pq.add(reader);
                } else {
                    reader.close();
                }
            }
        } finally {
            arquivoSaida.fechaArquivo();
        }
    }

    private static class ClienteReader {
        private ObjectInputStream inputStream;
        private Cliente current;

        public ClienteReader(File file) throws IOException, ClassNotFoundException {
            this.inputStream = new ObjectInputStream(new FileInputStream(file));
            advance();
        }

        private void advance() throws IOException, ClassNotFoundException {
            try {
                current = (Cliente) inputStream.readObject();
            } catch (EOFException e) {
                current = null;
            }
        }

        public Cliente peek() {
            return current;
        }

        public Cliente poll() throws IOException, ClassNotFoundException {
            Cliente temp = current;
            advance();
            return temp;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void close() throws IOException {
            inputStream.close();
        }
    }
}