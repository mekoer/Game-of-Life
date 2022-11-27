package jatek;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Tabla {
    private final int ActualHor;
    private final int ActualVer;
    private ArrayList<ArrayList<Cell>> State;

    public Tabla(int actualHor, int actualVer) {
        ActualHor = actualHor;
        ActualVer = actualVer;

        // initial tablaallapot: minden cella halott
        State = new ArrayList<>();
        for (int i = 0; i < ActualHor; i++) {
            ArrayList<Cell> column = new ArrayList<>();
            for (int j = 0; j < ActualVer; j++) {
                column.add(new Cell(false, i, j));
            }
            State.add(column);
        }
    }

    /**
     * Visszaadja a tárolóból a (h, v) helyen található cellát.
     * @param h a keresett cella vízszintes koordinátája
     * @param v a keresett cella függőleges koordinátája
     * @return Cell objektum a (h, v) által megadott pozícióból
     */
    public Cell getAt(int h, int v) {
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
        for (int i = 1; i < ActualHor - 1; i++) {
            for (int j = 1; j < ActualVer - 1; j++) {
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
     * Szerializálja a State tagváltozót.
     * @throws IOException
     */
    public void save() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.txt"));
        oos.writeObject(State);
        oos.close();
    }

    /**
     * Visszatölti a szerializált állapotot, ha éppen meg van nyitva egy játékablak, akkor
     * ha a játéktábla nagyobb lett közben, a közepébe helyezi el, ha kisebb lett lavágja a széleket.
     * Ha nincs megnyitva játékablak, de van mentett állás, akkor megnyit egy alapértelmezett méretű ablakot
     * és ebbe tölti bele a mentett mintát.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("save.txt"));
        ArrayList<ArrayList<Cell>> temp;
        temp = (ArrayList<ArrayList<Cell>>) in.readObject();

        // ha kisebb palyaba toltunk nagyobb mintat
        if (temp.size() > ActualHor || temp.get(0).size() > ActualVer) {
            this.kill();
            for(int i = 0; i < ActualHor; ++i) {
                for(int j = 0; j < ActualVer; ++j) {
                    State.get(i).get(j).setAlive(
                            temp.get(i + (temp.size() - ActualHor) / 2).get(j + (temp.get(0).size() - ActualVer) / 2).isAlive());
                }
            }
        }
        // ha nagyobb palyaba toltunk kisebb mintat
        else {
            //int** belerak = empty(meret_x); <- régi saját kódom
            this.kill();
            for(int i = 0; i < temp.size(); ++i) {
                for(int j = 0; j < temp.get(i).size(); ++j) {
                    State.get(i + (State.size() - temp.size()) / 2).get(
                            j + (State.get(0).size() - temp.get(0).size()) / 2).setAlive(
                            temp.get(i).get(j).isAlive());
                    //belerak[i + ((meret_x - hor) / 2)][j + ((meret_x - ver) / 2)] = input[i][j]; <- régi saját kódom
                }
            }
        }


        in.close();
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

    public ArrayList<ArrayList<Cell>> getState() {
        return State;
    }
}
