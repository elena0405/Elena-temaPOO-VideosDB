package fileio;

import entertainment.Season;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;
    private double ratingAverage;
    private int nrOfFavorites;
    private int duration;
    private int nrOfViews;

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.ratingAverage = 0;
        this.nrOfFavorites = 0;
        this.duration = 0;
        this.nrOfViews = 0;
    }

    private static final Comparator<SerialInputData> SORTBYDURATION = (o1, o2) -> {
        Integer d1 = o1.duration;
        Integer d2 = o2.duration;
        if (d1.equals(d2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return d1.compareTo(d2);
    };

    private static final Comparator<SerialInputData> SORTONLYBYRATING = (o1, o2) -> {
        Double rating1 = o1.ratingAverage;
        Double rating2 = o2.ratingAverage;
        return rating1.compareTo(rating2);
    };

    private static final Comparator<SerialInputData> SORTBYVIEWS = (o1, o2) -> {
        Integer v1 = o1.nrOfViews;
        Integer v2 = o2.nrOfViews;
        if (v1.equals(v2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return v1.compareTo(v2);
    };

    private static final Comparator<SerialInputData> SORTBYRATING = (o1, o2) -> {
        Double var1 = o1.ratingAverage;
        Double var2 = o2.ratingAverage;
        if (var1.equals(var2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return var1.compareTo(var2);

    };

    private static final Comparator<SerialInputData> SORTBYFAVORITE = (o1, o2) -> {
        Integer var1 = o1.nrOfFavorites;
        Integer var2 = o2.nrOfFavorites;
        if (var1.equals(var2)) {
            String s1 = o1.getTitle();
            String s2 = o2.getTitle();

            return s1.compareTo(s2);
        }
        return var1.compareTo(var2);
    };

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(final double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public int getNrOfFavorites() {
        return nrOfFavorites;
    }

    public void setNrOfFavorites(final int nrOfFavorites) {
        this.nrOfFavorites = nrOfFavorites;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public int getNrOfViews() {
        return nrOfViews;
    }

    public void setNrOfViews(final int nrOfViews) {
        this.nrOfViews = nrOfViews;
    }

    public static Comparator<SerialInputData> getSortByDuration() {
        return SORTBYDURATION;
    }

    public static Comparator<SerialInputData> getSortOnlyByRating() {
        return SORTONLYBYRATING;
    }

    public static Comparator<SerialInputData> getSortByViews() {
        return SORTBYVIEWS;
    }

    public static Comparator<SerialInputData> getSortByRating() {
        return SORTBYRATING;
    }

    public static Comparator<SerialInputData> getSortByFavorite() {
        return SORTBYFAVORITE;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
