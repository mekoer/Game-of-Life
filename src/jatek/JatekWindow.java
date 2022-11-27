package jatek;

import javax.swing.*;
import java.awt.*;

/**
 * A játéktábla ablakának osztálya
 */
public class JatekWindow extends JFrame {
    private TablaPanel jatekter;
    private int hor;
    private int ver;

    /**
     * Konstruktor, konfigurálja az ablak beállításait és létrehoz benne egy TablaPanelt. JFrameként viselkedik.
     * @param h a cellák száma vízszintesen
     * @param v a cellák száma függőlegesen
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

    /**
     * Visszaadja a tartalmazott TablaPanelt.
     * @return a tartalmazott TablaPanel
     */
    public TablaPanel getJatekter() {
        return jatekter;
    }
}
