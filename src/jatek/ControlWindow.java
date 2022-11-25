package jatek;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;

public class ControlWindow extends JFrame {
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;
    JPanel panel6;
    JPanel panel7;
    JatekWindow ablak;
    boolean paused;

    public ControlWindow() {
        paused = true;
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Game of Life");
        Image icon = new ImageIcon("icon.png").getImage();
        this.setIconImage(icon);
        this.setLayout(new GridLayout(7, 1));
        panel1Setup();
        panel2Setup();
        panel3Setup();
        panel456Setup();
        panel7Setup();
        this.pack();
        this.setVisible(true);
    }

    public void panel1Setup() {
        panel1 = new JPanel();
        panel1.setBackground(Color.BLACK);
        panel1.setLayout(new FlowLayout());

        // szelesseg textbox es label
        JLabel HLabel = new JLabel("Width:");
        JTextField horizontal = new JTextField("25", 3);

        // magassag textbox es label
        JLabel VLabel = new JLabel("Height:");
        JTextField vertical = new JTextField("25", 3);

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
    public void panel2Setup() {
        // play pause gomb
        JLabel playpauseL = new JLabel("Play / Pause");
        JButton playpauseB = new JButton("> / II");
        playpauseB.addActionListener(e -> ablak.getJatekter().controlSim());

        JButton step = new JButton("Step Once");
        step.addActionListener(e -> ablak.getJatekter().lep());

        panel2 = new JPanel(new FlowLayout());
        panel2.setBackground(Color.BLACK);
        panel2.add(playpauseL);
        panel2.add(playpauseB);
        panel2.add(step);

        this.add(panel2);
    }
    public void panel3Setup() {
        // speed control
        JLabel seb = new JLabel("Sebesség");

        JButton onex = new JButton("1x");
        onex.addActionListener(e -> ablak.getJatekter().refreshTimer(1000));

        JButton twox = new JButton("2x");
        twox.addActionListener(e -> ablak.getJatekter().refreshTimer(500));

        JButton fourx = new JButton("4x");
        fourx.addActionListener(e -> ablak.getJatekter().refreshTimer(250));

        panel3 = new JPanel(new FlowLayout());
        panel3.setBackground(Color.BLACK);

        panel3.add(seb);
        panel3.add(onex);
        panel3.add(twox);
        panel3.add(fourx);

        this.add(panel3);
    }
    public void panel456Setup() {
        JLabel lessLabel = new JLabel("minimalis szamu elo szomszed az eletben maradashoz");
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
                    ablak.getJatekter().setLessToDie(Integer.parseInt(lessThanToDie.getText()));
                }
            }
        });

        JLabel moreLabel = new JLabel("maximalis szamu elo szomszed az eletben maradashoz");
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
                    ablak.getJatekter().setMoreToDie(Integer.parseInt(moreThanToDie.getText()));
                }
            }
        });

        JLabel birthLabel = new JLabel("szukséges számú elo szomszed az ujjaszuleteshez");
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
                    ablak.getJatekter().setToBirth(Integer.parseInt(toBirth.getText()));
                }
            }
        });

        panel4 = new JPanel();
        panel4.setBackground(Color.BLACK);
        panel4.add(lessLabel);
        panel4.add(lessThanToDie);

        panel5 = new JPanel();
        panel5.setBackground(Color.BLACK);
        panel5.add(moreLabel);
        panel5.add(moreThanToDie);

        panel6 = new JPanel();
        panel6.setBackground(Color.BLACK);
        panel6.add(birthLabel);
        panel6.add(toBirth);

        this.add(panel4);
        this.add(panel5);
        this.add(panel6);
    }
    public void panel7Setup() {
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            try {
                ablak.getJatekter().save();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton load = new JButton("Load");
        load.addActionListener(e -> {
            try {
                ablak.getJatekter().load();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        panel7 = new JPanel();
        panel7.setBackground(Color.BLACK);
        panel7.add(save);
        panel7.add(load);
        this.add(panel7);
    }
}

