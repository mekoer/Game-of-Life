package jatek;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tesztosztály a TablaPanel osztályhoz.
 */
public class TablaPanelTest {
    TablaPanel panelTest;

    @Before
    public void setUp() {
        panelTest = new TablaPanel(25, 25);
    }

    /**
     * A controlSim metódus helyes működését teszteli.
     */
    @Test
    public void controlSim() {
        assertTrue(panelTest.getPaused());
        panelTest.controlSim();
        assertFalse(panelTest.getPaused());
        panelTest.controlSim();
        assertTrue(panelTest.getPaused());
    }

    /**
     * A timer delay értékér frissítő refreshTimer metódus helyes működését teszteli.
     */
    @Test
    public void refreshTimer() {
        panelTest.refreshTimer(250);
        Assert.assertEquals(250, panelTest.getTimer().getDelay());
        panelTest.refreshTimer(500);
        Assert.assertEquals(500, panelTest.getTimer().getDelay());
    }
}