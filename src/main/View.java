package main;

import fileio.UserInputData;

import java.util.List;

public final class View {
    private final List<UserInputData> users;

    public View(final List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Aceasta functie cauta un utilizator dupa nume
     * @param username este numele utilizatorului
     * @return intoarce utilizatorul, daca a fost gasit sau null in caz contrar
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
     * Aici incrementez numarul de vizualizari al unui videoclip, daca
     * a fost vizionat de utilizator
     * @param username este numele utilizatorului
     * @param title este titlul videoclipului
     * @return intoarce un mesaj in functie de tipul actiunii
     */
    public String viewVideo(final String username, final String title) {
        // Identific utilizatorul
        UserInputData user = findUser(username);
        if (user.getHistory().containsKey(title)) {
            // Daca videoclipul a fost vizionat incrementez numarul de vizualizari
            user.getHistory().put(title, user.getHistory().get(title) + 1);
        } else {
            user.getHistory().put(title, 1);
            // Daca nu a fost vizionat, il adaug in istoric.
        }
        return  "success -> " + title + " was viewed with total views of "
                + user.getHistory().get(title);
    }
}
