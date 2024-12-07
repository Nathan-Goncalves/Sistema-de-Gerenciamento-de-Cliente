package ordenacao;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import ordenacao.cms.ArquivoCliente;
import ordenacao.cms.ClienteGUI2;
import ordenacao.cms.GeradorDeArquivosDeClientes;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    
    
    private static void lerArquivo() {
        SwingUtilities.invokeLater(() -> {
            ClienteGUI2 gui = new ClienteGUI2();
            gui.setVisible(true);});  
    }
    

    private static void GeraArquivo() {
        Scanner scanner = new Scanner(System.in);

        // Solicitando o nome do arquivo ao usuário
        System.out.print("Digite o nome do arquivo de saída: ");
        String nomeArquivo = scanner.next();

        // Solicitando a quantidade de clientes ao usuário
        System.out.print("Digite a quantidade de clientes a serem gerados: ");
        int quantidadeClientes = scanner.nextInt();

        // Instanciando o gerador
        GeradorDeArquivosDeClientes gerador = new GeradorDeArquivosDeClientes();

        // Gerar um grande dataset de clientes
        gerador.geraGrandeDataSetDeClientes(nomeArquivo, quantidadeClientes);
    }
   
   
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("\nMenu de Operações:");
            System.out.println("1. Criar novo arquivo");
            System.out.println("2. Ler arquivo");
            System.out.println("3. Adicionar usuário");
            System.out.println("4. Remover usuário");
            System.out.println("5. Sair");
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
                    /*adicionarUsuario();
                    break;
                case 4:
                    removerUsuario();
                    break;
                case 5:
                    running = false;
                    System.out.println("Encerrando o programa...");
                    break;*/
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
         }
    }
    
}
