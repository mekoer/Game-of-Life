package jatek;

import com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Game of Life");
        this.setResizable(true);
        this.setPreferredSize(new Dimension(1920, 1080));
        this.setLayout(new BorderLayout());

        // nagy tablapanel, scrollolhato
        TablaPanel jatekPanel = new TablaPanel(1000, 1000);
        JScrollPane panel1 = new JScrollPane(jatekPanel);
        this.add(panel1, BorderLayout.CENTER);

        // gombok
        JMenuBar menu = new JMenuBar();

        JButton playpauseB = new JButton("> / II");
        playpauseB.addActionListener(e -> jatekPanel.controlSim());

        JButton step = new JButton("Step Once");
        step.addActionListener(e -> jatekPanel.lep());

        JButton onex = new JButton("1x");
        onex.addActionListener(e -> jatekPanel.refreshTimer(1000));

        JButton twox = new JButton("2x");
        twox.addActionListener(e -> jatekPanel.refreshTimer(500));

        JButton fourx = new JButton("4x");
        fourx.addActionListener(e -> jatekPanel.refreshTimer(250));

        JButton eightx = new JButton("8x");
        eightx.addActionListener(e -> jatekPanel.refreshTimer(125));

        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            jatekPanel.getTabla().kill();
            repaint();
        });

        JPanel magnifySliderPanel = new JPanel();
        JSlider magnifySlider = new JSlider(JSlider.VERTICAL, 1, 10, 1);

        magnifySlider.addChangeListener(e -> jatekPanel.zoom(magnifySlider.getValue()));

        magnifySliderPanel.add(magnifySlider);
        magnifySliderPanel.setPreferredSize(new Dimension(20, 600));

        menu.add(playpauseB);
        menu.add(step);
        menu.add(onex);
        menu.add(twox);
        menu.add(fourx);
        menu.add(eightx);
        menu.add(clear);

        this.add(magnifySliderPanel, BorderLayout.WEST);

        this.add(menu, BorderLayout.NORTH);

        this.pack();
        this.setVisible(true);
    }
}
