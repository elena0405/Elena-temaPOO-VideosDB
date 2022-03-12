package main;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.List;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashMap;

import utils.Utils;

public final class AwardsQuery {
    private final List<ActorInputData> actors;

    public AwardsQuery(final List<ActorInputData> actors) {
        this.actors = actors;
    }

    /**
     * Functia determina daca un actor indeplineste cerintele din input sau nu,
     * pe baza listei sale de premii
     * @param awards reprezinta lista de premii care filtreaza actorii primiti
     * @param actor reprezinta un actor in a carui lista de premii se
     *              identifica premiile cerute in input
     * @return set returneaza 1 daca actorul contine toate premiile mentionate in
     * input si 0 in caz contrar
     */
    public int filterAwards(final List<String> awards, final ActorInputData actor) {
        int i;
        // Initial, presupun ca actorul actor are toate premiile din
        // lista data ca parametru.
        int set = 1;
        int n = awards.size();

        // Iterez prin lista de premii si verifi daca actorul actor are
        // toate premiile din lista.
        for (i = 0; i < n; i++) {
            // Daca am gasit un singur premiu pe care actorul nu il are,
            // atunci decid ca actorul nu indeplineste conditiile din cerinta.
            if (!actor.getAwards().containsKey(Utils.stringToAwards(awards.get(i)))) {
                set = 0;
                break;
            }
        }

        return set;
    }

    /**
     * Functia determina numarul total de premii pentru un actor
     * @param actor reprezinta actorul pentru care se determina numarul de
     *              premii
     * @return nr returneaza numarul de premii total calculat
     */
    public int getActorAwards(final ActorInputData actor) {
        int nr = 0;
        Map<ActorsAwards, Integer> map = actor.getAwards();
        // Retin in variabila nr numarul de premii pentru actorul actor.
        for (Map.Entry<ActorsAwards, Integer> entry : map.entrySet()) {
            nr += entry.getValue();
        }

        return nr;
    }

    /**
     * Functia awards este fuctia propriu-zisa de afisare a actorilor care
     * au toate premiile mentionate in input, sortati conform unui criteriu
     * de sortare dat ca parametru.
     * @param filters reprezinta filtrarea pe baza careia se vor selecta acorii
     * @param sortType este criteriul de sortare
     * @return message.toString() returneaza string-ul ce contine numele
     * actorilor
     */
    public String awards(final List<List<String>> filters, final String sortType) {
        StringBuilder message = new StringBuilder("Query result: [");
        LinkedHashMap<String, Integer> newActors = new LinkedHashMap<>();

        for (ActorInputData actor : actors) {
            actor.setNumberAwards(getActorAwards(actor));
            // Daca actorul curent are toate premiile mentionate in
            // input, il adaug in lista noua.
            if (filterAwards(filters.get(3), actor) == 1) {
                newActors.put(actor.getName(), actor.getNumberAwards());
            }
        }

        // Ca sa pot sorta mai usor un LinkedHashMap, ma folosesc de
        // o variabila de tipul LinkedList.
        LinkedList<Map.Entry<String, Integer>> list =
                new LinkedList<>(newActors.entrySet());

        // Sortez lista list
        list.sort((o1, o2) -> {
            Integer f1 = o1.getValue();
            Integer f2 = o2.getValue();
            if (f1.equals(f2)) {
                String s1 = o1.getKey();
                String s2 = o2.getKey();

                return s1.compareTo(s2);
            }
            return f1.compareTo(f2);
        });

        if (sortType.equals("desc")) {
            Collections.reverse(list);
        }

        int index = 0;
        int n = list.size();

        // Adaug in mesajul final numele actorilor selectati
        for (Map.Entry<String, Integer> entry : list) {
            message.append(entry.getKey());
            if (index < n - 1) {
                message.append(", ");
            }
            index++;
        }

        message.append("]");
        return message.toString();
    }
}
