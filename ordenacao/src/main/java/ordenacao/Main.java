package ordenacao;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import ordenacao.cms.ArquivoCliente;
import ordenacao.cms.ClienteGUI2;
import ordenacao.cms.GeradorDeArquivosDeClientes;
import ordenacao.GerenciadorTabHashClientes;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    
    private static void lerArquivo() {
        SwingUtilities.invokeLater(() -> {
            ClienteGUI2 gui = new ClienteGUI2();
            gui.setVisible(true);});  
    }


    private static String gerarNomeArquivoSaida(String caminhoEntrada) {
        int pontoIndex = caminhoEntrada.lastIndexOf('.');
        if (pontoIndex == -1) {
            // Caso o arquivo não tenha extensão
            return caminhoEntrada + "_ordenado";
        }
        String nomeBase = caminhoEntrada.substring(0, pontoIndex); // Parte antes do ponto
        String extensao = caminhoEntrada.substring(pontoIndex);   // Parte após o ponto (inclusive)
        return nomeBase + "_ordenado" + extensao;
    }
    

    private static void GeraArquivo() {
        Scanner scanner = new Scanner(System.in);

        // Solicitando o nome do arquivo ao usuário
        System.out.print("Digite o caminho com nome do arquivo de saída: ");
        String nomeArquivo = scanner.next();

        // Solicitando a quantidade de clientes ao usuário
        System.out.print("Digite a quantidade de clientes a serem gerados: ");
        int quantidadeClientes = scanner.nextInt();

        // Instanciando o gerador
        GeradorDeArquivosDeClientes gerador = new GeradorDeArquivosDeClientes();

        // Gerar um grande dataset de clientes
        gerador.geraGrandeDataSetDeClientes(nomeArquivo, quantidadeClientes);
        
        try {
            // Instancia a classe ArquivoCliente
            ArquivoCliente arquivoCliente = new ArquivoCliente();

            // Instancia a classe OrdenacaoExterna
            OrdenacaoExterna ordenacaoExterna = new OrdenacaoExterna(arquivoCliente);

            // Tamanho do chunk: máximo de objetos que serão carregados na memória por vez
            int tamanhoChunk = 100;
            String caminhoSaida = gerarNomeArquivoSaida(nomeArquivo);
            
            // Chamar o método para ordenar o arquivo
            System.out.println("Iniciando a ordenação...");
            ordenacaoExterna.ordenarArquivo(nomeArquivo, caminhoSaida, tamanhoChunk);
            System.out.println("Ordenação concluída! Arquivo salvo em: " + caminhoSaida);
            
        } catch (Exception e) {
            // Tratar exceções, se necessário
            System.err.println("Erro ao ordenar o arquivo:");
            e.printStackTrace();
        }
    }
   
   
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\nMenu de Operações:");
            System.out.println("1. Criar novo arquivo");
            System.out.println("2. Ler arquivo");
            System.out.println("3. Editar Usuario");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();  // Limpa o buffer do scanner

            switch (escolha) {
                case 1:
                    GeraArquivo();
                    break;
                case 2:
                    lerArquivo();
                    break;
                case 3:
                    GerenciadorTabHashClientes gerenciador = new GerenciadorTabHashClientes();
                    gerenciador.exibirMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
         }
    }
    
}
