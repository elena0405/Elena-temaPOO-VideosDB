package main;

import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class LongestQuery {
    private final List<SerialInputData> serials;
    private final List<MovieInputData> movies;

    public LongestQuery(final List<SerialInputData> serials, final List<MovieInputData> movies) {
        this.movies = movies;
        this.serials = serials;
    }

    /**
     * Functia calculeaza durata unui serial
     * @param s este serialul pentru care se calculeaza durata
     * @return duration retureaza durata calculata
     */
    public int getSerialDuration(final SerialInputData s) {
        int i;
        int n = s.getSeasons().size();
        int duration = 0;

        // Calculez durata serialului adunand durata fiecarui sezon
        // in variabila duration
        for (i = 0; i < n; i++) {
            duration = duration + s.getSeasons().get(i).getDuration();
        }

        return duration;
    }

    /**
     * Functia sorteaza video-urile dupa durata lor dupa un criteriu de
     * sortare dat ca parametru si intoarce primele n videoclipuri sortate
     * @param n retine numarul de videoclipuri ce trebuiesc returnate
     * @param type reprezinta tipul videoclipurilor
     * @param sortType reprezinta criteriul de sortare
     * @param filters este lista cu campurile pe baza carora
     *                se fac alegerile
     * @return message.toString() returneaza titlurile videoclipurilor
     * selectate
     */
    public String longest(int n, final String type, final String sortType,
                          final List<List<String>> filters) {
        int count;
        String videoYear;
        List<String> videoGenres;
        String filterYear = filters.get(0).get(0);
        String filterGenre = filters.get(1).get(0);
        StringBuilder message = new StringBuilder("Query result: [");

        // Daca videoclipurile sunt filme
        if (type.equals("movies")) {
            count = 0;
            // In aceasta lista retin filmele care indeplinesc
            // criteriile date din input
            List<MovieInputData> newMovies = new ArrayList<>();

            // Iterez prin lista de filme
            for (MovieInputData movie : movies) {
                videoYear = String.valueOf(movie.getYear());
                videoGenres = movie.getGenres();
                // In functie de valorile din campurile de filtrare date
                // din input, adaug filmele in lisat noua de filme sau nu
                if (filterYear != null && filterGenre != null
                        && filterYear.equals(videoYear)
                        && videoGenres.contains(filterGenre)) {
                    newMovies.add(movie);
                    count++;
                } else if (filterGenre != null && filterYear == null
                       && videoGenres.contains(filterGenre)) {
                    newMovies.add(movie);
                    count++;
                } else if (filterGenre == null
                        && videoYear.equals(filterYear)) {
                    newMovies.add(movie);
                    count++;
                } else if (filterGenre == null && filterYear == null) {
                    newMovies.add(movie);
                    count++;
                }
            }

            // Sortez lista noa de filme
            newMovies.sort(MovieInputData.getCompareByDuration());
            if (sortType.equals("desc")) {
                Collections.reverse(newMovies);
            }

            // Resetez valoarea lui n daac este cazul.
            if (count < n) {
                n = count;
            }

            // Adaug in message titlurile primelor n filme selectate
            for (int i = 0; i < n; i++) {
                message.append(newMovies.get(i).getTitle());
                if (i < n - 1) {
                    message.append(", ");
                }
            }
        } else {   // Daca videoclipurile sunt seriale

            // Calculez mai intai durata fiecarui serial
            for (SerialInputData serial : serials) {
                serial.setDuration(getSerialDuration(serial));
            }

            // Sortez lisat de seriale
            serials.sort(SerialInputData.getSortByDuration());
            if (sortType.equals("desc")) {
                Collections.reverse(serials);
            }

            count = 0;
            List<SerialInputData> newSerials = new ArrayList<>();

            // Din lisat de seriale sortate, le selectez doar pe cele care
            // indeplinesc cerintele din input si le retin intr-o lista noua
            for (SerialInputData serial : serials) {
                videoYear = String.valueOf(serial.getYear());
                videoGenres = serial.getGenres();
                if (filterGenre != null
                        && videoYear.equals(filterYear)
                        && videoGenres.contains(filterGenre)) {
                    newSerials.add(serial);
                    count++;
                } else if (filterGenre == null && filterYear != null
                        && filterYear.equals(videoYear)) {
                    newSerials.add(serial);
                    count++;
                } else if (filterGenre != null && filterYear == null
                        && videoGenres.contains(filterGenre)) {
                    newSerials.add(serial);
                    count++;
                } else if (filterGenre == null
                        && filterYear == null) {
                    newSerials.add(serial);
                    count++;
                }
            }

            // Daca este cazul, resetez valoarea lui n.
            if (n > count) {
                n = count;
            }

            // Adaug titlurile primelor n seriale in message
            for (int i = 0; i < n; i++) {
                message.append(newSerials.get(i).getTitle());
                if (i < n - 1) {
                    message.append(", ");
                }
            }
        }
        message.append("]");
        return message.toString();
    }
}
