package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
//
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;


public class CasaDeBurritoTest {
    private Profesor p1;
    private Profesor p2;
    private Profesor p3SameIDp2;
    private Profesor p4;

    private CasaDeBurrito c1;
    private CasaDeBurrito c2;
    private CasaDeBurrito c3SameIDc2;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        p1 = new ProfesorImpl(1, "p1");
        p2 = new ProfesorImpl(2, "p2");

        // same ID as p2
        p3SameIDp2 = new ProfesorImpl(2, "p3");

        p4 = new ProfesorImpl(4, "p4");

        c1 = new CasaDeBurritoImpl(11, "c1", 11, Stream.of("c1-item1", "c2-item2").collect(toSet()));
        c2 = new CasaDeBurritoImpl(22, "c2", 22, Stream.of("c2-item1", "c2-item2", "c2-item3").collect(toSet()));

        // Same id as c1
        c3SameIDc2 = new CasaDeBurritoImpl(22, "c3", 33, Stream.of("c3-item1", "c3-item2", "c3-item3").collect(toSet()));
    }

    @Test
    public void getIdTest() {
        assertEquals(c1.getId(), 11);
        assertEquals(c2.getId(), 22);
        assertEquals(c3SameIDc2.getId(), 22);
    }

    @Test
    public void getNameTest() {
        assertEquals(c1.getName(), "c1");
        assertEquals(c2.getName(), "c2");
        assertEquals(c3SameIDc2.getName(), "c3");
    }

    @Test
    public void distanceTest() {
        assertEquals(c1.distance(), 11);
        assertEquals(c2.distance(), 22);
        assertEquals(c3SameIDc2.distance(), 33);
    }

    @Test
    public void isRatedByTest() {
        assertFalse(c1.isRatedBy(p1));

        // Can profesor be null?
        assertFalse(c1.isRatedBy(null));
    }

    @Test
    public void rateTestExceptionLow() throws CasaDeBurrito.RateRangeException {
        exceptionRule.expect(CasaDeBurrito.RateRangeException.class);
        c1.rate(p1, -1);
    }

    @Test
    public void rateTestExceptionHigh() throws CasaDeBurrito.RateRangeException {
        exceptionRule.expect(CasaDeBurrito.RateRangeException.class);
        c1.rate(p1, 6);
    }

    @Test
    public void ratesTest() throws CasaDeBurrito.RateRangeException {
        // Single rate
        c1.rate(p1,0);
        assertTrue(c1.isRatedBy(p1));
        assertFalse(c1.isRatedBy(p2));
        assertEquals(c1.numberOfRates(),1);
        assertEquals(0.0, c1.averageRating(),0);

        // Two Rates
        c1.rate(p2,5);
        assertTrue(c1.isRatedBy(p2));

        // Rate with same ID
        assertEquals(p2, p3SameIDp2);
        assertTrue(c1.isRatedBy(p3SameIDp2));

        assertEquals(c1.numberOfRates(),2);
        // (5+0)/2 = 2.5
        assertEquals(2.5, c1.averageRating(),0);

        // Same id, change rate from 5 to 4.
        c1.rate(p3SameIDp2,4);
        assertEquals(c1.numberOfRates(),2);
        // (4+0)/2 = 2
        assertEquals(2.0, c1.averageRating(),0);


        c1.rate(p4,3);
        assertEquals(c1.numberOfRates(),3);
        // (4+3+0)/3=2.3333
        assertEquals(2.3333, c1.averageRating(),0.5);
    }

    @Test
    public void equalsTest() {
        assertNotEquals(c1, c2);
        assertEquals(c2,c3SameIDc2);
    }

    @Test
    public void compareToTest() {
        assertTrue(c1.compareTo(c2) < 0);
        assertEquals(0, c2.compareTo(c3SameIDc2));
        assertTrue(c2.compareTo(c1) > 0);
    }
}