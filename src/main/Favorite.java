package main;

import fileio.UserInputData;

import java.util.List;

public final class Favorite {
   private final List<UserInputData> users;

    public Favorite(final List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Aceasta functie gaseste utilizatorul dupa nume
     * @param username retine numele utilizatorului
     * @return user returneaza utilizatorul gasit; daca nu l-a
     * gasit, returneaza null
     */
    public UserInputData findUser(final String username) {
        UserInputData user = null;
        int i;
        int n = this.users.size();

        for (i = 0; i < n; i++) {
            if (this.users.get(i).getUsername().equals(username)) {
                user = this.users.get(i);
                break;
            }
        }

        return user;
    }

    /**
     * Functia adauga un videoclip la lista de videoclipuri favorite
     * a utilizatorului cu numele username
     * @param username reprezinta numele utilizatorului, in a carui
     *                 lista de videoclipuri favorite ar trebui sa se
     *                 adauge videoclipul, daca a fost vizionat si daca
     *                 nu a mai fost adaugat
     * @param title retine titlul videoclipului ce trebuie adaugat in
     *              lista de favorite
     * @return message returneaza un mesaj care indica daca videoclipul a
     * fost adaugat sau nu
     */
    public String favorite(final String username, final String title) {
        UserInputData user = findUser(username);
        String message = "error -> " + title + " is not seen";

        // Verific mai intai daca videoul a fost vizionat
        if (user.getHistory().containsKey(title)) {
            // Daca da, verific daca a fost deja adaugat in lista de videouri
            // favorite
            if (user.getFavoriteMovies().contains(title)) {
                message = "error -> " + title + " is already in favourite list";
            } else {
                user.getFavoriteMovies().add(title);
                message = "success -> " + title + " was added as favourite";
            }
        }
        return message;
    }
}
