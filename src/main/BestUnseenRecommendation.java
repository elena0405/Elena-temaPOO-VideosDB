
package main;

import entertainment.Season;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

public final class BestUnseenRecommendation {
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final List<UserInputData> users;

    public BestUnseenRecommendation(final List<MovieInputData> movies,
                                     final List<SerialInputData> serials,
                                     final List<UserInputData> users) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Aceasta functie gaseste utilizatorul cu numele username
     * @param username reprezinta numele utilizatorului dupa care se face
     *                 cautarea
     * @return returneaza utilizatorul, daca a fost gasit si null in caz
     * contrar
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
     * Aceasta functie calculeaza rating-ul unui film dat ca parametru
     * @param movie este filmul pentru care se calculeaza rating-ul mediu
     * @return returneaza rating-ul calculat
     */
    public double getMovieRating(final MovieInputData movie) {
        int numberMovie = 0;
        double sumMovie = 0;

        for (Map.Entry<String, Double> entry: movie.getRatings().entrySet()) {
            if (entry.getValue() != 0) {
                sumMovie += entry.getValue();
                numberMovie++;
            }
        }
        if (sumMovie == 0) {
            return 0;
        }
        return (sumMovie / numberMovie);
    }

    /**
     * Aceasta functie calculeaza rating-ul unui sezon
     * @param s retine sezonul pentru care se calculeaza rating-ul
     * @return returneaza rating-ul calculat
     */
    public double getSezonRating(final Season s) {
        int n = s.getRatings().size();
        double sum = 0;

        for (Map.Entry<String, Double> entry : s.getRatings().entrySet()) {
            sum += entry.getValue();
        }

        if (sum == 0) {
            return 0;
        }
        return (sum / n);
    }

    /**
     * Functia getSerialRating calculeaza rating-ul unui serial
     * @param s retine serialul pentru care se calculeaza rating-ul
     * @return returneaza rating-ul calculat
     */
    public double getSerialRating(final SerialInputData s) {
        double sum = 0;
        int i;
        int n = s.getNumberSeason();

        for (i = 0; i < n; i++) {
            sum += getSezonRating(s.getSeasons().get(i));
        }

        if (sum == 0) {
            return 0;
        }
        return (sum / n);
    }

    /**
     * Aceasta este functia propriu-zisa de determinare a videoclipului
     * nevizualizat de catre utilizator care are cel mai mare rating
     * @param username retine numele utilizatorului
     * @return returneaza sirul ce contine numele videoclipului
     */
    public String bestUnseen(final String username) {
        Map<String, Double> videos = new LinkedHashMap<>();
        StringBuilder message = new
                StringBuilder("BestRatedUnseenRecommendation result: ");
        int i, j, n = movies.size(), m = serials.size();

        UserInputData user = findUser(username);

        // Calculez rating-ul mediu pentru fiecare film.
        for (i = 0; i < n; i++) {
            movies.get(i).setRatingAverage(getMovieRating(movies.get(i)));
        }

        // Calculez rating-ul mediu pentru fiecare serial
        for (j = 0; j < m; j++) {
            serials.get(j).setRatingAverage(getSerialRating(serials.get(j)));
        }

        for (i = 0; i < n; i++) {
            // Daca utilizatorul cu numele username a fost identificat si daca
            // nu s-a uitat la filmul curent, adaug campurile necesare din
            // variabila movies.get(i) intr-o lista noua
            if (user != null && !user.getHistory().containsKey(movies.get(i).getTitle())) {
                videos.put(movies.get(i).getTitle(), movies.get(i).getRatingAverage());
            }
        }

        for  (i = 0; i < m; i++) {
            // Daca utilizatorul cu numele user a fost identificat corect si daca
            // nu s-a uitat la serialul curent, adaug campurile necesare din
            // variabila serials.get(i) intr-o lista noua
            if (user != null && !user.getHistory().containsKey(serials.get(i).getTitle())) {
                videos.put(serials.get(i).getTitle(), serials.get(i).getRatingAverage());
            }
        }

       LinkedList<Map.Entry<String, Double>> list = new LinkedList<>(videos.entrySet());

        // Sortez lista list
       list.sort((o1, o2) -> {
           Double rating1;
           rating1 = o1.getValue();
           Double rating2;
           rating2 = o2.getValue();
           return -rating1.compareTo(rating2);
       });

       // Selectez primul video din lisat sortata descrescator dupa rating
       for (Map.Entry<String, Double> entry : list) {
           message.append(entry.getKey());
           return message.toString();
       }

       return ("BestRatedUnseenRecommendation cannot be applied!");
    }
}
