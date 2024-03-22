package com.gugawag.rpc.banco;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AppClienteBanco {

    private static BancoServiceIF banco;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        banco = (BancoServiceIF) registry.lookup("BancoService");

        Scanner entrada = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            menu();
            opcao = entrada.nextInt();
            switch (opcao) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    exibirQuantidadeContas();
                    break;
                case 3:
                    cadastrarConta();
                    break;
                case 4:
                    pesquisarConta();
                    break;
                case 5:
                    removerConta();
                    break;
                case 6:
                    listarContas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menu() {
        System.out.println("Banco de Ricardo Pereira Lins");
        System.out.println("\n=== BANCO RMI (ou FMI?!) ===");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Cadastro de nova conta");
        System.out.println("4 - Pesquisa de conta");
        System.out.println("5 - Remoção de conta");
        System.out.println("6 - Listar todas as contas");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void consultarSaldo() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o número da conta:");
        String conta = entrada.next();
        try {
            double saldo = banco.saldo(conta);
            System.out.println("Saldo da conta " + conta + ": " + saldo);
        } catch (RemoteException e) {
            System.out.println("Erro ao consultar saldo: " + e.getMessage());
        }
    }

    private static void exibirQuantidadeContas() {
        try {
            int quantidade = banco.quantidadeContas();
            System.out.println("Quantidade de contas: " + quantidade);
        } catch (RemoteException e) {
            System.out.println("Erro ao obter quantidade de contas: " + e.getMessage());
        }
    }

    private static void cadastrarConta() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o número da nova conta:");
        String novoNumero = entrada.next();
        System.out.println("Digite o saldo inicial:");
        double novoSaldo = entrada.nextDouble();
        try {
            banco.cadastrarConta(novoNumero, novoSaldo);
            System.out.println("Conta cadastrada com sucesso!");
        } catch (RemoteException | InputMismatchException e) {
            System.out.println("Erro ao cadastrar conta: " + e.getMessage());
        }
    }

    private static void pesquisarConta() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o número da conta a pesquisar:");
        String contaPesquisa = entrada.next();
        try {
            boolean encontrada = banco.pesquisarConta(contaPesquisa);
            if (encontrada) {
                System.out.println("Conta encontrada.");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (RemoteException e) {
            System.out.println("Erro ao pesquisar conta: " + e.getMessage());
        }
    }

    private static void removerConta() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o número da conta a remover:");
        String contaRemover = entrada.next();
        try {
            banco.removerConta(contaRemover);
            System.out.println("Conta removida com sucesso!");
        } catch (RemoteException e) {
            System.out.println("Erro ao remover conta: " + e.getMessage());
        }
    }

    private static void listarContas() {
        try {
            System.out.println("Listando todas as contas:");
            for (Conta conta : banco.listarContas()) {
                System.out.println("Número: " + conta.getNumero() + ", Saldo: " + conta.getSaldo());
            }
        } catch (RemoteException e) {
            System.out.println("Erro ao listar contas: " + e.getMessage());
        }
    }
}
