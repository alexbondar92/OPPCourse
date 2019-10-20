package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class CartelDeNachosImpl implements CartelDeNachos {
    private final Set<Profesor> losProfesores;
    private final Set<CasaDeBurrito> lasCasas;


    public CartelDeNachosImpl() {
        this.losProfesores = new TreeSet<>();
        this.lasCasas = new TreeSet<>();
    }

    @Override
    public Profesor joinCartel(int id, String name) throws Profesor.ProfesorAlreadyInSystemException {
        if (this.losProfesores.contains(new ProfesorImpl(id, name))) {
            throw new Profesor.ProfesorAlreadyInSystemException();
        }

        Profesor p = new ProfesorImpl(id, name);
        this.losProfesores.add(p);

        return p;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu) throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        if (this.lasCasas.contains(new CasaDeBurritoImpl(id, name, dist, menu))) {
            throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        }

        CasaDeBurrito c = new CasaDeBurritoImpl(id, name, dist, menu);
        this.lasCasas.add(c);

        return c;
    }

    @Override
    public Collection<Profesor> registeredProfesores() {
        return new TreeSet<>(this.losProfesores);
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        return new TreeSet<>(this.lasCasas);
    }

    @Override
    public Profesor getProfesor(int id) throws Profesor.ProfesorNotInSystemException {
        for (Profesor p : this.losProfesores) {
            if (p.getId() == id) {
                return p;
            }
        }

        throw new Profesor.ProfesorNotInSystemException();
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id) throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        for (CasaDeBurrito c : this.lasCasas) {
            if (c.getId() == id) {
                return c;
            }
        }

        throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2) throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        if (!(this.losProfesores.contains(p1)) || !(this.losProfesores.contains(p2))) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        if (p1.equals(p2)) {
            throw new Profesor.SameProfesorException();
        }
        for (Profesor p : losProfesores){
            if (p.getId() == p1.getId()){
                if (p.getFriends().contains(p2)){
                    throw new Profesor.ConnectionAlreadyExistsException();
                }
            }
        }
