package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import controller.AppController;
import view.MainGame;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private int kills = 0;
    private int shots = 0;
    private int successfulShots = 0;
    private int difficult = 1;
    private int finalWave = 1;
    private String password;
    private static ArrayList<User> users = new ArrayList<>();

    private static User loggedInUser;
    private int avatar;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        avatar = Math.abs(MainGame.getRandom().nextInt(3)) % 3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }



    public static void addUser(User user) {
        users.add(user);
        User.updateUsers();
    }

    public static void removeUser(User user) {
        users.remove(user);
        User.updateUsers();
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getName().equals(username)) return user;
        }
        return null;
    }
    public static void setUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get("users.dat")))) {
            ArrayList<User> users = (ArrayList<User>) ois.readObject();
            User.users.addAll(users);
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getAvatar() {
        return avatar;
    }
    public static void updateUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getKills() {
        return kills;
    }

    public int getShots() {
        return shots;
    }

    public int getSuccessfulShots() {
        return successfulShots;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }
    public void kill() {
        kills++;
    }
    public void shot() {
        shots++;
    }
    public void SuccessfulShot() {
        successfulShots++;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public void setSuccessfulShots(int successfulShots) {
        this.successfulShots = successfulShots;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
    public double accuracy() {
        if (shots == 0) return 0;
        return (double) successfulShots / shots;
    }
    public int difficultPoint() {
        return difficult * kills;
    }

    public int getFinalWave() {
        return finalWave;
    }

    public void setFinalWave(int finalWave) {
        this.finalWave = finalWave;
    }
    public double point(String sortCriteria) {
        if (sortCriteria.equals("Kills")) return kills;
        if (sortCriteria.equals("Difficulty")) return difficultPoint();
        if (sortCriteria.equals("Accuracy")) {
            return (double) ((int) (accuracy() * 100)) / 100;
        }
        return 0;
    }

}
