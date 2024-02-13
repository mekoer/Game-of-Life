package jatek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * A játéktér ablakában elhelyezett panel, erre rajzolja ki a program az cellákat, JPanelként viselkedik.
 */
public class TablaPanel extends JPanel {
    private final Tabla tabla;
    private final int VisibleHor;
    private final int VisibleVer;
    int padding = 50;
    private int drawnCellSize = 10;
    private final Timer timer;
    private int timerDelay = 1000;
    private boolean paused = true;

    /**
     * konstruktor, inicializálja a tagváltozókat és beállítja a panel alaptulajdonságait.
     * @param h a cellák száma vízszintesen
     * @param v a cellák száma függőlegesen
     */
    public TablaPanel(int h, int v) {
        VisibleHor = h;
        VisibleVer = v;
        int actualHor = h + padding * 2;
        int actualVer = v + padding * 2;
        timer = new Timer(timerDelay, new Simulation());

        tabla = new Tabla(actualHor, actualVer);

        this.addMouseListener(new MouseButtonListener());
        this.addMouseMotionListener(new MouseMotionListener());

        setPreferredSize(new Dimension(VisibleHor * drawnCellSize, VisibleVer * drawnCellSize));
        setOpaque(false);
    }

    /**
     * Zoomolást megvalósító logika
     * @param sliderValue a zoom slider értéke
     */
    public void zoom(int sliderValue) {
        int baseCellSize = 10;
        drawnCellSize = baseCellSize * sliderValue;
        repaint();
    }

    /**
     * Az egérkattintásokat figyelő Listener, itt található a logika ami a játéktábla editálásáért felel.
     */
    private class MouseButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int h = (e.getX() / drawnCellSize) + padding;
            int v = (e.getY() / drawnCellSize) + padding;
            if (e.getButton() == MouseEvent.BUTTON1) {
                tabla.getAt(h, v).setAlive(true);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                tabla.getAt(h, v).setAlive(false);
            }
            tabla.stateRefresh();
            repaint();
        }
    }

    /**
     * Az egérmozgásokat figyelő Listener, itt található a logika ami a játéktábla editálásáért felel.
     */
    private class MouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            int h = (e.getX() / drawnCellSize) + padding;
            int v = (e.getY() / drawnCellSize) + padding;
            if (isLeftMouseButton(e)) {
                tabla.getAt(h, v).setAlive(true);
            } else if (isRightMouseButton(e)) {
                tabla.getAt(h, v).setAlive(false);
            }
            tabla.stateRefresh();
            repaint();
        }
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
        tabla.stateRefresh();
        tabla.nextState();
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
     * A cellák rajzolásáért felelős függvény. Graphics2D felhasználásával rajzol fehér négyzeteket az élő cellák pozíciójára,
     * majd az egész tábla fölé egy négyzetrácsot, hogy jobban elkülöníthetőek legyenek a cellák.
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // fekete background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, VisibleHor * drawnCellSize, VisibleVer * drawnCellSize);

        // negyzetek rajzolasa
        for (int i = 0; i < VisibleHor; i++) {
            for (int j = 0; j < VisibleVer; j++) {
                if (tabla.getAt(i + padding, j + padding).isAlive()) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(i* drawnCellSize, j* drawnCellSize, drawnCellSize, drawnCellSize);
                }
                else if (!tabla.getAt(i + padding, j + padding).isAlive()) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(i* drawnCellSize, j* drawnCellSize, drawnCellSize, drawnCellSize);
                }
            }
        }

        // elvalasztovonalak, hogy szebb legyen
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < VisibleHor + 1; i++) {
            g2d.drawLine(i* drawnCellSize, 0, i* drawnCellSize, VisibleVer * drawnCellSize);
        }
        for (int j = 0; j < VisibleVer + 1; j++) {
            g2d.drawLine(0, j* drawnCellSize, VisibleHor * drawnCellSize, j* drawnCellSize);
        }
    }

    public void kill() {

    }

    public Tabla getTabla() {
        return tabla;
    }
    public boolean getPaused() { return paused; }
    public Timer getTimer() { return timer; }
}