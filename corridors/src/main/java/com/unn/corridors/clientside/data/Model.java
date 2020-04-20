package com.unn.corridors.clientside.data;

import com.unn.corridors.clientside.panels.IObserver;
import com.unn.corridors.datamodel.Move;
import com.unn.corridors.serverside.server.Server;
import lombok.Data;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public class Model {

    private static Model model = new Model();
    private Color myColor;
    private int myNum;
    private Server server;
    private Move lastMove;
    private ArrayList<IObserver> observers = new ArrayList<>();
    private Set<Cell> cells = new HashSet<>();
    private Message currentMessage;
    private Boolean isMyMove;
    public static Model getModel(){
        return model;
    }

    public void update() {
        observers.forEach(IObserver::update);
    }

    public void addMove(Move move) throws RemoteException {
        server.addMove(move);
    }

    public Long getMyCells() {
        return cells.stream().filter(cell -> cell.Side == myNum).count();
    }

    public Long getOtherCells() {
        return cells.size() - getMyCells();
    }

    public void setMyNum(int num) {
        myNum = num;
        if (num % 2 == 1) {
            myColor = Color.BLUE;
            currentMessage = Message.YOUR_MOVE;
            isMyMove = true;
        } else {
            myColor = Color.RED;
            currentMessage = Message.OTHER_MOVE;
            isMyMove = false;
        }
    }
}
