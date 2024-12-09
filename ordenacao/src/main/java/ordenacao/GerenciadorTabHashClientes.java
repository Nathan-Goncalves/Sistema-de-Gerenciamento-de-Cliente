package ordenacao;

import java.util.Scanner;
import ordenacao.cms.TabHashClientes;
import ordenacao.cms.Cliente;


public class GerenciadorTabHashClientes {
    private TabHashClientes tabela;

    public GerenciadorTabHashClientes() {
        this.tabela = new TabHashClientes();
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Menu Gerenciador de Tabela Hash ===");
            System.out.println("1. Inserir cliente");
            System.out.println("2. Remover cliente");
            System.out.println("0. Sair");
            System.out.print("Digite sua opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir quebra de linha

            switch (opcao) {
                case 1:
                    inserirCliente(scanner);
                    break;
                case 2:
                    removerCliente(scanner);
                    break;
                case 0:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void inserirCliente(Scanner scanner) {
        System.out.println("\n=== Inserir Cliente ===");
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o sobrenome do cliente: ");
        String sobrenome = scanner.nextLine();
        System.out.print("Digite o endereço do cliente: ");
        String endereco = scanner.nextLine();
        System.out.print("Digite o telefone do cliente: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite a idade do cliente: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Consumir quebra de linha

        Cliente cliente = new Cliente(nome, sobrenome, endereco, telefone, idade);
        boolean sucesso = tabela.create(cliente);

        if (sucesso) {
            System.out.println("Cliente inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir cliente. Colisão ou problema detectado.");
        }
    }

    private void removerCliente(Scanner scanner) {
        System.out.println("\n=== Remover Cliente ===");
        System.out.print("Digite o nome do cliente para remover: ");
        String nome = scanner.nextLine();

        boolean sucesso = tabela.delete(nome);

        if (sucesso) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado ou erro ao remover.");
        }
    }
}
