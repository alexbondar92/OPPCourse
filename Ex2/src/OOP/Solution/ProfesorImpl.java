package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor {
    private final int id;
    private final String name;
    private final Set<CasaDeBurrito> favorites;
    private final Set<Profesor> friends;

    public ProfesorImpl(int id, String name) {
        this.id = id;
        this.name = name;
        this.favorites = new TreeSet<>();
        this.friends = new TreeSet<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c) throws UnratedFavoriteCasaDeBurritoException {
        if (!(c.isRatedBy(this))) {
            throw new UnratedFavoriteCasaDeBurritoException();
        }

        this.favorites.add(c);

//        for (CasaDeBurrito casa : this.favorites){
//            if (casa.getId() == c.getId()){
//                return this;
//            }
//        }
//        this.favorites.add(c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return new TreeSet<>(this.favorites);
    }

    @Override
    public Profesor addFriend(Profesor p) throws SameProfesorException, ConnectionAlreadyExistsException {
        if (p.equals(this)) {
            throw new SameProfesorException();
        }
        if (this.friends.contains(p)) {
            throw new ConnectionAlreadyExistsException();
        }
        this.friends.add(p);

        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        return new TreeSet<>(this.friends);
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        Set<Profesor> tmpSet = this.getFriends();
        Predicate<Profesor> pred = (Profesor c) -> !(p.test(c));        // TODO: fixed - need to try and improve this one
        tmpSet.removeIf(pred);                                          // TODO: fixed - need to try and improve this one
//        tmpSet.removeIf(p);
        return tmpSet;
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        Set<CasaDeBurrito> tmpSet = new TreeSet<>(this.favorites);

        List<CasaDeBurrito> tmpList = tmpSet.stream().sorted(comp).collect(Collectors.toList());

        Predicate<CasaDeBurrito> pred = (CasaDeBurrito c) -> !(p.test(c));  // TODO: fixed - need to try and improve this one
        tmpList.removeIf(pred);                                              // TODO: fixed - need to try and improve this one
//        tmpList.removeIf(p);
        return tmpList;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        final int rLimit_aux = rLimit;
        Comparator<CasaDeBurrito> cmp = (CasaDeBurrito c1, CasaDeBurrito c2) -> {
            if (c1.averageRating() < c2.averageRating()) return 1;
            if (c1.averageRating() > c2.averageRating()) return -1;
            if (c1.distance() > c2.distance()) return 1;
            if (c1.distance() < c2.distance()) return -1;
            if (c1.getId() > c2.getId()) return 1;
            if (c1.getId() < c2.getId()) return -1;
            return 0;
        };
        Predicate<CasaDeBurrito> pred = (CasaDeBurrito c) -> (c.averageRating() >= rLimit_aux);
        return this.filterAndSortFavorites(cmp, pred);
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        final int dLimit_aux = dLimit;
        Comparator<CasaDeBurrito> cmp = (CasaDeBurrito c1, CasaDeBurrito c2) -> {
            if (c1.distance() > c2.distance()) return 1;
            if (c1.distance() < c2.distance()) return -1;
            if (c1.averageRating() < c2.averageRating()) return 1;
            if (c1.averageRating() > c2.averageRating()) return -1;
            if (c1.getId() > c2.getId()) return 1;
            if (c1.getId() < c2.getId()) return -1;
            return 0;
        };
        Predicate<CasaDeBurrito> pred = (CasaDeBurrito c) -> c.distance() <= dLimit_aux;
        return this.filterAndSortFavorites(cmp, pred);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Profesor)) {
            return false;
        } else {
            return (this.id == ((Profesor) o).getId());
        }
    }

    /**
     * @return the profesors's description as a string in the following format:
     * <format>
     * Profesor: <name>.
     * Id: <id>.
     * Favorites: <casaName1, casaName2, casaName3...>
     * </format>
     * Note: favorite casas de burrito are ordered by lexicographical order, asc.
     * <p>
     * Example:
     * <p>
     * Profesor: Oren.
     * Id: 236703.
     * Favorites: BBB, Burger salon.
     */
    @Override
    public String toString() {
        List<String> namesList = new ArrayList<>();
        for (CasaDeBurrito c : this.favorites) {
            namesList.add(c.getName());
        }
        Collections.sort(namesList);
        String joinedFavorites = String.join(", ", namesList);
        return "Profesor: " + this.name + ".\n" + "Id: " + this.getId() + ".\n" + "Favorites: " + joinedFavorites + ".";

    }

    @Override
    public int compareTo(Profesor o) {
        if (this.getId() == o.getId()) return 0;
        if (this.getId() > o.getId()) return 1;
        if (this.getId() < o.getId()) return -1;


        return 0;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
