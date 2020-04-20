package com.unn.corridors.clientside.panels;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JFrame {
    public MainPanel() {
        super("Corridors");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
        pack();
        setVisible(true);
    }

    private void initComponent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        StatusPanel status = new StatusPanel();
        GameField pane = new GameField(mainPanel);

            mainPanel.add(pane, BorderLayout.CENTER);
            mainPanel.add(status, BorderLayout.SOUTH);
            revalidate();
        getContentPane().add(mainPanel);
        setPreferredSize(new Dimension(420, 470));
    }
}
