package fileio;

import java.util.Comparator;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;
    private Map<String, Double> ratings;
    private double ratingAverage;
    private int aparitionsInFavorite;
    private int nrOfViewes;

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new HashMap<>();
        this.ratingAverage = 0;
        this.aparitionsInFavorite = 0;
        this.nrOfViewes = 0;
    }

    private static final Comparator<MovieInputData> COMPAREBYRATING = (o1, o2) -> {
        Double var1 = o1.ratingAverage;
        Double var2 = o2.ratingAverage;
        if (var1.equals(var2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return var1.compareTo(var2);
    };

    private static final Comparator<MovieInputData> COMPAREONLYBYRATING = (o1, o2) -> {
        Double r1 = o1.ratingAverage;
        Double r2 = o2.ratingAverage;
        return r1.compareTo(r2);
    };

    private static final Comparator<MovieInputData> COMPAREBYDURATION = (o1, o2) -> {
        Integer d1 = o1.duration;
        Integer d2 = o2.duration;
        if (d1.equals(d2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return d1.compareTo(d2);
    };

    private static final Comparator<MovieInputData> COMPAREBYFAVORITE = (o1, o2) -> {
        Integer var1 = o1.aparitionsInFavorite;
        Integer var2 = o2.aparitionsInFavorite;
        if (var1.equals(var2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return var1.compareTo(var2);
    };

    private static final Comparator<MovieInputData> COMPAREBYVIEWS = (o1, o2) -> {
        Integer v1 = o1.nrOfViewes;
        Integer v2 = o2.nrOfViewes;
        if (v1.equals(v2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return v1.compareTo(v2);
    };

    public int getDuration() {
        return duration;
    }

    public int getAparitionsInFavorite() {
        return aparitionsInFavorite;
    }

    public void setAparitionsInFavorite(final int aparitionsInFavorite) {
        this.aparitionsInFavorite = aparitionsInFavorite;
    }

    public Map<String, Double> getRatings() {
        return ratings;
    }

    public void setRatings(final HashMap<String, Double> ratings) {
        this.ratings = ratings;
    }

    public void setRatingAverage(final double average) {
        this.ratingAverage = average;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public void setNrOfViewes(final int nrOfViewes) {
        this.nrOfViewes = nrOfViewes;
    }

    public int getNrOfViewes() {
        return nrOfViewes;
    }

    public static Comparator<MovieInputData> getCompareByRating() {
        return COMPAREBYRATING;
    }

    public static Comparator<MovieInputData> getCompareOnlyByRating() {
        return COMPAREONLYBYRATING;
    }

    public static Comparator<MovieInputData> getCompareByDuration() {
        return COMPAREBYDURATION;
    }

    public static Comparator<MovieInputData> getCompareByFavorite() {
        return COMPAREBYFAVORITE;
    }

    public static Comparator<MovieInputData> getCompareByViews() {
        return COMPAREBYVIEWS;
    }

    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}
