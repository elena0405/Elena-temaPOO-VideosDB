package main;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.LinkedList;

public final class PopularRecommendation {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;

    public PopularRecommendation(final List<UserInputData> users,
                                 final List<MovieInputData> movies,
                                 final List<SerialInputData> serials) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Functia determina toate genurile selectate din filme si din seriale
     * @param movies este lista de filme
     * @param serials este lista de seriale
     * @return genres returneaza genurile identificate
     */
    public List<String> getGenres(final List<MovieInputData> movies,
                                   final List<SerialInputData> serials) {
        List<String> genres = new ArrayList<>();
        int i, j;
        int n = movies.size();
        // Iterez prin lista de filme
        for (i = 0; i < n; i++) {
            // Iterez prin lista de genuri corespunzatoare unui film
            for (j = 0; j < movies.get(i).getGenres().size(); j++) {
                // Daca genul identificat nu a fost adaugat in lista de genuri
                // il adaug
                if (!genres.contains(movies.get(i).getGenres().get(j))) {
                    genres.add(movies.get(i).getGenres().get(j));
                }
            }
        }

        n = serials.size();
        // Iterez prin lista de seriale
        for (i = 0; i < n; i++) {
            // Iterez prin lista de genuri aferenta unui serial
            for (j = 0; j < serials.get(i).getGenres().size(); j++) {
                // Daca genul identificat nu a fost adaugat in lista de genuri,
                // il adaug
                if (!genres.contains(serials.get(i).getGenres().get(j))) {
                    genres.add(serials.get(i).getGenres().get(j));
                }
            }
        }

        return genres;
    }

    /**
     * Functia cauta utilizatorul dupa nume
     * @param username este numele utilizatorului pe care il caut
     * @return intoarce utilizatorul identificat sau null
     */
    public UserInputData findUser(final String username) {
        int i;
        int n = users.size();
        for (i = 0; i < n; i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
    }

    /**
     * Functia determina popularitatea unui gen, care se calculeaza retinand
     * numarul de vizualizari ale videoclipurilor care au acel gen
     * @param genre este un gen pentru care se determina popularitatea
     * @return nr intoarce numarul total de vizualizari pentru acel gen
     */
    public int getGenrePopularity(final String genre) {
        int i;
        int nr = 0;
        int n = movies.size();

        // Iterez prin lista de filme
        for (i = 0; i < n; i++) {
            // Daca filmul are genul genre, adaug la variabila nr numarul
            // de vizualizari al filmului
            if (movies.get(i).getGenres().contains(genre)) {
                nr += movies.get(i).getNrOfViewes();
            }
        }

        // Iterez prin lisat de seriale
        n = serials.size();
        for (i = 0; i < n; i++) {
            // Daca filmul are genul genre, adaug la variabila nr numarul
            // de vizualizari al serialului
            if (serials.get(i).getGenres().contains(genre)) {
                nr += serials.get(i).getNrOfViews();
            }
        }

        return nr;
    }

    /**
     * Functia intoarce filmele care apartin genului dat ca parametru
     * @param genre retine genul dupa care se identifica filmele
     * @return l returneaza lista de filme care coontin genul genre
     */
    public List<MovieInputData> moviesFromGenre(final String genre) {
        int i;
        int n = movies.size();
        List<MovieInputData> l = new ArrayList<>();

        for (i = 0; i < n; i++) {
            if (movies.get(i).getGenres().contains(genre)) {
                l.add(movies.get(i));
            }
        }
        return l;
    }

    /**
     * Functia intoarce serialele care apartin genului genre
     * @param genre este genul pe baza caruia se cauta serialele
     * @return l intoarce lista de seriale identificate
     */
    public List<SerialInputData> serialsFromGenre(final String genre) {
        int i;
        int n = serials.size();
        List<SerialInputData> l = new ArrayList<>();

        for (i = 0; i < n; i++) {
            if (serials.get(i).getGenres().contains(genre)) {
                l.add(serials.get(i));
            }
        }

        return l;
    }

    /**
     * Dupa cum sugereaza si numele fuctiei, acesta intoarce primul videoclip
     * nevizualizat de utilizator dintr-un gen anume
     * @param username este numele utilizatorului
     * @param genre este tipul genului
     * @return returneaza titlul video-ului sau null, daca utilizatorul
     * a vazut toate video-urile din acel gen
     */
    public String videoUnseenByUser(final String username,
                                    final String genre) {
        // Mai intai caut utilizatorul dupa nume
        UserInputData user = findUser(username);
        // Deoarece in baza de date filmele apar inaintea serialelor, caut
        // mai intai in lista de filme un videoclip nevizualizat.
        // Identific apoi toate filmele care au genul genre
        List<MovieInputData> movieList = moviesFromGenre(genre);
        for (MovieInputData movieInputData : movieList) {
            if (user != null && !
                    user.getHistory().containsKey(movieInputData.getTitle())) {
                // Daca am gasit, returnez filmul
                return movieInputData.getTitle();
            }
        }

        // Daca utilizatorul a vazut toate filmele care au genul genre, fac
        // cautarea in lista de seriale.
        // Identific serialele care au genul genre
        List<SerialInputData> serialList = serialsFromGenre(genre);
        for (SerialInputData serialInputData : serialList) {
            if (user != null
                && !user.getHistory().containsKey(serialInputData.getTitle())) {
                // Daca l-am gasit, il returnez
                return serialInputData.getTitle();
            }
        }
        // Daca utilizatorul a vazut toate videoclipurile care contin genul
        // genre, returnez null.
        return null;
    }

    /**
     * Aici determin videoclipul din cel mai popular gen
     * @param username este numele utilizatorului
     * @return intoarce titlul videoclipului sau un mesaj de eroare
     * daca acesta nu a putut fi identificat.
     */
    public String popular(final String username) {
        List<String> list;
        // Determin genurile
        list = getGenres(movies, serials);
        UserInputData user = findUser(username);
        // Daca utilizatorul nu este PREMIUM, intorc un mesaj de eroare.
        if (user != null && user.getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }

        Map<String, Integer> genres = new LinkedHashMap<>();
        StringBuilder message =
                new StringBuilder("PopularRecommendation result: ");
        int i, n = list.size();

        // Retin popularitatea fiecarui gen
        for (i = 0; i < n; i++) {
            genres.put(list.get(i), getGenrePopularity(list.get(i)));
        }

        // Sortez lista linked descrescator.
        LinkedList<Map.Entry<String, Integer>> linked =
                new LinkedList<>(genres.entrySet());
        linked.sort((o1, o2) -> {
            Integer val1 = o1.getValue();
            Integer val2 = o2.getValue();
            return -val1.compareTo(val2);
        });

        // Acum iau genurile in ordinea descrescatoare a popularitatii lor
        // si determin videoclipul nevizualizat de utilizator

        for (i = 0; i < n; i++) {
            if (videoUnseenByUser(username, linked.get(i).getKey()) != null) {
                message.append(videoUnseenByUser(username, linked.get(i).getKey()));
                return message.toString();
            }
        }

        return "PopularRecommendation cannot be applied!";
    }

}
