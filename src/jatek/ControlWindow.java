package jatek;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;

/**
 * Az irányítópanel ablaka.
 */
public class ControlWindow extends JFrame {
    private JTextField horizontal;
    private JTextField vertical;
    private JatekWindow ablak;

    /**
     * Konstruktor, konfigurálja az ablakot és meghívja a tartalmazott panelek setup függvényeit.
     */
    public ControlWindow() {
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Game of Life");
        Image icon = new ImageIcon("icon.png").getImage();
        this.setIconImage(icon);
        this.setLayout(new GridLayout(7, 1));
        panel1Setup();
        panel2Setup();
        panel3Setup();
        panel4Setup();
        panel5Setup();
        panel6Setup();
        panel7Setup();
        this.pack();
        this.setVisible(true);
    }

    /**
     * Az első panelt rakja össze. Tartalmaz két JTextFieldet és egy JButton gombot,
     * ezekket vezérelhető hogy mekkora méretű játékablakot nyitunk.
     */
    public void panel1Setup() {
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.BLACK);
        panel1.setLayout(new FlowLayout());

        // szelesseg textbox es label
        JLabel HLabel = new JLabel("Width: ");
        horizontal = new JTextField("25", 3);

        // magassag textbox es label
        JLabel VLabel = new JLabel("Height: ");
        vertical = new JTextField("25", 3);

        // inditogomb es listener
        JButton generate = new JButton("Generate");
        generate.addActionListener(e -> {
            if (ablak != null) {
                ablak.dispose();
            }
            ablak = new JatekWindow(Integer.parseInt(horizontal.getText()),
                    Integer.parseInt(vertical.getText()));
        });

        panel1.add(HLabel);
        panel1.add(horizontal);
        panel1.add(VLabel);
        panel1.add(vertical);
        panel1.add(generate);

        this.add(panel1);
    }

    /**
     * A második panelt rakja össze. Két JButtont tartalmaz, az egyik a szimulációt indítja és állítja meg, a másik egyszer lépteti a szimulációt.
     */
    public void panel2Setup() {
        // play pause gomb
        JLabel playpauseL = new JLabel("Play / Pause: ");
        JButton playpauseB = new JButton("> / II");
        playpauseB.addActionListener(e -> ablak.getJatekter().controlSim());

        JButton step = new JButton("Step Once");
        step.addActionListener(e -> ablak.getJatekter().lep());

        JPanel panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Color.BLACK);
        panel2.add(playpauseL);
        panel2.add(playpauseB);
        panel2.add(step);

        this.add(panel2);
    }

    /**
     * A harmadik panelt rakja össze. Négy JButtont tartalmaz, ezekkel a szimuláció sebessége szabályozható.
     * Az alapbeállítás 1 lépés/sec, ehhez képest mehet 2x, 4x vagy 8x gyorsabban.
     */
    public void panel3Setup() {
        // speed control
        JLabel seb = new JLabel("Sebesség: ");

        JButton onex = new JButton("1x");
        onex.addActionListener(e -> ablak.getJatekter().refreshTimer(1000));

        JButton twox = new JButton("2x");
        twox.addActionListener(e -> ablak.getJatekter().refreshTimer(500));

        JButton fourx = new JButton("4x");
        fourx.addActionListener(e -> ablak.getJatekter().refreshTimer(250));

        JButton eightx = new JButton("8x");
        eightx.addActionListener(e -> ablak.getJatekter().refreshTimer(125));

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(Color.BLACK);

        panel3.add(seb);
        panel3.add(onex);
        panel3.add(twox);
        panel3.add(fourx);
        panel3.add(eightx);

        this.add(panel3);
    }

    /**
     * A negyedik panelt rakja össze. Az itt található JTextFeieldben állítható,
     * hogy mi legyen a cella életben maradásához szükséges minimális számú élő szomszéd.
     */
    public void panel4Setup() {
        JLabel lessLabel = new JLabel("Minimális számú élő szomszéd az életben maradáshoz: ");
        JTextField lessThanToDie = new JTextField("2", 3);
        lessThanToDie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                doThis();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                doThis();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                doThis();
            }
            public void doThis() {
                if (!lessThanToDie.getText().equals("")) {
                    ablak.getJatekter().getTabla().setLessToDie(Integer.parseInt(lessThanToDie.getText()));
                }
            }
        });

        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.BLACK);
        panel4.add(lessLabel);
        panel4.add(lessThanToDie);

        this.add(panel4);
    }

    /**
     * Az ötödik panelt rakja össze. Az itt található JTextFeieldben állítható,
     * hogy mi legyen a cella életben maradásához szükséges maximális számú élő szomszéd.
     */
    public void panel5Setup() {
        JLabel moreLabel = new JLabel("Maximális számú élő szomszéd az életben maradáshoz: ");
        JTextField moreThanToDie = new JTextField("3", 3);
        moreThanToDie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                doThis();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                doThis();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                doThis();
            }
            public void doThis() {
                if (!moreThanToDie.getText().equals("")) {
                    ablak.getJatekter().getTabla().setMoreToDie(Integer.parseInt(moreThanToDie.getText()));
                }
            }
        });

        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.BLACK);
        panel5.add(moreLabel);
        panel5.add(moreThanToDie);

        this.add(panel5);
    }

    /**
     * A hatodik panelt rakja össze. Az itt található JTextFeieldben állítható,
     * hogy mi legyen a cella újjászületéséhez szükséges számú élő szomszéd.
     */
    public void panel6Setup() {
        JLabel birthLabel = new JLabel("Szükséges számú élő szomszéd az újjászületéshez: ");
        JTextField toBirth = new JTextField("3", 3);
        toBirth.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                doThis();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                doThis();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                doThis();
            }
            public void doThis() {
                if (!toBirth.getText().equals("")) {
                    ablak.getJatekter().getTabla().setToBirth(Integer.parseInt(toBirth.getText()));
                }
            }
        });

        JPanel panel6 = new JPanel();
        panel6.setBackground(Color.BLACK);
        panel6.add(birthLabel);
        panel6.add(toBirth);

        this.add(panel6);
    }

    /**
     * A hetedik panelt rakja össze. Három JButtont tartalmaz, mentés és kilépés, mentés és töltés funkciókkal.
     */
    public void panel7Setup() {
        JButton saveExit = new JButton("Save&Exit");
        saveExit.addActionListener(e -> {
            try {
                if (ablak != null) {
                    ablak.getJatekter().getTabla().save();
                    System.exit(1);
                }
                else {
                    System.exit(1);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            try {
                if (ablak == null) {
                    // semmi
                }
                else {
                    ablak.getJatekter().getTabla().save();
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton load = new JButton("Load");
        load.addActionListener(e -> {
            try {
                if (ablak == null) {
                    ablak = new JatekWindow(Integer.parseInt(horizontal.getText()), Integer.parseInt(vertical.getText()));
                    ablak.getJatekter().getTabla().load();
                    ablak.getJatekter().repaint();
                }
                else {
                    ablak.getJatekter().getTabla().load();
                    ablak.getJatekter().repaint();
                }  
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        JPanel panel7 = new JPanel();
        panel7.setBackground(Color.BLACK);
        panel7.add(saveExit);
        panel7.add(save);
        panel7.add(load);
        this.add(panel7);
    }
}

