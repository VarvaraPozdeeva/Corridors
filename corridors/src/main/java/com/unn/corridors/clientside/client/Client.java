package com.unn.corridors.clientside.client;

import com.unn.corridors.datamodel.Move;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    Move addMove(Move move) throws RemoteException;
}

