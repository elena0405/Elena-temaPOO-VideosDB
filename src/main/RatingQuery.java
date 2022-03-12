package main;

import entertainment.Season;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class RatingQuery {
    private final List<SerialInputData> serials;
    private final List<MovieInputData> movies;

    public RatingQuery(final List<SerialInputData> serials,
                        final List<MovieInputData> movies) {
        this.movies = movies;
        this.serials = serials;
    }

    /**
     * Functia calculeaza rating-ul unui film
     * @param movie este filmul pentru care se calculeaza rating-ul
     * @return intoarce rating-ul mediu al filmului
     */
    public double getMovieRating(final MovieInputData movie) {
        double sumMovie = 0;
        int numberMovie = 0;

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
     * Functia calculeaza rating-ul unui sezon
     * @param s este sezonul pentru care se calculeaza rating-ul
     * @return intoarce rating-ul sezonului s
     */
    public double getSezonRating(final Season s) {
        double sumSeason = 0;
        int n = s.getRatings().size();

        for (Map.Entry<String, Double> entry : s.getRatings().entrySet()) {
            sumSeason += entry.getValue();
        }

        if (sumSeason == 0) {
            return 0;
        }
        return (sumSeason / n);
    }

    /**
     * Functia calculeaza rating-ul unui serial
     * @param s este serialul pentru care se calculeaza rating-ul
     * @return intoarce rating-ul serialului s
     */
    public double getSerialRating(final SerialInputData s) {
        double sum = 0;
        int i;
        int n = s.getNumberSeason();

        for (i = 0; i < n; i++) {
            if (getSezonRating(s.getSeasons().get(i)) != 0) {
                sum += getSezonRating(s.getSeasons().get(i));
            }
        }
        if (sum == 0) {
            return 0;
        }
        return (sum / n);
    }

    /**
     * Aici determin video-urile care respecta criteriile din input si
     * dupa ce le sortez le aleg pe primele n
     * @param n este numarul de videoclipuri ce se doreste a fi selectate
     * @param objectType este tipul videoclipului
     * @param sortType este tipul sortarii
     * @param filters reprezinta criteriile de selectie
     * @return intoarce titlurile video-urilor
     */
    public String videoRating(int n, final String objectType, final String sortType,
                               final List<List<String>> filters) {
        String filterYear = filters.get(0).get(0);
        String filterGenre = filters.get(1).get(0);
        StringBuilder message = new StringBuilder("Query result: [");

        // Daca video-urile sunt filme
        if (objectType.equals("movies")) {
            List<MovieInputData> newMovies = new ArrayList<>();

            for (MovieInputData movie : movies) {
                movie.setRatingAverage(getMovieRating(movie));
            }

            String movieYear;

            for (MovieInputData movie : movies) {
                if (movie.getRatingAverage() > 0) {
                    movieYear = String.valueOf(movie.getYear());
                    if (filterYear != null && filterGenre != null
                            && filterYear.equals(movieYear)
                            && movie.getGenres().contains(filterGenre)) {
                        newMovies.add(movie);
                    } else if (filterYear != null
                            && filterYear.equals(movieYear)
                            && filterGenre == null) {
                        newMovies.add(movie);
                    } else if (filterGenre != null
                            && movie.getGenres().contains(filterGenre)
                            && filterYear == null) {
                        newMovies.add(movie);
                    } else if (filterGenre == null && filterYear == null) {
                        newMovies.add(movie);
                    }
                }
            }

            // Sortez lista de filme dupa rating_average
            newMovies.sort(MovieInputData.getCompareByRating());
            if (sortType.equals("desc")) {
                Collections.reverse(newMovies);
            }

            if (n > newMovies.size()) {
                n = newMovies.size();
            }

            for (int i = 0; i < n; i++) {
                message.append(newMovies.get(i).getTitle());
                if (i < n - 1) {
                    message.append(", ");
                }
            }
        } else {
            // Daca video-urile sunt seriale
            List<SerialInputData> newSerials = new ArrayList<>();

            for (SerialInputData serial : serials) {
                serial.setRatingAverage(getSerialRating(serial));
            }

            String serialYear;

            for (SerialInputData serial : serials) {
                if (serial.getRatingAverage() > 0) {
                    serialYear = String.valueOf(serial.getYear());
                    if (filterYear != null
                            && filterGenre != null
                            && filterYear.equals(serialYear)
                            && serial.getGenres().contains(filterGenre)) {
                        newSerials.add(serial);
                    } else if (filters.get(0).get(0) == null
                            && filterGenre == null) {
                        newSerials.add(serial);
                    } else if (filterYear != null
                            && filterYear.equals(serialYear)
                            && filterGenre == null) {
                        newSerials.add(serial);
                    } else if (filterGenre != null
                            && serial.getGenres().contains(filterGenre)
                            && filters.get(0).get(0) == null) {
                        newSerials.add(serial);
                    }
                }
            }

            // Sortez lista de seriale dupa rating_average
            newSerials.sort(SerialInputData.getSortByRating());
            if (sortType.equals("desc")) {
                Collections.reverse(newSerials);
            }

            if (n > newSerials.size()) {
                n = newSerials.size();
            }

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
