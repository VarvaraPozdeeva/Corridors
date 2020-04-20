package com.unn.corridors.clientside.panels;

import com.unn.corridors.clientside.data.Model;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements IObserver {

    private JLabel status;
    private JLabel message = new JLabel();
    private Model model = Model.getModel();

    StatusPanel() {
        model.getObservers().add(this);
        status = new JLabel("Your cells =" +  model.getMyCells()+" , other cells = " + model.getOtherCells());
        message.setText("Message: " + model.getCurrentMessage().valueOf());

        setLayout(new GridLayout(2,1));
        add(status);
        add(message);
    }

    @Override
    public void update() {
        status.setText("Your cells =" +  model.getMyCells()+" , other cells = " + model.getOtherCells());
        message.setText("Message: " + model.getCurrentMessage().valueOf());
        revalidate();
    }
}