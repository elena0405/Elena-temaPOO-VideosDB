package fileio;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Information about an user, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class UserInputData {
    /**
     * User's username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the movies seen
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteMovies;

    private final Map<String, Double> ratingsHistory;

    public UserInputData(final String username, final String subscriptionType,
                         final Map<String, Integer> history,
                         final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.ratingsHistory = new HashMap<>();
    }

    private static final Comparator<UserInputData> SORTBYRATING = (o1, o2) -> {
        Integer nr1 = o1.ratingsHistory.size();
        Integer nr2 = o2.ratingsHistory.size();
        if (nr1.equals(nr2)) {
            return o1.username.compareTo(o2.username);
        }
        return nr1.compareTo(nr2);
    };

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public Map<String, Double> getRatingsHistory() {
        return ratingsHistory;
    }

    public static Comparator<UserInputData> getSortByRating() {
        return SORTBYRATING;
    }

    @Override
    public String toString() {
        return "UserInputData{" + "username='"
                + username + '\'' + ", subscriptionType='"
                + subscriptionType + '\'' + ", history="
                + history + ", favoriteMovies="
                + favoriteMovies + '}';
    }
}
