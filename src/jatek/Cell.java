package jatek;

import java.io.Serializable;

public class Cell implements Serializable {
    boolean alive;
    int aliveNear;
    int lessThan; // (ha él) ennél kevesebb szomszéd alive szomszéd -> halál
    int moreThan; // (ha él) ennél több alive szomszéd -> halál
    int forBirth; // (ha nem él) ennyi élő szomszéd -> szuletik
    int coordHor;
    int coordVer;

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
