package controller;

import model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoardeMenuController {
    public static LeaderBoardeMenuController INSTANCE = new LeaderBoardeMenuController();

    public static LeaderBoardeMenuController getINSTANCE() {
        return INSTANCE;
    }
    public ArrayList<User> sortedByKills() {
        ArrayList<User> users = new ArrayList<>(User.getUsers());
        users.sort(Comparator.comparing(User::getKills).thenComparing(User::getFinalWave).reversed());
        return users;
    }
    public ArrayList<User> sortedByDifficult() {
        ArrayList<User> users = new ArrayList<>(User.getUsers());
        users.sort(Comparator.comparing(User::difficultPoint).thenComparing(User::getFinalWave).reversed());
        return users;
    }
    public ArrayList<User> sortedByAccuracy() {
        ArrayList<User> users = new ArrayList<>(User.getUsers());
        users.sort(Comparator.comparing(User::accuracy).thenComparing(User::getFinalWave).reversed());
        return users;
    }
}
