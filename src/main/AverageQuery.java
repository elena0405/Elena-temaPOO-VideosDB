package main;

import entertainment.Season;
import fileio.ActorInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public final class AverageQuery {
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final List<ActorInputData> actors;

    public AverageQuery(final List<MovieInputData> movies,
                       final List<SerialInputData> serials,
                       final List<ActorInputData> actors) {
        this.movies = movies;
        this.actors = actors;
        this.serials = serials;
    }

    /**
     * Aceasta functie intoarce rating-ul mediu pentru un film dat ca parametru
     * @param movie reprezinta fimlul pentru care vreau sa calculez rating-ul
     * @return returneaza rating-ul calculat
     */
    public double getMovieRating(final MovieInputData movie) {
        int numberMovie = 0;
        double sumMovie = 0;
        // Iterez prin lista de rating-uri aferente unui film si daca
        // rating-ul oferit de un utilizator este nenul, il adaug la suma
        // si adun 1 la numberMovie, care reprezinta numarul de rating-uri
        // nenule
        for (Map.Entry<String, Double> entry: movie.getRatings().entrySet()) {
            if (entry.getValue() != 0) {
                sumMovie += entry.getValue();
                numberMovie++;
            }
        }
        if (sumMovie == 0) {
            return 0;
        }
        // Daca sumMovie este diferit de 0, returnez media aritmetica
        // a rating-urilor
        return (sumMovie / numberMovie);
    }

    /**
     * Aceasta functie calculeaza rating-ul unui sezon
     * @param s reprezinta sezonul pentru care vreau sa calculez rating-ul
     * @return returneaza rating-ul calculat
     */
    public double getSezonRating(final Season s) {
        int n = s.getRatings().size();
        double sum = 0;

        // Iterez prin lista de rating-uri aferenta unui sezon si aflu suma
        // tuturor rating-urilor
        for (Map.Entry<String, Double> entry : s.getRatings().entrySet()) {
            sum += entry.getValue();
        }

        if (sum == 0) {
            return 0;
        }
        // Daca suma este diferita de 0, returnez media aritmetica
        return (sum / n);
    }

    /**
     * Aceasta functie calculeaza rating-ul unui serial
     * @param s reprezinta serialul pentru care vreau sa calculez rating-ul
     * @return returneaza rating-ul calculat
     */
    public double getSerialRating(final SerialInputData s) {
        double sum = 0;
        int i;
        int n = s.getNumberSeason();
        // Pentru a calcula rating-ul unui serial, iterez prin lista de sezoane
        // corespunzatoare serialului curent si adaug in variabila sum
        // rating-ul mediu al fiecarui sezon
        for (i = 0; i < n; i++) {
            sum += getSezonRating(s.getSeasons().get(i));
        }

        if (sum == 0) {
            return 0;
        }
        // Daca suma este nenula, returnez media aritmetica a rating-urilor
        // unui serial
        return (sum / n);
    }

    /**
     * Aceasta functie calculeaza media pentru fiecare film si serial si
     * ordoneaza primii N actori dupa rating
     * @param n selecteaza primii N actori care indeplinesc cerintele din enunt
     * @param sortType reprezinta tipul sortarii
     * @return returneaza string-ul creat
     */

    public String average(int n, final String sortType) {
        double sum, average;
        int i, j, number;
        int m = actors.size();
        List<ActorInputData> newActors = new ArrayList<>();
        StringBuilder message = new StringBuilder("Query result: [");

        // Iterez prin lista de actori
        for (i = 0; i < m; i++) {
            sum = 0;
            number = 0;
            // Iterez prin lista de filme
            for (j = 0; j < movies.size(); j++) {
                if (actors.get(i).getFilmography().contains(movies.get(j).getTitle())
                        && getMovieRating(movies.get(j)) != 0) {
                    // Daca actorul curent a jucat in filmul cu titlul
                    // movies.get(j).getTitle() si daca rating-ul acestui
                    // film este nenul, atunci adaug acel rating la suma
                    // rating-urilor totale si incrementez numarul de
                    // rating-uri nenule
                    sum += getMovieRating(movies.get(j));
                    number++;
                }
            }

            // Iterez prin lista de seriale
            for (j = 0; j < serials.size(); j++) {
                // Daca actorul a jucat in serialul curent si daca acest
                // serial are rating-ul nenul, adaug rating-ul serialului in
                // variabila sum si incrementez numarul de videoclipuri care au
                // un rating nenul
                if (actors.get(i).getFilmography().contains(serials.get(j).getTitle())
                       && getSerialRating(serials.get(j)) != 0) {
                    sum += getSerialRating(serials.get(j));
                    number++;
                }
                if (sum == 0) {
                    average = 0;
                } else {
                    // Daca suma este nenula, calculez meda aritmetica a
                    // rating-urilor videoclipurilor in care a jucat un actor
                    average = sum / number;
                }
                actors.get(i).setRatingsAverage(average);
            }
        }


        for (i = 0; i < m; i++) {
            // Adaug actorii care au un rating mediu nenul intr-o lista noua
            if (actors.get(i).getRatingsAverage() > 0) {
                newActors.add(actors.get(i));
            }
        }

        // Sortez lista noua conform criteriului de sortare din input
        newActors.sort(ActorInputData.getCompareByRating());
        if (sortType.equals("desc")) {
            Collections.reverse(newActors);
        }

        if (n > newActors.size()) {
            n = newActors.size();
        }

        // Selectez primii n actori din lista noua si adaug numele
        // acestora in mesajul final
        for (i = 0; i < n; i++) {
            message.append(newActors.get(i).getName());
            if (i < n - 1) {
                message.append(", ");
            }
        }

        message.append("]");
        return message.toString();
    }

}
