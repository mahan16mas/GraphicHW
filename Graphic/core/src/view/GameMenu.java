package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.AppController;
import controller.GameController;
import model.*;
import model.target.Obstacle;
import model.target.Tank;
import model.target.Truck;

import java.util.ArrayList;
import java.util.Random;

public class GameMenu implements Screen {
    private MainGame game;
    private ProgressBar freezeProgress;
    private Plane plane;
    private GameController controller;
    private static boolean musicMuted = false;
    private Label kills;
    private Label clusterRemaining;
    private Label currentWaveLabel;
    private Label radioActiveRemaining;
    private static Music music;
    private Skin skin;
    private Window pauseWindow;
    private boolean isGamePaused = false;
    public static int groundY = 70;
    private Wave currentWave = Wave.getWaveByNumber(User.getLoggedInUser().getFinalWave());
    private int tankRemaining, truckRemaining;
    private final Random random = new Random();
    private float tankSpawnTime, truckSpawnTime;
    private Difficulty difficulty = Difficulty.getDifficulty(Difficulty.getDifficulty(User.getLoggedInUser().getDifficult()));
    private int tankDefaultSpeed = 50 * difficulty.getTankSpeed();
    private int truckSpeed = 40 * difficulty.getTankSpeed();
    private int tankNumber, truckNumber, treeNumber, wallNumber, migNumber;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Texture ground = new Texture(Gdx.files.internal("Backgrounds/back7.png"));
    private Texture background = new Texture(Gdx.files.internal("Backgrounds/ground.png"));
    private float noShootTime = 1;
    private Stage stage = new Stage(new ScreenViewport());

