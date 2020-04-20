package com.unn.corridors.serverside.server;

import com.unn.corridors.clientside.client.Client;
import com.unn.corridors.datamodel.Move;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private Registry registry;
    private List<Client> clients;

    public ServerImpl(Registry registry) throws RemoteException {
        super();
        this.registry = registry;
        clients = new ArrayList<>();
    }

    public void addMove(Move move) {
        System.out.println("ServerImpl.addMove" + move);
        clients.forEach(client -> {
            try {
                client.addMove(move);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    public int addClient(String name) throws RemoteException, NotBoundException {
        Client client = (Client) registry.lookup(name);
        clients.add(client);
        System.out.println("Connect clientside" + clients.size());
        return clients.size();

    }
}
