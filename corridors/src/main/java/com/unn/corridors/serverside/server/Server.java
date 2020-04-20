package com.unn.corridors.serverside.server;

import com.unn.corridors.datamodel.Move;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    void addMove(Move move)  throws RemoteException;
    int addClient(String name) throws RemoteException, NotBoundException;
}
