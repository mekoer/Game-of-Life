package jatek;

import org.junit.Assert;
import org.junit.Test;

/**
 * Teszt osztály a JatekWindow osztályhoz.
 */
public class JatekWindowTest {

    /**
     * Teszteli, hogy konstruktorhívás esetén helyesen jön e létre az ablak.
     */
    @Test
    public void jatekAblakTest() {
        JatekWindow test = new JatekWindow(25, 25);
        Assert.assertNotNull(test);
        Assert.assertNotNull(test.getJatekter());
        Assert.assertEquals(375, test.getJatekter().getWidth());
        Assert.assertEquals(375, test.getJatekter().getHeight());
    }
}