//        if (p1.getFriends().contains(p2) && p2.getFriends().contains(p1)) {
//            throw new Profesor.ConnectionAlreadyExistsException();
//        }
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if (!this.losProfesores.contains(p)) {
            throw new Profesor.ProfesorNotInSystemException();
        }

        ArrayList<CasaDeBurrito> returnCasas = new ArrayList<>();
        Set<Profesor> friendsOfp = p.getFriends();
        List<Profesor> tmpList = friendsOfp.stream().sorted(Comparable::compareTo).collect(Collectors.toList());

        for (Profesor p_intr : tmpList) {
            Collection<CasaDeBurrito> casasP = p_intr.favoritesByRating(0);
            for (CasaDeBurrito casa : casasP) {
                if (!returnCasas.contains(casa)) {
                    returnCasas.add(casa);
                }
            }
        }
        return returnCasas;
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(Profesor p) throws Profesor.ProfesorNotInSystemException {
        if (!this.losProfesores.contains(p)) {
            throw new Profesor.ProfesorNotInSystemException();
        }

        ArrayList<CasaDeBurrito> returnCasas = new ArrayList<>();
        Set<Profesor> friendsOfp = p.getFriends();
        List<Profesor> tmpList = friendsOfp.stream().sorted(Comparable::compareTo).collect(Collectors.toList());

        for (Profesor p_intr : tmpList) {
            Collection<CasaDeBurrito> casasP = p_intr.favoritesByDist(Integer.MAX_VALUE);
            for (CasaDeBurrito casa : casasP) {
                if (!returnCasas.contains(casa)) {
                    returnCasas.add(casa);
                }
            }
        }
        return returnCasas;
    }

    @Override
    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t) throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        if (!this.losProfesores.contains(p)) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        if (!this.lasCasas.contains(c)) {
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        if (t < 0) {
            throw new ImpossibleConnectionException();
        }

        if (p.favorites().contains(c)) {
            return true;
        }

        Stack<Profesor> stack = new Stack<>();
        stack.push(p);
        for (Profesor friend : p.getFriends()) {
            if (!stack.contains(friend)) {
                if (this.getRecommendation_aux(friend, c, t - 1, stack)) {
                    return true;
                }
            }
        }
        stack.pop();
        return false;
    }

    private boolean getRecommendation_aux(Profesor p, CasaDeBurrito c, int t, Stack<Profesor> stack) {
        if (t < 0) {
            return false;
        }
        if (p.favorites().contains(c)) {
            return true;
        }

        stack.push(p);
        for (Profesor friend : p.getFriends()) {
            if (!stack.contains(friend)) {
                if (this.getRecommendation_aux(friend, c, t - 1, stack)) {
                    return true;
                }
            }
        }
        stack.pop();
        return false;
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds() {
        Map<Integer, Integer> hist = new TreeMap<>();
        for (CasaDeBurrito casa : this.lasCasas) {
            hist.put(casa.getId(), 0);
        }

        for (Profesor p : this.losProfesores) {
            for (Profesor friend : p.getFriends()) {
                for (CasaDeBurrito casa : friend.favorites()) {
                    hist.replace(casa.getId(), hist.get(casa.getId()) + 1);
                }
            }
        }

        List<Integer> retList = new ArrayList<>();
        if(hist.isEmpty()) return retList;
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(hist.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Integer max = list.get(list.size() - 1).getValue();
        for (Map.Entry<Integer, Integer> current : list) {
            if (current.getValue().equals(max)) {
                retList.add(current.getKey());
            }
        }
        return retList.stream().sorted().collect(Collectors.toList());
    }

    /**
     * @return the cartel's description as a string in the following format:
     * <format>
     * Registered profesores: <profesorId1, profesorId2, profesorId3...>.
     * Registered casas de burrito: <casaId1, casaId2, casaId3...>.
     * Profesores:
     * <profesor1Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * <profesor2Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * ...
     * End profesores.
     * </format>
     * Note: profesores, casas de burrito and friends' ids are ordered by natural integer order, asc.*
     * Example:
     * <p>
     * Registered profesores: 1, 236703, 555555.
     * Registered casas de burrito: 12, 13.
     * Profesores:
     * 1 -> [236703, 555555555].
     * 236703 -> [1].
     * 555555 -> [1].
    //     * End profesores.
     */
    @Override
    public String toString() {

        List<Integer> profesoresIds = new ArrayList<>();
        List<Integer> casasIds = new ArrayList<>();

        for (Profesor p : this.losProfesores) {
            profesoresIds.add(p.getId());
        }
        for (CasaDeBurrito c : this.lasCasas) {
            casasIds.add(c.getId());
        }

        profesoresIds = profesoresIds.stream().sorted().collect(Collectors.toList());
        casasIds = casasIds.stream().sorted().collect(Collectors.toList());
        String retString = "Registered profesores: " + String.join(", ", profesoresIds.toString()) + ".\n";
        retString = retString + "Registered casas de burrito: " + String.join(", ", casasIds.toString()) + ".\n";
        retString = retString + "Profesores:\n";

        for (Integer pId : profesoresIds) {
            List<Integer> friendsIds = new ArrayList<>();
            Set<Profesor> tempSet = new TreeSet<>();
            try {
                tempSet = this.getProfesor(pId).getFriends();
            } catch (Profesor.ProfesorNotInSystemException e) {
                assert false;
            }

            for (Profesor p : tempSet) {
                friendsIds.add(p.getId());
            }
            friendsIds = friendsIds.stream().sorted().collect(Collectors.toList());
            retString = retString + pId + " -> " + String.join(", ", friendsIds.toString()) + ".\n";
        }

        return retString + "End profesores.";
    }

    @Override
    public int hashCode() {
        return losProfesores.hashCode() + lasCasas.hashCode();
    }
}
