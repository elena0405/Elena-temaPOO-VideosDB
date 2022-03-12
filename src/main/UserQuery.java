package main;

import fileio.UserInputData;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public final class UserQuery {
    private final List<UserInputData> users;

    public UserQuery(final List<UserInputData> users) {
        this.users = users;
    }

    /**
     * Aici sortez utilizatorii care au dat cele mai multe rating-uri si ii
     * aleg pe primii n
     * @param n este numarul de utilizatori ce se doreste a fi selectati
     * @param sortType este tipul sortarii
     * @return returneaza numele utilizatorilor identificati
     */
    public String numberOfRatings(int n, final String sortType) {
        int i, count = 0;
        StringBuilder message = new StringBuilder("Query result: [");
        List<String> s = new ArrayList<>();

        users.sort(UserInputData.getSortByRating());
        if (sortType.equals("desc")) {
            Collections.reverse(users);
        }

        for (i = 0; i < users.size(); i++) {
            if (users.get(i).getRatingsHistory().size() != 0) {
                s.add(users.get(i).getUsername());
                count++;
            }
        }

        if (n > count) {
            n = count;
        }

       for (i = 0; i < n; i++) {
           message.append(s.get(i));
           if (i < n - 1) {
               message.append(", ");
           }
       }

       message.append("]");

       return message.toString();
    }
}
