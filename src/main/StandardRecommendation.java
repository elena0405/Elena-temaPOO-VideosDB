package main;

import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import java.util.List;

public final class StandardRecommendation {
    private final List<UserInputData> users;
    private final List<MovieInputData> movies;
    private final List<SerialInputData> serials;

    public StandardRecommendation(final List<UserInputData> users,
                                  final List<MovieInputData> movies,
                                  final List<SerialInputData> serials) {
        this.movies = movies;
        this.serials = serials;
        this.users = users;
    }

    /**
     * Functia determina daca un videoclip cu titlul title a fost
     * vizionat de utilizatorul user
     * @param title este titlul filmului
     * @param user este utilizatorul in al carui istoric voi face cautarea
     * @return intoarce 1 daca utilizatorul a vazut vide-ul si 0 in
     * caz contrar
     */
    public int viewedVideo(final String title, final UserInputData user) {
        if (user.getHistory().containsKey(title)) {
            return 1;
        }
        return 0;
    }

    /**
     * Functia determina utilizatorul care are numele username
     * @param username este numele utilizatorului
     * @return intoarce utilizatorul, daca este gasit sau null in caz
     * contrar
     */
    public UserInputData getUser(final String username) {
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
     * Functia determina primul videoclip care nu a fost vizualizat
     * de catre utilizatorul cu numele username
     * @param username este numele utilizaorului
     * @return intoarce videoclipul, daca a fost gasit sau un mesaj de eroare
     */
    public String standard(final String username) {
        StringBuilder message =
                new StringBuilder("StandardRecommendation result: ");
        int i;
        int n = movies.size();
        UserInputData user = getUser(username);

        for (i = 0; i < n; i++) {
            if (user != null
                    && viewedVideo(movies.get(i).getTitle(), user) == 0) {
                message.append(movies.get(i).getTitle());
                return message.toString();
            }
        }

        n = serials.size();
        for (i = 0; i < n; i++) {
            if (user != null
                    && viewedVideo(serials.get(i).getTitle(), user) == 0) {
                message.append(serials.get(i).getTitle());
                return message.toString();
            }
        }
        return ("StandardRecommendation cannot be applied!");
    }
}
