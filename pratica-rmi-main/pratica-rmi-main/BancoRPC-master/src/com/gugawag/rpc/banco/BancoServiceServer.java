package com.gugawag.rpc.banco;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoServiceServer extends UnicastRemoteObject implements BancoServiceIF {

    private Map<String, Conta> saldoContas;

    public BancoServiceServer() throws RemoteException {
        saldoContas = new HashMap<>();
        saldoContas.put("1", new Conta("1", 200.0));
        saldoContas.put("2", new Conta("2", 147.0));
        saldoContas.put("3", new Conta("3", 750.0));
    }

    @Override
    public double saldo(String conta) throws RemoteException {
        Conta c = saldoContas.get(conta);
        if (c != null) {
            return c.getSaldo();
        } else {
            throw new RemoteException("Conta não encontrada!");
        }
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        return saldoContas.size();
    }

    @Override
    public void cadastrarConta(String numero, double saldoInicial) throws RemoteException {
        if (!saldoContas.containsKey(numero)) {
            saldoContas.put(numero, new Conta(numero, saldoInicial));
        } else {
            throw new RemoteException("Conta já cadastrada!");
        }
    }

    @Override
    public boolean pesquisarConta(String conta) throws RemoteException {
        return saldoContas.containsKey(conta);
    }

    @Override
    public void removerConta(String conta) throws RemoteException {
        if (saldoContas.containsKey(conta)) {
            saldoContas.remove(conta);
        } else {
            throw new RemoteException("Conta não encontrada para remoção!");
        }
    }

    @Override
    public void adicionarConta(Conta novaConta) throws RemoteException {
        String numeroConta = novaConta.getNumero();
        if (!saldoContas.containsKey(numeroConta)) {
            saldoContas.put(numeroConta, novaConta);
        } else {
            throw new RemoteException("Conta já cadastrada!");
        }
    }

    @Override
    public List<Conta> listarContas() throws RemoteException {
        return new ArrayList<>(saldoContas.values());
    }
}
