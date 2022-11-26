package jatek;

import java.io.Serializable;

/**
 * Egy cellát és az arra vonatkozó adatokat csoportosítja egy osztályba.
 */
public class Cell implements Serializable {
    private boolean alive;
    private int aliveNear;
    private int lessThan; // (ha él) ennél kevesebb szomszéd alive szomszéd -> halál
    private int moreThan; // (ha él) ennél több alive szomszéd -> halál
    private int forBirth; // (ha nem él) ennyi élő szomszéd -> szuletik
    private final int coordHor;
    private final int coordVer;

    /**
     * Konstruktor, beállítja a cellára vonatkozó alapszabályokat és adatokat.
     * @param isAlive életben van-e a cella
     * @param hor cella vízszintes koordinátája
     * @param ver cella függőleges koordinátája
     */
    public Cell(boolean isAlive, int hor, int ver) {
        alive = isAlive;
        lessThan = 2; // alapszabalyok
        moreThan = 3;
        forBirth = 3;
        coordHor = hor;
        coordVer = ver;
    }

    // getter, setter
    public boolean isAlive() { return alive; }
    public int getLessThan() { return lessThan; }
    public int getMoreThan() { return moreThan; }
    public int getForBirth() { return forBirth; }
    public int getHor() { return coordHor; }
    public int getVer() { return coordVer; }
    public int getAliveNear() { return aliveNear; }

    public void setAlive(boolean alive) { this.alive = alive; }
    public void setLessThan(int lessThan) { this.lessThan = lessThan; }
    public void setMoreThan(int moreThan) { this.moreThan = moreThan; }
    public void setForBirth(int forBirth) { this.forBirth = forBirth; }
    public void setAliveNear(int aliveNear) { this.aliveNear = aliveNear; }



}
