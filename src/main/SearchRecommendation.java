package main;

import entertainment.Season;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class SearchRecommendation {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;

    public SearchRecommendation(final List<UserInputData> users,
                                 final List<SerialInputData> serials,
                                 final List<MovieInputData> movies) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Aceasta functie determina utilizatorul in functie de nume
     * @param username este numele utilizatorului
     * @return intoarce utilizatorul, daca acesta este identificat sau
     * null in caz contrar
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
     * Functia calculeaza rating-ul unui film dat ca parametru
     * @param movie este filmul pentru care se calculeaza rating-ul
     * @return intoarce rating-ul mediu al filmului
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
     * Aici calculez rating-ul unui sezon dat ca parametru
     * @param s este sezon pentru care vreau sa determin rating-ul
     * @return intoarce rating-ul sezonului
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
     * Functia determina rating-ul serialului dat ca parametru
     * @param s este serialul pentru care se calculeaza rating-ul
     * @return intoarce rating-ul
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
     * Functia intoarce videoclipurile nevizualizate de utilizator, care au
     * genul genre; aceste videoclipuri vor fi sortate crescator dupa rating
     * @param username este numele utilizatorului
     * @param genre este genul dupa care se face cautarea
     * @return intoarce titlurile video-urilor gasite
     */
    public String search(final String username, final String genre) {
        StringBuilder message =
                new StringBuilder("SearchRecommendation result: [");
        UserInputData user = findUser(username);
        if (user != null && user.getSubscriptionType().equals("BASIC")) {
            return "PopularRecommendation cannot be applied!";
        }

        Map<String, Double> videos = new HashMap<>();

        for (MovieInputData movie : movies) {
            movie.setRatingAverage(getMovieRating(movie));
        }

        for (SerialInputData serial : serials) {
            serial.setRatingAverage(getSerialRating(serial));
        }

        for (MovieInputData movie : movies) {
            if (user != null && !user.getHistory().containsKey(movie.getTitle())
                    && movie.getGenres().contains(genre) && genre != null) {
                videos.put(movie.getTitle(), movie.getRatingAverage());
            }
        }

        for (SerialInputData serial : serials) {
            if (user != null
                    && !user.getHistory().containsKey(serial.getTitle())
                    && serial.getGenres().contains(genre) && genre != null) {
                videos.put(serial.getTitle(), serial.getRatingAverage());
            }
        }

        LinkedList<Map.Entry<String, Double>> list =
                new LinkedList<>(videos.entrySet());
        list.sort((o1, o2) -> {
            Double rating1 = o1.getValue();
            Double rating2 = o2.getValue();
            if (rating1.equals(rating2)) {
                String s1 = o1.getKey();
                String s2 = o2.getKey();
                return s1.compareTo(s2);
            }
            return rating1.compareTo(rating2);
        });

        int ok = 0;
        int n = list.size();
        int index = 0;

        for (Map.Entry<String, Double> entry : list) {
            message.append(entry.getKey());
            if (index < n - 1) {
                message.append(", ");
            }
            index++;
            ok = 1;
        }
        if (ok == 1) {
            message.append("]");
            return message.toString();
        }

        return "SearchRecommendation cannot be applied!";
    }
}
