package main;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class FavoriteQuery {
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;
    private final List<UserInputData> users;

    public FavoriteQuery(final List<UserInputData> users,
                            final List<MovieInputData> movies,
                            final List<SerialInputData> serials) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Functia determina numarul de aparitii al unui videoclip in lista de
     * videoclipuri favorite a tuturor utilizatorilor
     * @param users este o lista ce contine toti utilizatorii
     * @param title retine titlul videoclipului
     * @return nr returneaza numarul de aparitii al videoclipului in lista de
     * videoclipuri favorite a utilizatorilor
     */
    public int getNrOfAparitions(List<UserInputData> users,
                                       final String title) {
        int i;
        int n = users.size();
        int nr = 0;

        // Iterez prin lista de utilizatori ca sa pot determina numarul de
        // aparitii in listele de videoclipuri favorite ale utilizatorilor a
        // videoclipului cu titlul title
        for (i = 0; i < n; i++) {
            if (users.get(i).getFavoriteMovies().contains(title)) {
                nr++;
            }
        }

        return nr;
    }

    /**
     * Functia determina primele n videoclipuri care au cel mai mare numar
     * de aparitii in videoclipurile favorite ale utilizatorilor, sortate
     * conform criteriului de sortare dat ca parametru
     * @param n retine numarul de videoclipuri ce trebuiesc selectate
     * @param objectType retine tipul videoclipurilor, adica daca sunt filme
     *                    sau seriale
     * @param sortType reprezinta tipul sortarii cerute
     * @param filters este criteriul de selectie al videoclipurilor
     * @return message.toString() titlurile videoclipurilor selectate
     */
    public String favoriteQuery(int n, final String objectType,
                                 final String sortType, final List<List<String>> filters) {
        StringBuilder message = new StringBuilder("Query result: [");
        String filterYear = filters.get(0).get(0);
        String filterGenre = filters.get(1).get(0);

        // Daca video-urile sunt filme
        if (objectType.equals("movies")) {
            LinkedList<MovieInputData> list = new LinkedList<>();
            // Iterez prin ista de filme
            for (MovieInputData movie : movies) {
                movie.setAparitionsInFavorite(
                        getNrOfAparitions(users, movie.getTitle()));
                String movieYear = String.valueOf(movie.getYear());
                // Daca un film a aparut in minim o lista de videoclipuri
                // favorite a unui unui utilizator
                if (movie.getAparitionsInFavorite() != 0) {
                    // Conform criteriilor de filtrare, adaug sau nu filmul
                    // in noua lista de filme
                    if (filterYear != null && filterGenre != null
                            && filterYear.equals(movieYear)
                           && movie.getGenres().contains(filterGenre)) {
                        list.add(movie);
                    } else if (filterYear != null && filterYear.equals(movieYear)
                           && filterGenre == null) {
                        list.add(movie);
                    } else if (filterGenre != null
                           && movie.getGenres().contains(filterGenre)
                           && filterYear == null) {
                        list.add(movie);
                    } else if (filterGenre == null && filterYear == null) {
                        list.add(movie);
                    }
                }
            }

            // Sortez lista noua de filme
            list.sort(MovieInputData.getCompareByFavorite());
            if (sortType.equals("desc")) {
                Collections.reverse(list);
            }

            if (n > list.size()) {
                n = list.size();
            }

            // Adaug in mesajul final primele n filme care indeplinesc
            // conditiile cerute
            for (int i = 0; i < n; i++) {
                message.append(list.get(i).getTitle());
                if (i < n - 1) {
                    message.append(", ");
                }
            }
        } else {
            // Daca videourile sunt seriale
            String serialYear;
            LinkedList<SerialInputData> list = new LinkedList<>();
            // Iterez prin lista de seriale
            for (SerialInputData serial : serials) {
                serial.setNrOfFavorites(getNrOfAparitions(users, serial.getTitle()));
                serialYear = String.valueOf(serial.getYear());
                // Daca un serial apare in cel putin o lista de video-uri favorite
                if (serial.getNrOfFavorites() > 0) {
                    // Adaug sau nu serialul respectiv conform criteriilor
                    // de filtrare
                    if (filterYear != null && filterGenre != null
                            && filterYear.equals(serialYear)
                            && serial.getGenres().contains(filterGenre)) {
                        list.add(serial);
                    } else if (filterYear != null && filterYear.equals(serialYear)
                            && filterGenre == null) {
                        list.add(serial);
                    } else if (filterGenre != null && filterYear == null
                            && serial.getGenres().contains(filterGenre)) {
                        list.add(serial);
                    } else if (filterGenre == null && filterYear == null) {
                        list.add(serial);
                    }

                }
            }

           // Sortez lista de seriale
            list.sort(SerialInputData.getSortByFavorite());
            if (sortType.equals("desc")) {
                Collections.reverse(list);
            }

            if (n > list.size()) {
                n = list.size();
            }

            // Adaug in mesajul final primele n seriale care indeplinesc
            // conditiile din cerinta
            for (int i = 0; i < n; i++) {
                message.append(list.get(i).getTitle());
                if (i < n - 1) {
                    message.append(", ");
                }
            }
        }
        message.append("]");
        return message.toString();
    }
}
