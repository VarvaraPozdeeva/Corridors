package com.unn.corridors.clientside.panels;

import com.unn.corridors.clientside.data.Cell;
import com.unn.corridors.clientside.data.Model;
import com.unn.corridors.datamodel.Move;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameField extends JPanel implements IObserver {

    private Model model = Model.getModel();
    private int columnCount = 10;
    private ArrayList<MyRectangle> rectangles;
    private Move movedRec = null;
    private Move selectedRec = null;
    private Set<MyRectangle> mySelectedRectangles;
    private Set<MyRectangle> otherSelectedRectangles;
    private Set<MyRectangle> mySelectedCells;
    private Set<MyRectangle> otherSelectedCells;
    private ArrayList<MyRectangle> cells;


    GameField(JPanel mainPanel) {
        model.getObservers().add(this);

        rectangles = new ArrayList<>();
        mySelectedRectangles = new HashSet<>();
        otherSelectedRectangles = new HashSet<>();
        cells = new ArrayList<>();
        mySelectedCells = new HashSet<>();
        otherSelectedCells = new HashSet<>();

        MouseAdapter mouseHandler;
        mouseHandler = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int cellWidth = 40;
                int cellHeight = 40;

                if (e.getX() >= 0 && e.getY() >= 0) {

                    int column = (e.getX()) / cellWidth;
                    int row = (e.getY()) / cellHeight;

                    if (column >= 0 && row >= 0 && column < columnCount && row < columnCount) {

                        if ((e.getX() % cellWidth) > 10 && (e.getY() % cellHeight) < 10)
                            selectedRec = new Move(column, row, 0, model.getMyNum(), -1, true);

                        if ((e.getX() % cellWidth) < 10 && (e.getY() % cellHeight) > 10)
                            selectedRec = new Move(column, row, 1, model.getMyNum(), -1, true);
                    }
                }
                repaint();
            }

            @SneakyThrows
            @Override
            public void mousePressed(MouseEvent e) {

                if (model.getIsMyMove()) {
                    System.out.println("TestPane.mousePressed");
                    int width = getWidth();
                    int height = getHeight();

                    int cellWidth = width / columnCount;
                    int cellHeight = height / columnCount;

                    if (e.getX() >= 0 && e.getY() >= 0) {

                        int column = (e.getX()) / cellWidth;
                        int row = (e.getY()) / cellHeight;

                        if (column >= 0 && row >= 0 && column < columnCount && row < columnCount) {

                            if ((e.getX() % cellWidth) > 10 && (e.getY() % cellHeight) < 10)
                                movedRec = new Move(column, row, 0, model.getMyNum(), -1, true);

                            if ((e.getX() % cellWidth) < 10 && (e.getY() % cellHeight) > 10)
                                movedRec = new Move(column, row, 1, model.getMyNum(), -1, true);
                        }
                    }

                    if (movedRec != null) {
                        addSelectedRectangle(movedRec, true);
                        int index = checkCell(movedRec, true);
                        if (index != -1) {
                            movedRec.setIndexCellFill(index);
                        }
                        model.addMove(movedRec);
                        repaint();
                        mainPanel.revalidate();
                    }
                }
            }
        };
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    @Override
    public void invalidate() {
        rectangles.clear();
        super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int sizeA = 10;
        int sizeB = 30;

        int cellWidth = 40;

        if (rectangles.isEmpty()) {
            for (int row = 0; row < columnCount; row++) {
                for (int col = 0; col < columnCount; col++) {
                    MyRectangle cell = new MyRectangle(
                            (cellWidth * col) + 10, (row * sizeB) + row * 10,
                            sizeB, sizeA);
                    rectangles.add(cell);
                    cell = new MyRectangle(
                            (col * cellWidth), (row * sizeB) + 10 * (row + 1),
                            sizeA, sizeB);
                    rectangles.add(cell);
                    cell = new MyRectangle(
                            (col * cellWidth) + sizeA, (row * sizeB) + sizeA + 10 * row,
                            30, 30);
                    cells.add(cell);
                }
            }
        }

        g2d.setColor(model.getMyColor());
        for (MyRectangle cell : mySelectedRectangles) {
            g2d.fill(cell);
        }
        for (MyRectangle cell : mySelectedCells) {
            g2d.fill(cell);
        }
        if (model.getMyColor().equals(Color.BLUE)) {
            g2d.setColor(Color.RED);
        } else g2d.setColor(Color.BLUE);

        for (MyRectangle cell : otherSelectedRectangles) {
            g2d.fill(cell);
        }
        for (MyRectangle cell : otherSelectedCells) {
            g2d.fill(cell);
        }

        g2d.setColor(Color.GRAY);
        for (MyRectangle cell : rectangles) {
            g2d.draw(cell);
        }
        for (MyRectangle cell : cells) {
            g2d.draw(cell);
        }

        if(selectedRec!=null){
            g2d.fill(rectangles.get(selectedRec.getCellIndex(columnCount)));
        }
        g2d.dispose();
    }

    private int checkCell(Move move, Boolean isMy) {
        int index1, index2, index3, index;
        int row = move.getRow();
        int col = move.getCol();

        if (move.getDirection() == 0) {
            index1 = (row) * 2 + ((col - 1) * columnCount * 2);
            index2 = (row) * 2 + ((col - 1) * columnCount * 2) + 1;
            index3 = (row + 1) * 2 + ((col - 1) * columnCount * 2) + 1;
            index = addCell(index1, index2, index3, row, col, 1);
            if (index != -1)
                return index;

            index1 = (row) * 2 + (col * columnCount * 2) + 1;
            index2 = (row) * 2 + ((col + 1) * columnCount * 2);
            index3 = (row + 1) * 2 + (col * columnCount * 2) + 1;
            index = addCell(index1, index2, index3, row, col, 3);
            return index;

        } else {
            index2 = (row) * 2 + ((col + 1) * columnCount * 2);
            index1 = (row) * 2 + (col * columnCount * 2);
            index3 = (row + 1) * 2 + ((col) * columnCount * 2) + 1;
            index = addCell(index1, index2, index3, row, col, 3);
            if (index != -1)
                return index;

            index1 = (row - 1) * 2 + ((col + 1) * columnCount * 2);
            index2 = (row - 1) * 2 + ((col) * columnCount * 2);
            index3 = (row - 1) * 2 + ((col) * columnCount * 2) + 1;
            index = addCell(index1, index2, index3, row, col, 2);
            return index;
        }
    }

    private int addCell(int index1, int index2, int index3, int row, int col, int side) {
        if (rectangles.get(index1).isColored &&
                rectangles.get(index2).isColored &&
                rectangles.get(index3).isColored) {
            int index = -1;
            switch (side) {
                case 1: // up
                    index = row + ((col - 1) * columnCount);
                    break;
                case 2: // left
                    index = row - 1 + (col * columnCount);
                    break;
                case 3: // down. right
                    index = row + (col * columnCount);
                    break;
            }
            MyRectangle cell = cells.get(index);
            if (index >= 0)
                model.getCells().add(new Cell(index, model.getMyNum()));
            mySelectedCells.add(cell);
            return index;
        }
        return -1;
    }


    private void addSelectedRectangle(Move move, Boolean my) {
        int index = move.getCellIndex(columnCount);
        MyRectangle cell = rectangles.get(index);
        cell.isColored = true;
        if (my) mySelectedRectangles.add(cell);
        else otherSelectedRectangles.add(cell);
    }

    @Override
    public void update() {

        if ((model.getLastMove().getClientNum() == model.getMyNum())) {
            addSelectedRectangle(model.getLastMove(), true);
        } else {
            addSelectedRectangle(model.getLastMove(), false);
        }

        model.getCells().forEach(cell -> {
            if (cell.getSide() == model.getMyNum()) {
                mySelectedCells.add(cells.get(cell.getIndex()));
            } else {
                otherSelectedCells.add(cells.get(cell.getIndex()));
            }
        });
        repaint();
    }

    class MyRectangle extends Rectangle {
        Boolean isColored;

        MyRectangle(int x, int y, int width, int height) {
            super(x, y, width, height);
            isColored = false;
        }
    }
}
