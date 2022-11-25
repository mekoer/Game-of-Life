package jatek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;


/**
 * A játéktér ablakában elhelyezett panel, erre rajzolja ki a program az cellákat, JPanelként viselkedik.
 */
public class TablaPanel extends JPanel {
    private ArrayList<ArrayList<Cell>> State;
    int hor;
    int ver;
    int cellSize;
    Timer timer;
    int timerDelay;
    boolean paused;

    /**
     * konstruktor, inicializálja a tagváltozókat és beállítja a panel alaptulajdonságait.
     * @param h a cellák száma vízszintesen
     * @param v a cellák száma függőlegesen
     */
    public TablaPanel(int h, int v) {
        hor = h;
        ver = v;
        paused = true;
        timerDelay = 1000;
        cellSize = 15;
        timer = new Timer(timerDelay, new Simulation());

        // initial tablaallapot: minden cella halott
        State = new ArrayList<>();
        for (int i = 0; i < hor; i++) {
            ArrayList<Cell> column = new ArrayList<>();
            for (int j = 0; j < ver; j++) {
                column.add(new Cell(false, i, j));
            }
            State.add(column);
        }

        this.addMouseListener(new MouseButtonListener());

        setPreferredSize(new Dimension(hor*cellSize, ver*cellSize));
        setOpaque(false);
    }

