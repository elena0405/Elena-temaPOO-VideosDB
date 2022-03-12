package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        List<UserInputData> users = input.getUsers();
        List<ActionInputData> commands = input.getCommands();
        List<MovieInputData> movies = input.getMovies();
        List<SerialInputData> serials = input.getSerials();
        List<ActorInputData> actors = input.getActors();

        int i;
        int n = commands.size();
        String message;

        for (i = 0; i < n; i++) {
            if (commands.get(i).getActionType().equals("command")) {
                if (commands.get(i).getType().equals("favorite")) {
                    Favorite f = new Favorite(users);
                    message = f.favorite(commands.get(i).getUsername(),
                            commands.get(i).getTitle());
                    arrayResult.add(
                            fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                } else if (commands.get(i).getType().equals("view")) {
                    View v = new View(users);
                    message = v.viewVideo(commands.get(i).getUsername(),
                            commands.get(i).getTitle());
                    arrayResult.add(
                            fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                } else if (commands.get(i).getType().equals("rating")) {
                    Rating r = new Rating(users, movies, serials);
                    message = r.rating(commands.get(i).getUsername(),
                            commands.get(i).getTitle(),
                            commands.get(i).getSeasonNumber(),
                            commands.get(i).getGrade());
                    arrayResult.add(
                            fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                }
            } else if (commands.get(i).getActionType().equals("query")) {
                if (commands.get(i).getObjectType().equals("users")) {
                    UserQuery userQuery = new UserQuery(users);
                    message = userQuery.numberOfRatings(
                            commands.get(i).getNumber(),
                            commands.get(i).getSortType());
                    arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                            null, message));
                } else if (commands.get(i).getObjectType().equals("actors")) {
                    if (commands.get(i).getCriteria().equals("average")) {
                        AverageQuery averageQuery = new AverageQuery(movies, serials, actors);
                        message = averageQuery.average(commands.get(i).getNumber(),
                                commands.get(i).getSortType());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    } else if (commands.get(i).getCriteria().equals("awards")) {
                        AwardsQuery a = new AwardsQuery(actors);
                        message = a.awards(commands.get(i).getFilters(),
                                commands.get(i).getSortType());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    } else if (commands.get(i).getCriteria().equals("filter_description")) {
                        FilterDescriptionQuery f = new FilterDescriptionQuery(actors);
                        message = f.filter(commands.get(i).getFilters(),
                                commands.get(i).getSortType());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    }
                } else if (commands.get(i).getObjectType().equals("movies")
                            || commands.get(i).getObjectType().equals("shows")) {
                        if (commands.get(i).getCriteria().equals("ratings")) {
                            RatingQuery ratingQuery = new RatingQuery(serials, movies);
                            message = ratingQuery.videoRating(commands.get(i).getNumber(),
                                    commands.get(i).getObjectType(),
                                    commands.get(i).getSortType(),
                                    commands.get(i).getFilters());
                            arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                        } else if (commands.get(i).getCriteria().equals("favorite")) {
                            FavoriteQuery f = new FavoriteQuery(users, movies, serials);
                            message = f.favoriteQuery(commands.get(i).getNumber(),
                                    commands.get(i).getObjectType(),
                                    commands.get(i).getSortType(), commands.get(i).getFilters());
                            arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                        } else if (commands.get(i).getCriteria().equals("longest")) {
                            LongestQuery l = new LongestQuery(serials, movies);
                            message = l.longest(commands.get(i).getNumber(),
                                    commands.get(i).getObjectType(),
                                    commands.get(i).getSortType(),
                                    commands.get(i).getFilters());
                            arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                        } else if (commands.get(i).getCriteria().equals("most_viewed")) {
                            MostViewedQuery m = new MostViewedQuery(movies, serials, users);
                            message = m.mostViewed(commands.get(i).getNumber(),
                                    commands.get(i).getObjectType(),
                                    commands.get(i).getFilters(),
                                    commands.get(i).getSortType());
                            arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                    null, message));
                        }
                    }
                } else if (commands.get(i).getActionType().equals("recommendation")) {
                    if (commands.get(i).getType().equals("standard")) {
                        StandardRecommendation s =
                                new StandardRecommendation(users, movies, serials);
                        message = s.standard(commands.get(i).getUsername());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    } else if (commands.get(i).getType().equals("best_unseen")) {
                        BestUnseenRecommendation b =
                                new BestUnseenRecommendation(movies, serials, users);
                        message = b.bestUnseen(commands.get(i).getUsername());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    } else if (commands.get(i).getType().equals("popular")) {
                        PopularRecommendation p =
                                new PopularRecommendation(users, movies, serials);
                        message = p.popular(commands.get(i).getUsername());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    } else if (commands.get(i).getType().equals("favorite")) {
                        FavoriteRecommendation f =
                                new FavoriteRecommendation(movies, serials, users);
                        message = f.favorite(commands.get(i).getUsername());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    } else if (commands.get(i).getType().equals("search")) {
                        SearchRecommendation s =
                                new SearchRecommendation(users, serials, movies);
                        message = s.search(commands.get(i).getUsername(),
                                commands.get(i).getGenre());
                        arrayResult.add(fileWriter.writeFile(commands.get(i).getActionId(),
                                null, message));
                    }
                }
            }

        fileWriter.closeJSON(arrayResult);
    }
}
