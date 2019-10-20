package OOP.Tests;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import OOP.Solution.CasaDeBurritoImpl;
import OOP.Solution.ProfesorImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.*;


public class ProfesorTest {
    private Profesor p1;
    private Profesor p2;
    private Profesor p3SameIDp2;
    private Profesor p4;
    private Profesor p5;
    private Profesor p6;

    private CasaDeBurrito c1;
    private CasaDeBurrito c2;
    private CasaDeBurrito c3SameIDc2;
    private CasaDeBurrito c4;
    private CasaDeBurrito c5;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        p1 = new ProfesorImpl(1, "p1");
        p2 = new ProfesorImpl(2, "p2");
        p3SameIDp2 = new ProfesorImpl(2, "p3");
        p4 = new ProfesorImpl(4, "p4");
        p5 = new ProfesorImpl(5, "p5");
        p6 = new ProfesorImpl(6, "p6");

        c1 = new CasaDeBurritoImpl(11, "c1", 11, emptySet());
        c2 = new CasaDeBurritoImpl(22, "c2", 22, emptySet());

        // Same id as c1
        c3SameIDc2 = new CasaDeBurritoImpl(22, "c3", 33, emptySet());
        c4 = new CasaDeBurritoImpl(44, "c4", 44, emptySet());
        c5 = new CasaDeBurritoImpl(55, "c5", 55, emptySet());
    }

    @Test
    public void getIdTest() {
        assertEquals(p1.getId(), 1);
        assertEquals(p2.getId(), 2);
    }

    @Test
    public void favoriteExceptionTest() throws Profesor.UnratedFavoriteCasaDeBurritoException {
        exceptionRule.expect(Profesor.UnratedFavoriteCasaDeBurritoException.class);
        assertTrue(p1.favorites().isEmpty());
        p1.favorite(c1);
    }

    @Test
    public void favoriteTest() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        c1.rate(p1, 0);
        p1.favorite(c1);

        assertTrue(p1.favorites().contains(c1));

        // add same rest
        c2.rate(p1, 5);
        p1.favorite(c2).favorite(c2);
        assertTrue(p1.favorites().contains(c2));

        c1.rate(p2, 5);
        c2.rate(p2, 5);

        // update c2 rate to 0;
        c3SameIDc2.rate(p2, 0);

        // p2 favorite only c2, while c1,c2 are rated.
        assertTrue(p2.favorites().isEmpty());
        p2.favorite(c2).favorite(c3SameIDc2);

        // same id, check that equals and hashcode are overloaded
        assertEquals(1, p2.favorites().size());

        p2.favorite(c1).favorite(c2).favorite(c3SameIDc2);
        assertEquals(2, p2.favorites().size());
        assertTrue(p2.favorites().contains(c3SameIDc2));
        assertTrue(p2.favorites().contains(c2));
        assertTrue(p2.favorites().contains(c1));
    }

    @Test
    public void favoritesTest() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        assertTrue(p1.favorites().isEmpty());

        c1.rate(p1, 0);
        p1.favorite(c1);

        assertEquals(1, p1.favorites().size());
        assertTrue(p1.favorites().contains(c1));

        Collection<CasaDeBurrito> willBeChangedSet = p1.favorites();

        willBeChangedSet.add(c2);

        assertNotEquals(willBeChangedSet, p1.favorites());
        assertEquals(1, p1.favorites().size());
        assertEquals(2, willBeChangedSet.size());
    }

    @Test
    public void addFriendSameProfesorExceptionTest() throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        exceptionRule.expect(Profesor.SameProfesorException.class);
        p1.addFriend(p1);

    }

    @Test
    public void addFriendConnectionAlreadyExistsExceptionTest() throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        exceptionRule.expect(Profesor.ConnectionAlreadyExistsException.class);
        p1.addFriend(p2);
        p1.addFriend(p2);
    }

    @Test
    public void addFriendTest() throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        p1.addFriend(p2);

        assertTrue(p1.getFriends().contains(p2));
        assertFalse(p2.getFriends().contains(p1));


        p2.addFriend(p1);
        assertTrue(p2.getFriends().contains(p1));

        assertEquals(1, p1.getFriends().size());
        assertEquals(1, p2.getFriends().size());


        // Trying add friend with same id.
        assertTrue(p3SameIDp2.getFriends().isEmpty());
        try {
            p1.addFriend(p3SameIDp2);
        } catch (Profesor.ConnectionAlreadyExistsException ignored) {
            assertTrue(p3SameIDp2.getFriends().isEmpty());
            assertTrue(p1.getFriends().contains(p3SameIDp2));
        }


        // Same id adding
        try {
            p3SameIDp2.addFriend(p2);
        } catch (Profesor.SameProfesorException ignored) {
            assertTrue(p3SameIDp2.getFriends().isEmpty());
        }

        p3SameIDp2.addFriend(p1).addFriend(p4);
        assertEquals(2, p3SameIDp2.getFriends().size());

    }

    @Test
    public void getFriendsTest() throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        assertTrue(p1.getFriends().isEmpty());
        p1.addFriend(p2);

        Set<Profesor> willBeChangedSet = p1.getFriends();

        willBeChangedSet.add(p4);

        assertNotEquals(willBeChangedSet, p1.favorites());
        assertEquals(1, p1.getFriends().size());
        assertEquals(2, willBeChangedSet.size());
    }

    @Test
    public void filteredFriendsTest() throws Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException, CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        p1.addFriend(p2).addFriend(p4).addFriend(p5).addFriend(p6);

        // 2 4 6
        assertEquals(3, p1.filteredFriends(p -> p.getId() % 2 == 0).size());

        c1.rate(p2, 5);
        c2.rate(p2, 5);
        c4.rate(p2, 5);
        p2.favorite(c1).favorite(c2).favorite(c4);

        // p2 fav c1,c2,c4
        assertTrue(p1.filteredFriends(p -> p.favorites().size() == 3).contains(p2));
    }

    @Test
    public void filterAndSortFavoritesTest() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        c1.rate(p1, 1);
        c2.rate(p1, 2);
        c4.rate(p1, 4);
        c5.rate(p1, 5);
        p1.favorite(c1).favorite(c2).favorite(c4).favorite(c5);

        // c4 c5
        Iterator<CasaDeBurrito> sortedByDistIter =
                p1.filterAndSortFavorites(Comparator.naturalOrder(), c -> c4.getId() <= c.getId()).iterator();

        assertEquals(sortedByDistIter.next().getId(), c4.getId());
        assertEquals(sortedByDistIter.next().getId(), c5.getId());
    }

    @Test
    public void favoritesByRatingTest() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        c1.rate(p1, 1);
        c2.rate(p1, 2);
        c4.rate(p1, 4);
        c5.rate(p1, 5);
        p1.favorite(c1).favorite(c2).favorite(c4).favorite(c5);

        // return empty
        Iterator<CasaDeBurrito> sortedByDistIter = p1.favoritesByRating(10000).iterator();

        assertFalse(sortedByDistIter.hasNext());

        sortedByDistIter = p1.favoritesByRating(0).iterator();

        assertEquals(sortedByDistIter.next().getId(), c5.getId());
        assertEquals(sortedByDistIter.next().getId(), c4.getId());
        assertEquals(sortedByDistIter.next().getId(), c2.getId());
        assertEquals(sortedByDistIter.next().getId(), c1.getId());
    }

    @Test
    public void favoritesByDistTest() throws CasaDeBurrito.RateRangeException, Profesor.UnratedFavoriteCasaDeBurritoException {
        c1.rate(p1, 5);
        c2.rate(p1, 5);
        c4.rate(p1, 5);
        c5.rate(p1, 5);
        p1.favorite(c1).favorite(c2).favorite(c4).favorite(c5);

        Iterator<CasaDeBurrito> sortedByDistIter = p1.favoritesByDist(0).iterator();

        assertFalse(sortedByDistIter.hasNext());

        sortedByDistIter = p1.favoritesByDist(
                p1.favorites().stream().map(CasaDeBurrito::distance).mapToInt(Integer::intValue).max().orElse(0)
        ).iterator();

        assertEquals(sortedByDistIter.next().getId(), c1.distance());
        assertEquals(sortedByDistIter.next().getId(), c2.distance());
        assertEquals(sortedByDistIter.next().getId(), c4.distance());
        assertEquals(sortedByDistIter.next().getId(), c5.distance());
    }

    @Test
    public void equalsTest() {
        assertNotEquals(p1, p2);
        assertEquals(p2, p3SameIDp2);
    }

    @Test
    public void compareToTest() {
        assertTrue(p1.compareTo(p2) < 0);
        assertEquals(0, p2.compareTo(p3SameIDp2));
        assertTrue(p2.compareTo(p1) > 0);
    }
}