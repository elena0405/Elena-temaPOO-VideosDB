package main;

import entertainment.Season;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.List;
import java.util.Map;

public final class Rating {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;

    public Rating(final List<UserInputData> users,
                  final List<MovieInputData> movies,
                  final List<SerialInputData> serials) {
        this.users = users;
        this.movies = movies;
        this.serials = serials;
    }

    /**
     * Functia determina utilizatorul in functie de nume
     * @param username este numele utilizatorului
     * @return intoarce utilizatorul sau null in cazul in care
     * nu a fost gasit
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
     * Functia cauta un film dupa titlu
     * @param title este numele filmului cautat
     * @return intoarce filmul daca a fost gasit sau null
     * in caz contrar
     */
    public MovieInputData findMovie(final String title) {
        for (MovieInputData movie : movies) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Functia cauta un serial dupa titlu
     * @param title este titlul serialului
     * @return intoarce serialul sau null daca nu este gasit
     */
    public SerialInputData findSerial(final String title) {
        int i;
        int n = serials.size();

        for (i = 0; i < n; i++) {
            if (serials.get(i).getTitle().equals(title)) {
                return serials.get(i);
            }
        }

        return null;
    }

    /**
     * Functia determina daca un video a fost vizualizat de catre utilizatorul
     * al carui istoric este retinut in map
     * @param title este titlul video-ului
     * @param map o variabila de tipul Map ce retine istoricul utilizatorului
     * @return intoarce 1 daca video-ul a fost vizionat sau 0 in caz contrar
     */
    public int viewedVideo(final String title, final Map<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getKey().equals(title)) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * Functia determina daca utilizatorul cu numele username
     * a dat rating la video-ul ale carui rating-uri sunt retinute
     * in map
     * @param username este numele utilizatorului
     * @param map este istoricul rating-urilor pentru acel video
     * @return returneaza 1 daca video-ul a primit rating de la
     * utilizatorul cu numele username si 0 in caz contrar
     */
    public int videoWithRating(final String username, final Map<String, Double> map) {
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getKey().equals(username)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Functia adauga rating pentru video-ul cat ca parametru
     * @param username este numele utilizatorului
     * @param title este titlul video-ului
     * @param seasonNumber este numarul de sezoane corespunzatoare
     *                     video-ului; daca video-ul este film, acesta
     *                     va fi 0
     * @param rating este valoarea ce trebuie adaugata pe post de rating
     * @return intoarce un mesaj de eroare sau de succes
     */
    public String rating(final String username, final String title, final int seasonNumber,
                         final Double rating) {
        UserInputData user = findUser(username);
        String message;

        // Daca videoclipul a fost vizinat
        if (user != null && viewedVideo(title, user.getHistory()) == 1) {
            // Daca videoclipul e film
            if (seasonNumber == 0) {
                // Caut filmul in lista de filme
                MovieInputData movie = findMovie(title);
                // Daca videoclipul nu a primit rating de la user-ul curent
                if (movie != null && videoWithRating(username, movie.getRatings()) == 0) {
                    movie.getRatings().put(username, rating);
                    user.getRatingsHistory().put(title, rating);
                    message = "success -> " + title + " was rated with " + rating + " by "
                            + username;
                } else {
                    message = "error -> " + title + " has been already rated";
                }
            } else {
                // Caut serialul in lista de seriale
                SerialInputData serial = findSerial(title);
                // Caut sezonul in lista de sezoane aferente serialului gasit
                Season season = null;
                if (serial != null) {
                    season = serial.getSeasons().get(seasonNumber - 1);
                }
                // Daca sezonul nu a primit rating de la user-ul curent
                if (season != null && videoWithRating(username, season.getRatings()) == 0) {
                    season.getRatings().put(username, rating);
                    user.getRatingsHistory().put(title, rating);
                    message = "success -> " + title + " was rated with " + rating + " by "
                            + username;
                } else {
                    message = "error -> " + title + " has been already rated";
                }
            }
        } else {
            message = "error -> " + title + " is not seen";
        }

        return message;
    }
}
