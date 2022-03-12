package main;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public final class FavoriteRecommendation {
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final List<UserInputData> users;

    public FavoriteRecommendation(final List<MovieInputData> movies,
                                   final List<SerialInputData> serials,
                                   final List<UserInputData> users) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Functia cauta utilizatorul in functie de nume in lista de
     * utilizatori
     * @param username retine numele utilizatorului ce urmeaza a
     *                 fi cautat
     * @return returneaza utilizatorul daca l-a gasit sau null in
     * caz contrar
     */
    public UserInputData getUser(final String username) {
        int i;
        int n = users.size();

        // Iterez prin lista de utilizatri ca sa gasesc utilizatorul cu numele
        // username
        for (i = 0; i < n; i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        // Daca nu l-am gasit, returnez null
        return null;
    }

    /**
     * Functia determina numarul de aparitii al unui videoclip in listele
     * de videoclipuri favorite ale utilizatorilor
     * @param title este titlul videoclipului
     * @return nr returneaza numarul de aparitii calculat
     */
    public int videoInFavorites(final String title) {
        int nr = 0;
        for (UserInputData user : users) {
            if (user.getFavoriteMovies().contains(title)) {
                nr++;
            }
        }
        return nr;
    }

    /**
     * Functia favorite determina videoclipul care apare cel mai des in lista
     * de videoclipuri favorite, dar care nu a fost inca vazut de utilizatorul
     * cu numele username
     * @param username este numele utilizatorului
     * @return message.toString() returneaza numele videoclipului sau un
     * mesaj de eroare in cazul in care nu s-a putut face alegerea
     */
    public String favorite(final String username) {
        // Pentru a pastra ordinea videoclipurilor din baza de date, am
        // retinut campurile necesare sortarii mai intai intr-un linkedHashMap.
        LinkedHashMap<String, Integer> videos = new LinkedHashMap<>();
        UserInputData user = getUser(username);
        // Daca utilizatorul nu are cont de PREMIUM, voi returna un mesaj
        // de eroare
        if (user != null
                && user.getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }

        StringBuilder message =
                new StringBuilder("FavoriteRecommendation result: ");

        for (MovieInputData movie : movies) {
            if (videoInFavorites(movie.getTitle()) >= 1 && user != null
                    && !user.getHistory().containsKey(movie.getTitle())) {
                videos.put(movie.getTitle(),
                        videoInFavorites(movie.getTitle()));
            }
        }

        for (SerialInputData serial : serials) {
            if (videoInFavorites(serial.getTitle()) >= 1 && user != null
                    && !user.getHistory().containsKey(serial.getTitle())) {
                videos.put(serial.getTitle(),
                        videoInFavorites(serial.getTitle()));
            }
        }

        // Pentru sortare, am retinut datele pe care vreau sa le folosesc
        // intr-un linkedList, pentru a pastra si ordinea din baza de date.
        LinkedList<Map.Entry<String, Integer>> newList =
                new LinkedList<>(videos.entrySet());
        // Sortez noua lista de videoclipuri
        newList.sort((o1, o2) -> {
            Integer val1 = o1.getValue();
            Integer val2 = o2.getValue();
            // Deoarece sortarea se face in mod descrescator, am inmultit
            // valoarea returnata de functia de camparare cu -1
            return -val1.compareTo(val2);
        });

        // Selectez primul element din lista sortata anterior si returnez
        // ce am gasit
        for (Map.Entry<String, Integer> entry : newList) {
            message.append(entry.getKey());
            return message.toString();
        }

        // Daca niciunul din cazurile de mai sus nu este indeplinit, returnez un
        // mesaj de eroare
        return "FavoriteRecommendation cannot be applied!";
    }

}
