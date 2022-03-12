package main;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class MostViewedQuery {
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final List<UserInputData> users;

    public MostViewedQuery(final List<MovieInputData> movies,
                            final List<SerialInputData> serials,
                            final List<UserInputData> users) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Functia determina numarul de vizualizari a videoclipului cu
     * titlul title
     * @param users reprezinta lista de utilizatori
     * @param title este titlul videoclipului
     * @return number returneaza numarul de vizualizari a video-ului
     * cu titlul title
     */
    public int getNrOfViews(List<UserInputData> users,
                               final String title) {
        int i;
        int n = users.size();
        int number = 0;

        // Iterez prin lisat de utilizatori si calculez numarul de vizionari
        for (i = 0; i < n; i++) {
            if (users.get(i).getHistory().containsKey(title)) {
                number += users.get(i).getHistory().get(title);
            }
        }

        return number;
    }

    /**
     * Functia care determina primele n videoclipuri ce vor fi sortate in
     * functie de numarul de vizionari al tuturor utilizatorilor
     * @param n retine numarul de video-uri ce trebuiesc selectate
     * @param objectType retine tipul video-urilor
     * @param filters retine criteriile de selectie
     * @param sortType retine tipul sortarii
     * @return message.toString() intoarce un string cu titlurile video-urilor selectate
     */
    public String mostViewed(int n, final String objectType, final List<List<String>> filters,
                              final String sortType) {
        String videoYear;
        List<String> videoGenres;
        String filterYear = filters.get(0).get(0);
        String filterGenre = filters.get(1).get(0);

        StringBuilder message = new StringBuilder("Query result: [");

        // Daca video-urile sun filme
        if (objectType.equals("movies")) {
            LinkedList<MovieInputData> newMovies = new LinkedList<>();

            for (MovieInputData movie : movies) {
                // Calculez pentru fiecare film numarul de vizonari
                movie.setNrOfViewes(getNrOfViews(users,
                        movie.getTitle()));
                videoYear = String.valueOf(movie.getYear());
                videoGenres = movie.getGenres();
                // Selectez filmele dupa criteriile de sortare din input
                if (movie.getNrOfViewes() > 0) {
                    if (filterGenre != null
                           && videoYear.equals(filterYear)
                           && videoGenres.contains(filterGenre)) {
                        newMovies.add(movie);
                    } else if (filterGenre == null && filterYear != null
                            && filterYear.equals(videoYear)) {
                        newMovies.add(movie);
                    } else if (filterGenre != null && filterYear == null
                            && videoGenres.contains(filterGenre)) {
                        newMovies.add(movie);
                    } else if (filterGenre == null && filterYear == null) {
                        newMovies.add(movie);
                    }
                }
            }

            // Sortez filmele selectate dupa criteriul din input
            newMovies.sort(MovieInputData.getCompareByViews());
            if (sortType.equals("desc")) {
                Collections.reverse(newMovies);
            }

            if (n > newMovies.size()) {
                n = newMovies.size();
            }

            // Aleg primele n filme selectate si sortate
            for (int i = 0; i < n; i++) {
                message.append(newMovies.get(i).getTitle());
                if (i < n - 1) {
                    message.append(", ");
                }
            }
        } else {
            // Daca video-urile sunt seriale
            LinkedList<SerialInputData> newSerials = new LinkedList<>();

            for (SerialInputData serial : serials) {
                // Determin numarul de vizualizari pt fiecare serial
                serial.setNrOfViews(getNrOfViews(users, serial.getTitle()));
                videoGenres = serial.getGenres();
                videoYear = String.valueOf(serial.getYear());
                // Selectez serialele care ma intereseaza
                if (serial.getNrOfViews() > 0) {
                    if (filterGenre != null && filterYear != null
                            && videoGenres.contains(filterGenre)
                            && filterYear.equals(videoYear)) {
                        newSerials.add(serial);
                    } else if (filterGenre == null && filterYear != null
                            && filterYear.equals(videoYear)) {
                        newSerials.add(serial);
                    } else if (filterGenre != null && filterYear == null
                            && videoGenres.contains(filterGenre)) {
                        newSerials.add(serial);
                    } else if (filterGenre == null && filterYear == null) {
                        newSerials.add(serial);
                    }
                }
            }

            // Sortez serialele selectate
            newSerials.sort(SerialInputData.getSortByViews());
            if (sortType.equals("desc")) {
                Collections.reverse(newSerials);
            }

            if (n > newSerials.size()) {
                n = newSerials.size();
            }

            // Pun in message titlurile serialelor selectate
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
