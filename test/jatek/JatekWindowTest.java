package jatek;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class JatekWindowTest {
    @Test
    public void jatekAblakTest() {
        JatekWindow test = new JatekWindow(25, 25);
        Assert.assertNotNull(test);
        Assert.assertNotNull(test.getJatekter());
        Assert.assertEquals(375, test.getJatekter().getWidth());
        Assert.assertEquals(375, test.getJatekter().getHeight());
    }
}