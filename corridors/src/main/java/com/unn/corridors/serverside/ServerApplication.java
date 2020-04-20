package com.unn.corridors.serverside;

import com.unn.corridors.serverside.server.Server;
import com.unn.corridors.serverside.server.ServerImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerApplication {
    public static void main(String[] args) {
        try {
            final Registry registry = LocateRegistry.createRegistry(2098);

            Server server = null;
            try {
                server = new ServerImpl(registry);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            String serverName = "server";
            registry.bind(serverName, server);
            System.out.println("server start");

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