    /**
     * Az egérrel végzett akciókért felelős Listener, itt található a logika ami a játéktábla editálásáért felel.
     */
    private class MouseButtonListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int h = e.getX() / cellSize;
            int v = e.getY() / cellSize;
            if (e.getButton() == MouseEvent.BUTTON1) {
                State.get(h).get(v).setAlive(true);
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {
                State.get(h).get(v).setAlive(false);
            }
            stateRefresh();
            repaint();
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    /**
     * A szimuláció futtatásáért felelős Listener, a Timer ezt kaphatja paraméterül.
     */
    private class Simulation implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            lep();
        }
    }

    /**
     *Egy lépés végrehajtása.
     */
    public void lep() {
        stateRefresh();
        nextState();
        repaint();
    }

    /**
     * A szimuláció indításáért és leállításáért felelős logika.
     */
    public void controlSim() {
        if (paused) {
            timer.start();
            paused = false;
        }
        else {
            timer.stop();
            paused = true;
        }
    }

    /**
     * A futás sebességét szabályozó logika.
     * @param time erre az értékre állítja át a timert
     */
    public void refreshTimer(int time) {
        timerDelay = time;
        timer.stop();
        timer.setDelay(time);
        timer.start();
    }

    /**
     * Szerializálja a State tagváltozót.
     * @throws IOException
     */
    public void save() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.txt"));
        oos.writeObject(State);
        oos.close();
    }

    /**
     * Visszatölti a szerializált állapotot,
     * ha a játéktábla nagyobb lett közben, a közepébe helyezi el, ha kisebb lett lavágja a széleket.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.txt"));
        ArrayList<ArrayList<Cell>> temp;
        temp = (ArrayList<ArrayList<Cell>>) in.readObject();

        //int** belerak = empty(meret_x);
        this.kill();
        for(int i = 0; i < temp.size(); ++i) {
            for(int j = 0; j < temp.get(i).size(); ++j) {
                State.get(i + (State.size() - temp.size()) / 2).get(
                        j + (State.get(0).size() - temp.get(0).size()) / 2).setAlive(
                                temp.get(i).get(j).isAlive());
                //belerak[i + ((meret_x - hor) / 2)][j + ((meret_x - ver) / 2)] = input[i][j];
            }
        }

        repaint();
    }

    /**
     * A tábla összes cellájának az állapotát halottra állítja.
     */
    public void kill() {
        for (ArrayList<Cell> column : State) {
            for (Cell cell : column) {
                cell.setAlive(false);
            }
        }
    }

    /**
     * A cellák rajzolásáért felelős függvény.
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // fekete background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, hor*cellSize, ver*cellSize);

        // negyzetek rajzolasa
        for (int i = 0; i < hor; i++) {
            for (int j = 0; j < ver; j++) {
                if (this.getAt(i, j).isAlive()) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
                }
                else if (!this.getAt(i, j).isAlive()) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
                }
            }
        }

        // elvalasztovonalak, hogy szebb legyen
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < hor+1; i++) {
            g2d.drawLine(i*cellSize, 0, i*cellSize, ver*cellSize);
        }
        for (int j = 0; j < ver+1; j++) {
            g2d.drawLine(0, j*cellSize, hor*cellSize, j*cellSize);
        }
    }

    /**
     * Visszaadja a tárolóból a (h, v) helyen található cellát.
     * @param h a cellák száma vízszintesen
     * @param v a cellák száma függőlegesen
     * @return Cell objektum a (h, v) által megadott pozícióból
     */
    Cell getAt(int h, int v) {
        return State.get(h).get(v);
    }

    /**
     * Kiszámolja a paraméterül kapott cella élő szomszádainak számát
     * és átálltja a cella aliveNear attribútumát a megfelelő értékre.
     * @param cell a cella aminek meg akarjuk számolni a szomszédait
     */
    public void calculateAliveNear(Cell cell) {
        // cella es az ot korulvevo cellak koordinatai
        // x-1,y-1 | x,y-1 | x+1,y-1
        //  x-1,y  |  x,y  |  x+1,y
        // x-1,y+1 | x,y+1 | x+1,y+1

        int aliveNear = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                // a parameterben megadott cella, nem tortenik semmi
                //noinspection StatementWithEmptyBody
                if (i == 0 && j == 0) {
                    //semmi
                }
                // ha eletben van az adott szomszed, inkrementaljuk
                else if (this.getAt(i + cell.getHor(), j + cell.getVer()).isAlive()) {
                    aliveNear++;
                }
            }
        }
        cell.setAliveNear(aliveNear);
    }

    /**
     * Az összes tárolt cellán kiszámolja az élő szomszédok számát.
     */
    public void stateRefresh() {
        for (int i = 1; i < hor - 1; i++) {
            for (int j = 1; j < ver - 1; j++) {
                this.calculateAliveNear(this.getAt(i, j));
            }
        }
    }

    /**
     * Kiszámolja a tábla következő állapotát.
     */
    public void nextState() {
        ArrayList<ArrayList<Cell>> temp = new ArrayList<>(State);
        for (ArrayList<Cell> column : temp) {
            for (Cell cell : column) {
                if (cell.isAlive() && (cell.getAliveNear() > cell.getMoreThan()
                        || cell.getAliveNear() < cell.getLessThan())) {
                    this.getAt(cell.getHor(), cell.getVer()).setAlive(false);
                }
                if (!cell.isAlive() && cell.getAliveNear() == cell.getForBirth()) {
                    this.getAt(cell.getHor(), cell.getVer()).setAlive(true);
                }
            }
        }
        State = temp;
    }

    /**
     * Beállítja minden cellán, hogy minimum hány élő szomszéd szükséges az életben maradásához.
     * @param lessT Ha a cellának ennél kevesebb szomszédja van, meghal.
     */
    public void setLessToDie(int lessT) {
        for (ArrayList<Cell> column : State) {
            for (Cell cell : column) {
                cell.setLessThan(lessT);
            }
        }
    }

    /**
     * Beállítja minden cellán, hogy maximum hány élő szomszéd szükséges az életben maradásához.
     * @param moreT Ha a cellának ennél több élő szomszédja van, meghal.
     */
    public void setMoreToDie(int moreT) {
        for (ArrayList<Cell> column : State) {
            for (Cell cell : column) {
                cell.setMoreThan(moreT);
            }
        }
    }

    /**
     * Beállítja minden cellán, hogy maximum hány élő szomszéd szükséges az újjászületéshez.
     * @param toB Ha egy halott cellának pontosan ennyi szomszédja van, újjászületik.
     */
    public void setToBirth(int toB) {
        for (ArrayList<Cell> column : State) {
            for (Cell cell : column) {
                cell.setForBirth(toB);
            }
        }
    }
}