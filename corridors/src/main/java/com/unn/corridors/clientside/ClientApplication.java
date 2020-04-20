package com.unn.corridors.clientside;

import com.unn.corridors.clientside.client.Client;
import com.unn.corridors.clientside.client.ClientImpl;
import com.unn.corridors.clientside.data.Model;
import com.unn.corridors.clientside.panels.MainPanel;
import com.unn.corridors.serverside.server.Server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class ClientApplication{

    public static void main(String[] args) {
        try {
            Random rand = new Random();

            String serverName = "server";
            String clientName = "Client" + rand.nextInt();
            Registry registry = LocateRegistry.getRegistry("localhost", 2098);
            Server server = (Server) registry.lookup(serverName);

            Client client = new ClientImpl();

            registry.bind(clientName, client);
            int num = server.addClient(clientName);

            Model.getModel().setMyNum(num);
            Model.getModel().setServer(server);

            javax.swing.SwingUtilities.invokeLater(MainPanel::new);

        } catch (RemoteException | AlreadyBoundException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
