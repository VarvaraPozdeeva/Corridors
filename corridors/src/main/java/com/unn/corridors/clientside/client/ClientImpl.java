package com.unn.corridors.clientside.client;

import com.unn.corridors.clientside.data.Cell;
import com.unn.corridors.clientside.data.Message;
import com.unn.corridors.clientside.data.Model;
import com.unn.corridors.datamodel.Move;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private Model model = Model.getModel();

    public ClientImpl() throws RemoteException {
        super();
    }

    public Move addMove(Move move) {
        model.setLastMove(move);
        if(move.getClientNum() == model.getMyNum()) {
            model.setIsMyMove(false);
            model.setCurrentMessage(Message.OTHER_MOVE);
        }
        else {
            if(move.getClientNum() != model.getMyNum()) {
                model.setIsMyMove(true);
                model.setCurrentMessage(Message.YOUR_MOVE);
            }
        }
        if(move.getIndexCellFill()>0) {
            model.getCells().add(new Cell(move.getIndexCellFill(), move.getClientNum()));
            if(move.getClientNum() == model.getMyNum()){
                model.setIsMyMove(true);
                model.setCurrentMessage(Message.YOUR_MOVE);
            } else {
                model.setIsMyMove(false);
                model.setCurrentMessage(Message.OTHER_MOVE);
            }
        }
        model.update();
        return null;
    }
}