    public GameMenu(MainGame game, int clusterNumbers, int radioActiveBombs, float freezeBar) {
        music = AppController.getMusic(0);
        music.setLooping(true);
        this.game = game;
        plane = new Plane(game);
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        freezeProgress = new ProgressBar(0 , 1, .1f, false, skin);
        Gdx.input.setInputProcessor(stage);
        com.badlogic.gdx.scenes.scene2d.ui.Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        Button pauseButton = new Button(skin);
        pauseButton.setColor(Color.GREEN);
        pauseButton.add(new Image(new Texture("Game/pauseButton.png")));
        pauseButton.setSize(50, 50);
        pauseButton.setPosition(20, Gdx.graphics.getHeight() - 70);
        pauseButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                pause();
            }
        });
        kills = new Label("", skin);
        radioActiveRemaining = new Label("", skin);
        clusterRemaining = new Label("", skin);
        currentWaveLabel = new Label("Wave " + Wave.getWaveByNumber(User.getLoggedInUser().getFinalWave()).toString(), skin);
        currentWaveLabel.setSize(400, 50);
        currentWaveLabel.setPosition((float) (Gdx.graphics.getWidth()) / 2, Gdx.graphics.getHeight() - 100);
        radioActiveRemaining.setSize(50, 50);
        clusterRemaining.setSize(50,50);
        kills.setSize(50,50);
        clusterRemaining.setPosition(50, Gdx.graphics.getHeight() - 150);
        radioActiveRemaining.setPosition(50, Gdx.graphics.getHeight() - 200);
        kills.setPosition(50, Gdx.graphics.getHeight() - 250);
        tankNumber = Wave.getTankNumber();
        tankRemaining = tankNumber;
        truckNumber = Wave.getTruckNumber();
        truckRemaining = truckNumber;
        tankSpawnTime = random.nextFloat(3, 7);
        truckSpawnTime = random.nextFloat(4, 8);
        freezeProgress.setPosition(Gdx.graphics.getWidth() - freezeProgress.getWidth() - 50, Gdx.graphics.getHeight() - 50);
        migNumber = 0;
        controller = new GameController(game, this, clusterNumbers, radioActiveBombs, freezeBar);
        controller.setBuilding();
        controller.setTrees();
        controller.setBunker();
        root.addActor(pauseButton);
        root.addActor(currentWaveLabel);
        root.addActor(clusterRemaining);
        root.addActor(radioActiveRemaining);
        root.addActor(kills);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        kills.setText("Kills: " + controller.getKills());
        radioActiveRemaining.setText("Radio Active Bombs Remaining: " +  controller.getRadioActiveBombs());
        clusterRemaining.setText("Cluster Bombs Remaining: " + controller.getClusterBombs());
        if (!musicMuted)
            music.play();
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.batch.draw(ground, 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), groundY);
        freezeProgress.setValue(controller.getFreezeBar());
        freezeProgress.draw(game.batch, 1);
        stage.act();
        stage.draw();
        if (!isGamePaused) {
            noShootTime += delta;
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && noShootTime > 0.5f) {
                controller.shoot();
                noShootTime = 0;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) controller.shootCluster();
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) controller.shootRadioActive();
            tankSpawnController(delta);
            truckSpawnController(delta);
            controller.update(delta);
        }
        game.batch.end();
    }


    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {
        isGamePaused = true;
        pauseWindow = new Window("Game Paused", skin);
        pauseWindow.setResizable(false);
        pauseWindow.setSize(400, 500);
        pauseWindow.setPosition((Gdx.graphics.getWidth() - pauseWindow.getWidth()) / 2,
                (Gdx.graphics.getHeight() - pauseWindow.getHeight()) / 2);
        TextButton exitGame = new TextButton("Exit Without Saving", skin);
        TextButton guide = new TextButton("Guide", skin);
        TextButton stopMusic = new TextButton("Stop/Play Music", skin);
        TextButton changeMusic = new TextButton("Change Music", skin);
        TextButton resumeGame = new TextButton("Resume", skin);
        pauseWindow.add(resumeGame).row();
        pauseWindow.add(guide).row();
        pauseWindow.add(changeMusic).row();
        pauseWindow.add(stopMusic).row();
        pauseWindow.add(exitGame).row();
        resumeGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isGamePaused = false;
                pauseWindow.remove();
            }
        });
        exitGame.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                User.getLoggedInUser().setKills(controller.getKills());
                User.getLoggedInUser().setShots(controller.getShots());
                User.getLoggedInUser().setSuccessfulShots(controller.getDestroyed());
                dispose();
                music.stop();
                game.setScreen(new MainMenu(game));
            }
        });
        stopMusic.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                if (musicMuted) {
                    musicMuted = false;
                    return;
                }
                music.pause();
                musicMuted = true;
            }
        });
        changeMusic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeMusic();
            }
        });
        stage.addActor(pauseWindow);
    }

    private void changeMusic() {
        Window changeMenu = new Window("Change Menu", skin);
        TextButton music1 = new TextButton("Music 1", skin);
        TextButton music2 = new TextButton("Music 2", skin);
        TextButton music3 = new TextButton("Music 3", skin);
        TextButton back = new TextButton("Back", skin);
        music1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                music = AppController.getMusic(0);
            }
        });
        music2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                music = AppController.getMusic(1);
            }
        });
        music3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                music.stop();
                music = AppController.getMusic(2);
            }
        });
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                changeMenu.remove();
            }
        });
        changeMenu.setSize(400, 500);
        changeMenu.setPosition((Gdx.graphics.getWidth() - changeMenu.getWidth()) / 2, (Gdx.graphics.getHeight() - changeMenu.getHeight()) / 2);
        stage.addActor(changeMenu);
        changeMenu.add(music1).row();
        changeMenu.add(music2).row();
        changeMenu.add(music3).row();
        changeMenu.add(back).row();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        music.dispose();
        stage.dispose();
    }

    private void spawnTank() {
        obstacles.add(new Tank(game, tankDefaultSpeed, -1 * Tank.width(), currentWave.isTankShots()));
    }

    private void tankSpawnController(float delta) {

        tankSpawnTime -= delta;
        if (tankSpawnTime < 0) {
            if (tankRemaining > 0) {
                spawnTank();
                tankRemaining--;
            }
            tankSpawnTime = random.nextFloat(3, 7);
        }
    }

    private void truckSpawnController(float delta) {
        truckSpawnTime -= delta;
        if (truckSpawnTime < 0) {
            if (truckRemaining > 0) {
                truckSpawn();
                truckRemaining--;
            }
            truckSpawnTime = random.nextFloat(4, 8);
        }
    }

    private void truckSpawn() {
        obstacles.add(new Truck(game, truckSpeed));
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Plane getPlane() {
        return plane;
    }

    public int getTankDefaultSpeed() {
        return tankDefaultSpeed;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public int getTankRemaining() {
        return tankRemaining;
    }

    public int getTruckRemaining() {
        return truckRemaining;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public static Music getMusic() {
        return music;
    }

    public static boolean isMusicMuted() {
        return musicMuted;
    }

    public static void setMusicMuted(boolean musicMuted) {
        GameMenu.musicMuted = musicMuted;
    }
}
