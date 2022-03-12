package fileio;

import actor.ActorsAwards;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    private double ratingsAverage;
    private int numberAwards;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        this.ratingsAverage = 0;
        this.numberAwards = 0;
    }

    private static final Comparator<ActorInputData> COMPAREBYNRAWARDS = (o1, o2) -> {
        Integer nr1 = o1.numberAwards;
        Integer nr2 = o2.numberAwards;
        if (nr1.equals(nr2)) {
            String s1 = o1.name;
            String s2 = o2.name;

            return s1.compareTo(s2);
        }
        return nr1.compareTo(nr2);
    };

    private static final Comparator<ActorInputData> COMPAREBYRATING = (o1, o2) -> {
        Double rating1 = o1.ratingsAverage;
        Double rating2 = o2.ratingsAverage;
        if (rating1.equals(rating2)) {
            String s1 = o1.name;
            String s2 = o2.name;

            return s1.compareTo(s2);
        }
        return rating1.compareTo(rating2);
    };

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public int getNumberAwards() {
        return numberAwards;
    }

    public void setNumberAwards(final int numberAwards) {
        this.numberAwards = numberAwards;
    }

    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }

    public double getRatingsAverage() {
        return ratingsAverage;
    }

    public void setRatingsAverage(final double ratingsAverage) {
        this.ratingsAverage = ratingsAverage;
    }

    public static Comparator<ActorInputData> getCompareByNrAwards() {
        return COMPAREBYNRAWARDS;
    }

    public static Comparator<ActorInputData> getCompareByRating() {
        return COMPAREBYRATING;
    }

    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }

}
