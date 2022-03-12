package main;

import fileio.ActorInputData;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

public final class FilterDescriptionQuery {
    private final List<ActorInputData> actors;

    public FilterDescriptionQuery(final List<ActorInputData> actors) {
        this.actors = actors;
    }

    /**
     * Functia determina daca campul careerDescription al actorului actor
     * contine toate cuvintele din lista keyWords
     * @param actor este actorul in  a carui descriere se face cautarea
     *              cuvintelor din  keyWords
     * @param keyWords este cel de-al treilea camp din lisat filters data din input
     * @return intoarce 1 daca actorul indeplineste conditiile din input sau 0
     * in caz contrar
     */
    public int checkFilter(final ActorInputData actor,
                           final List<String> keyWords) {
        // Transform toate literele din sirul careerDescription in litere mici
        String lower = actor.getCareerDescription().toLowerCase();
        // Pentru fiecare cuvant din key_words verific daca gasesc un
        // corspondent in careerDescription
        int ok;
        Scanner scanner;
        // Folosesc Scanner ca sa selectez fiecare cuvant din textul
        // aferent descrierii actorului
        for (String keyWord : keyWords) {
            // Initial, cuvantul key_word nu este gasit
            ok = 0;
            scanner = new Scanner(lower);
            // Selectez caracterele de delimitare
            scanner.useDelimiter("[ .,-]");
            // Cat timp mai am un element urmator
            while (scanner.hasNext()) {
                // Daca am gasit corespondentul lui key_word in
                // careerDescription, ma opresc din cautare
                if (scanner.next().equals(keyWord)) {
                    ok = 1;
                    break;
                }
            }

            // Daca pentru un singur cuvant din key_words nu am gasit un
            // corespondent in careerDescription, atunci actorul nu va fi
            // adaugat in lista de afisari.
            if (ok == 0) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * Functia sorteaza actorii care au toate cuvintele din campul
     * filters.get(2) in descrierea carierei
     * @param filter este lista din input pe baza careia se fac selectiile
     * @param sortType este tipul sortarii pentru lista de actori
     * @return intoarce actorii sortati care indeplinesc conditiile din enunt
     */
    public String filter(final List<List<String>> filter, final String sortType) {
        List<String> words = filter.get(2);
        LinkedList<String> actorsNames = new LinkedList<>();
        StringBuilder message = new StringBuilder("Query result: [");

        for (ActorInputData actor : actors) {
            // Daca actorul curent indeplineste conditiile din filter, va fi
            // adaugat in lista pentru afisare
            if (checkFilter(actor, words) == 1) {
                actorsNames.add(actor.getName());
            }
        }

        // Sortez lista conform criteriului de sortare precizat in input
        actorsNames.sort(Comparator.naturalOrder());
        if (sortType.equals("desc")) {
            Collections.reverse(actorsNames);
        }

        for (int i = 0; i < actorsNames.size(); i++) {
            message.append(actorsNames.get(i));
            if (i < actorsNames.size() - 1) {
                message.append(", ");
            }
        }

        message.append("]");
        return message.toString();
    }

}
