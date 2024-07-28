package controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class AppController {

    private static final ArrayList<FileHandle> avatars;
    static {
        avatars = new ArrayList<>();
        avatars.add(Gdx.files.internal("avatars/image1.png"));
        avatars.add(Gdx.files.internal("avatars/image2.png"));
        avatars.add(Gdx.files.internal("avatars/image3.png"));
    }
    private static final ArrayList<FileHandle> musicPaths = new ArrayList<>();
    static {
        musicPaths.add(Gdx.files.internal("Music/music1.mp3"));
        musicPaths.add(Gdx.files.internal("Music/music2.mp3"));
        musicPaths.add(Gdx.files.internal("Music/music3.mp3"));
        }
    public static Music getMusic(int number) {
        return Gdx.audio.newMusic(musicPaths.get(number));
    }
    public static ArrayList<FileHandle> getAvatars() {
        return avatars;
    }
    public void addImage(String path) {
        avatars.add(Gdx.files.internal(path));
    }
}
