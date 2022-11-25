package jatek;

import javax.swing.*;
import java.awt.*;

/**
 * A játéktábla ablakának osztálya
 */
public class JatekWindow extends JFrame {
    TablaPanel jatekter;
    int hor;
    int ver;

    /**
     * @param h
     * @param v
     */
    public JatekWindow(int h, int v) {
        hor = h;
        ver = v;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        jatekter = new TablaPanel(hor, ver); // ebben lesznek a cellak

        this.add(jatekter);
        this.setTitle("Game of Life");
        Image icon = new ImageIcon("icon.png").getImage();
        this.setIconImage(icon);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    public TablaPanel getJatekter() {
        return jatekter;
    }
}
