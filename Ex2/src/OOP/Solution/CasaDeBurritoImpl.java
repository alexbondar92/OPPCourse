package OOP.Solution;


import java.util.*;

import OOP.Provided.*;

public class CasaDeBurritoImpl implements OOP.Provided.CasaDeBurrito {
    private final int id;
    private final String name;
    private final int dist;
    private final Set<String> menu;
    private final Map<Profesor, Integer> ratings;

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.dist = dist;
        //this.menu = menu;
//        this.menu = Set.copyOf(menu);                                   // no copyOf method
        this.menu = new TreeSet<>(menu);
//        this.menu = menu.stream().collect(Collectors.toSet());          // TODO: if needed
        this.ratings = new TreeMap<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int distance() {
        return this.dist;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        if (p == null) return false;            // TODO: null check for all methods!

//        for (Map.Entry<Profesor, Integer> entry : this.ratings.entrySet())
//        {
//            if (entry.getKey().getId() == p.getId()){
//                return true;
//            }
//        }
//        return false;
        return this.ratings.containsKey(p);
    }

    public CasaDeBurrito rate(Profesor p, int r) throws RateRangeException {
        if (r < 0 || 5 < r) {
            throw new RateRangeException();
        }


        this.ratings.put(p,r);
//        if (!this.isRatedBy(p)){
//            this.ratings.put(p, r);
//        } else {
//            for (Map.Entry<Profesor, Integer> entry : this.ratings.entrySet()){
//                if (entry.getKey().getId() == p.getId()){
//                    entry.setValue(r);
//                }
//            }
//        }

        return this;
    }

    @Override
    public int numberOfRates() {
        return this.ratings.size();
    }

    @Override
    public double averageRating() {
        if (this.numberOfRates() == 0) {
            return 0;
        }
        int sum = 0;

        for (Map.Entry<Profesor, Integer> entry : this.ratings.entrySet()) {
            sum += entry.getValue();                // TODO: stream sum up func... better one...
        }

        return ((double) sum / this.numberOfRates());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CasaDeBurrito)) {
            return false;
        } else {
            return (this.id == ((CasaDeBurrito) o).getId());
        }
    }

    /**
     * @return the CasaDeBurrito's description as a string in the following format:
     * <format>
     * CasaDeBurrito: <name>.
     * Id: <id>.
     * Distance: <dist>.
     * Menu: <menuItem1, menuItem2, menuItem3...>.
     * </format>
     * Note: Menu items are ordered by lexicographical order, asc.
     * <p>
     * Example:
     * <p>
     * CasaDeBurrito: BBB.
     * Id: 1.
     * Distance: 5.
     * Menu: Cola, French Fries, Steak.
     */
    @Override
    public String toString() {
        List<String> tmpList = new ArrayList<>(this.menu);
        Collections.sort(tmpList);
        String joinedMenu = String.join(", ", tmpList);
        return "CasaDeBurrito: " + this.name + ".\n" + "Id: " + this.getId() + ".\n" + "Distance: " + this.distance() + ".\n" + "Menu: " + joinedMenu + ".";
    }

    @Override
    public int compareTo(CasaDeBurrito o) {
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