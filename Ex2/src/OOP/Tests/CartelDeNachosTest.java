package OOP.Tests;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CartelDeNachosImpl;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptySet;
import static org.junit.Assert.*;


public class CartelDeNachosTest {
    private class PojoClass {
        String name = "1";
        int id = 1;
    }

    private Profesor p1;
    private Profesor p2;
    private Profesor p3;
    private Profesor p4;
    private Profesor p5;
    private Profesor p6;

    private CasaDeBurrito c1;
    private CasaDeBurrito c2;
    private CasaDeBurrito c3;
    private CasaDeBurrito c4;
    private CasaDeBurrito c5;
    private CasaDeBurrito c6;

    private CartelDeNachos sys;
    PojoClass pojo;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        sys = new CartelDeNachosImpl();
        pojo = new PojoClass();
    }

    @Test
    public void joinCartelTest() throws Profesor.ProfesorAlreadyInSystemException {
        assertTrue(sys.registeredProfesores().isEmpty());

        try {
            sys.joinCartel(pojo.id, pojo.name);
            sys.joinCartel(pojo.id, "p1-already-in-system");
            fail();
        } catch (Profesor.ProfesorAlreadyInSystemException ignored) {
        }

        // p1 in system
        assertEquals(1, sys.registeredProfesores().size());
    }

    @Test
    public void addCasaDeBurritoTest() {

        assertTrue(sys.registeredCasasDeBurrito().isEmpty());

        try {
            sys.addCasaDeBurrito(pojo.id, pojo.name, 11, emptySet());
            sys.addCasaDeBurrito(1, "already-exists", 22, emptySet());
        } catch (CasaDeBurrito.CasaDeBurritoAlreadyInSystemException ignored) {
        }

        // c1 in system
        assertEquals(1, sys.registeredCasasDeBurrito().size());
    }

    @Test
    public void registeredProfesoresTest() throws Profesor.ProfesorAlreadyInSystemException {

        assertTrue(sys.registeredProfesores().isEmpty());

        sys.joinCartel(pojo.id, pojo.name);

        Collection<Profesor> willBeChangedSet = sys.registeredProfesores();
        willBeChangedSet.add(new ProfesorImpl(2, "p2"));

        assertNotEquals(willBeChangedSet, sys.registeredProfesores());
        assertEquals(1, sys.registeredProfesores().size());
        assertEquals(2, willBeChangedSet.size());
    }

    @Test
    public void registeredCasasDeBurritoTest() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        assertTrue(sys.registeredCasasDeBurrito().isEmpty());

        sys.addCasaDeBurrito(pojo.id, pojo.name, 11, emptySet());

        Collection<CasaDeBurrito> willBeChangedSet = sys.registeredCasasDeBurrito();
        willBeChangedSet.add(new CasaDeBurritoImpl(2, "c2", 22, emptySet()));

        assertNotEquals(willBeChangedSet, sys.registeredCasasDeBurrito());
        assertEquals(1, sys.registeredCasasDeBurrito().size());
        assertEquals(2, willBeChangedSet.size());
    }

    @Test
    public void getProfesorTest() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException {

        int i = 0;

        try {
            sys.getProfesor(0);
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {
        }

        sys.joinCartel(pojo.id, pojo.name);
        sys.joinCartel(pojo.id + (++i), pojo.name + i);

        assertEquals(1, sys.getProfesor(1).getId());
        assertEquals(2, sys.getProfesor(2).getId());

        assertNotEquals(sys.getProfesor(1), sys.getProfesor(2));
    }

    @Test
    public void getCasaDeBurritoTest() throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException {

        int i = 0;

        try {
            sys.getCasaDeBurrito(0);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException ignored) {
        }
        sys.addCasaDeBurrito(pojo.id, pojo.name, i * 10, emptySet());
        sys.addCasaDeBurrito(pojo.id + (++i), pojo.name + i, i * 10, emptySet());

        assertEquals(1, sys.getCasaDeBurrito(1).getId());
        assertEquals(2, sys.getCasaDeBurrito(2).getId());

        assertNotEquals(sys.getCasaDeBurrito(1), sys.getCasaDeBurrito(2));
    }

    @Test
    public void addConnectionTest() throws Profesor.ProfesorAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {

        p1 = sys.joinCartel(1, "p1");
        p2 = sys.joinCartel(2, "p2");

        ProfesorImpl notInSystem = new ProfesorImpl(555, "notInSys");
        try {
            sys.addConnection(p1, notInSystem);
            fail();
        } catch (Profesor.SameProfesorException | Profesor.ConnectionAlreadyExistsException e) {
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {
        }

        try {
            sys.addConnection(notInSystem, p1);
        } catch (Profesor.SameProfesorException | Profesor.ConnectionAlreadyExistsException e) {
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {
        }

        try {
            sys.addConnection(new ProfesorImpl(1, "sameID"), p1);
            fail();
        } catch (Profesor.SameProfesorException | Profesor.ConnectionAlreadyExistsException ignored) {
        } catch (Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        sys.addConnection(p1, p2);
        assertTrue(p1.getFriends().contains(p2));
        assertTrue(p2.getFriends().contains(p1));
        assertEquals(1, p1.getFriends().size());
        assertEquals(1, p2.getFriends().size());
    }

    @Test
    public void favoritesByRatingTest() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        p1 = sys.joinCartel(1, "p1");
        p2 = sys.joinCartel(2, "p2");

        c1 = sys.addCasaDeBurrito(11, "c1", 11, emptySet());
        c2 = sys.addCasaDeBurrito(22, "c2", 22, emptySet());

        try {
            sys.favoritesByRating(new ProfesorImpl(555, "not-in-system"));
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {
        }

        sys.addConnection(p1, p2);
        c1.rate(p2, 1);
        c2.rate(p2, 2);

        p2.favorite(c1).favorite(c2);
        Iterator<CasaDeBurrito> favoritesByRatingIter = sys.favoritesByRating(p1).iterator();

        assertEquals(22, favoritesByRatingIter.next().getId());
        assertEquals(11, favoritesByRatingIter.next().getId());
        assertFalse(favoritesByRatingIter.hasNext());

        // "האוסף מכיל מסעדות מועדפות של חברים ישירים של הפרופסור בלבד. כלומר, לא יופיעו
        // באוסף מסעדות המועדפות על הפרופסור עצמו ולא על חברים של חברי הפרופסור. "
        p3 = sys.joinCartel(3, "p3");
        c3 = sys.addCasaDeBurrito(33, "c3", 33, emptySet());
        c3.rate(p3, 3);
        p3.favorite(c3);

        sys.addConnection(p2, p3);
        favoritesByRatingIter = sys.favoritesByRating(p1).iterator();

        assertEquals(22, favoritesByRatingIter.next().getId());
        assertEquals(11, favoritesByRatingIter.next().getId());
        assertFalse(favoritesByRatingIter.hasNext());

        // אין כפילויות באוסף. אם חבר מעדיף מסעדה R ,וחבר שני מעדיף את אותה המסעדה, אזי
        // R מופיע בסדר פעם אחת בדיוק )בנוסף לשאר המסעדות המועדפות ע"י החבר הראשון ).

        sys.addConnection(p1, p3);
        c1.rate(p3, 4);
        p3.favorite(c1);

        // ProfesorImpl{id=1, name='p1', favoriteRests=[], friends=[2, 3]}
        // ProfesorImpl{id=2, name='p2', favoriteRests=[CasaDeBurritoImpl{id=22, name='c2', dist=22, menu=[], rates={2=2}}, CasaDeBurritoImpl{id=11, name='c1', dist=11, menu=[], rates={2=1, 3=4}}], friends=[1, 3]}
        // ProfesorImpl{id=3, name='p3', favoriteRests=[CasaDeBurritoImpl{id=33, name='c3', dist=33, menu=[], rates={3=3}}, CasaDeBurritoImpl{id=11, name='c1', dist=11, menu=[], rates={2=1, 3=4}}], friends=[1, 2]}
        favoritesByRatingIter = sys.favoritesByRating(p1).iterator();

        // expected:
        // CasaDeBurritoImpl{id=22, name='c2', dist=22, menu=[], rates={2=2}}
        // CasaDeBurritoImpl{id=11, name='c1', dist=11, menu=[], rates={2=1, 3=4}}
        // CasaDeBurritoImpl{id=33, name='c3', dist=33, menu=[], rates={3=3}}

//        assertEquals(22, favoritesByRatingIter.next().getId());
//        assertEquals(11, favoritesByRatingIter.next().getId());
//        assertEquals(33, favoritesByRatingIter.next().getId());
//        assertFalse(favoritesByRatingIter.hasNext());
    }

    @Test
    public void favoritesByDistTest() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {

        try {
            sys.favoritesByDist(new ProfesorImpl(555, "not-in-system"));
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {
        }

        p1 = sys.joinCartel(1, "p1");
        p2 = sys.joinCartel(2, "p2");

        c1 = sys.addCasaDeBurrito(11, "c1", 11, emptySet());
        c2 = sys.addCasaDeBurrito(22, "c2", 22, emptySet());

        sys.addConnection(p1, p2);
        c1.rate(p2, 1);
        c2.rate(p2, 2);

        p2.favorite(c1).favorite(c2);
        Iterator<CasaDeBurrito> favoriteByDistIter = sys.favoritesByRating(p1).iterator();

        assertEquals(22, favoriteByDistIter.next().distance());
        assertEquals(11, favoriteByDistIter.next().distance());
        assertFalse(favoriteByDistIter.hasNext());

    }

    @Test
    public void getRecommendationTest() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, CartelDeNachos.ImpossibleConnectionException, CasaDeBurrito.CasaDeBurritoNotInSystemException {
        p1 = sys.joinCartel(1, "p1");
        p2 = sys.joinCartel(2, "p2");
        p3 = sys.joinCartel(3, "p3");
        p4 = sys.joinCartel(4, "p4");

        c1 = sys.addCasaDeBurrito(11, "c1", 11, emptySet());
        c2 = sys.addCasaDeBurrito(22, "c2", 22, emptySet());

        c1.rate(p1, 1);
        c1.rate(p2, 2);
        c1.rate(p3, 3);
        c1.rate(p4, 4);

        p1.favorite(c1);
        p2.favorite(c1);
        p3.favorite(c1);
        p4.favorite(c1);

        sys.addConnection(p2,p3);
        sys.addConnection(p3,p4);


        try {
            sys.getRecommendation(new ProfesorImpl(555,"noInSystem"),c1,5);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException | CartelDeNachos.ImpossibleConnectionException e) {
            fail();
        } catch (Profesor.ProfesorNotInSystemException ignored) {}

        try {
            sys.getRecommendation(p1,new CasaDeBurritoImpl(555,"notInSystem",5,emptySet()),5);
            fail();
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException ignored) {
        } catch (CartelDeNachos.ImpossibleConnectionException | Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        try {
            sys.getRecommendation(p1,c1,-1);
            fail();
        } catch (CartelDeNachos.ImpossibleConnectionException ignored) {
        } catch (CasaDeBurrito.CasaDeBurritoNotInSystemException | Profesor.ProfesorNotInSystemException e) {
            fail();
        }

        // p1 not friends with p2
        assertFalse(p1.getFriends().contains(p2));
        assertTrue(sys.getRecommendation(p1,c1,0));

        sys.addConnection(p1,p2);
        // נאמר כי מסעדה
        // היא מומלצת-t ע"י פרופסור, אם הוא נמצא במרחק לכל היותר t קשרי חברויות מפרופסור אחר אשר
        // מעדיף מסעדה זו.

        // p1 -> p2 -> p3 -> p4 , 3 connections

        assertTrue(sys.getRecommendation(p1,c1,0));
        assertTrue(sys.getRecommendation(p1,c1,1));
        assertTrue(sys.getRecommendation(p1,c1,2));
        assertTrue(sys.getRecommendation(p1,c1,3));

//        assertFalse(sys.getRecommendation(p1,c1,4));
//        assertFalse(sys.getRecommendation(p1,c1,30));
//        assertFalse(sys.getRecommendation(p1,c1,300));
    }

    @Test
    public void getMostPopularRestaurantsIdsTest() throws Profesor.ProfesorAlreadyInSystemException, CasaDeBurrito.CasaDeBurritoAlreadyInSystemException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException, Profesor.ProfesorNotInSystemException, Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        p1 = sys.joinCartel(1, "p1");
        p2 = sys.joinCartel(2, "p2");
        p3 = sys.joinCartel(3, "p3");
        p4 = sys.joinCartel(4, "p4");

        c1 = sys.addCasaDeBurrito(11, "c1", 11, emptySet());
        c2 = sys.addCasaDeBurrito(22, "c2", 22, emptySet());


        c1.rate(p1, 1);
        c2.rate(p2, 2);
        c2.rate(p3, 3);
        c2.rate(p4, 4);

        p1.favorite(c1);
        p2.favorite(c2);
        p3.favorite(c2);
        p4.favorite(c2);

        sys.addConnection(p1,p2);
        sys.addConnection(p1,p3);
        sys.addConnection(p1,p4);

        Iterator<Integer> favorIter = sys.getMostPopularRestaurantsIds().iterator();

        assertEquals((int) favorIter.next(), c1.getId());
        assertEquals((int) favorIter.next(), c2.getId());
        assertFalse(favorIter.hasNext());

        p5 = sys.joinCartel(5, "p5");
        c3 = sys.addCasaDeBurrito(33, "c3", 33, emptySet());
        c4 = sys.addCasaDeBurrito(44, "c4", 44, emptySet());

        c3.rate(p1,5);
        c3.rate(p2,5);
        c3.rate(p3,5);
        c3.rate(p4,5);
        c3.rate(p5,5);

        p1.favorite(c3);
        p2.favorite(c3);
        p3.favorite(c3);
        p4.favorite(c3);
        p5.favorite(c3);

        sys.addConnection(p1,p5);

        favorIter = sys.getMostPopularRestaurantsIds().iterator();

        // c3 points = 8, four friends of p1 + everyone friends of p1.
        assertEquals((int) favorIter.next(), c3.getId());
        assertFalse(favorIter.hasNext());

        // make c1 most popular as c3.

        c1.rate(p1,5).rate(p2,5).rate(p3,5).rate(p3,5).rate(p4,5).rate(p5,5);
        p1.favorite(c1);
        p2.favorite(c1);
        p3.favorite(c1);
        p4.favorite(c1);
        p5.favorite(c1);

        favorIter = sys.getMostPopularRestaurantsIds().iterator();

        // c3,c2 points = 8 ; c2.id < c3.id
        assertEquals((int) favorIter.next(), c1.getId());
        assertEquals((int) favorIter.next(), c3.getId());
        assertFalse(favorIter.hasNext());
    }


}