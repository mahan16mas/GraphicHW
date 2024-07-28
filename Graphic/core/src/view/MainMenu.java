package view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.AppController;
import jdk.tools.jmod.Main;
import model.User;

import java.util.Random;

public class MainMenu implements Screen {
    private Stage stage;
    private MainGame game;
    private int avatarWidthAndHeight = 100;
    private Skin skin;

    private Texture background = new Texture(Gdx.files.internal("Backgrounds/back2.png"));
    public MainMenu(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        Label username = new Label(User.getLoggedInUser().getName(), skin);
        username.setColor(Color.BLACK);
        Image avatar = new Image(new Texture(AppController.getAvatars().get(User.getLoggedInUser().getAvatar())));
        root.addActor(avatar);
        avatar.setSize(avatarWidthAndHeight, avatarWidthAndHeight);
        avatar.setPosition(0, Gdx.graphics.getHeight() - avatarWidthAndHeight);
        root.addActor(username);
        username.setSize(avatarWidthAndHeight, 30);
        username.setPosition(5, Gdx.graphics.getHeight() - avatarWidthAndHeight - 40);
        TextButton playNewGame = new TextButton("New Game", skin);
        playNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.getLoggedInUser().setFinalWave(1);
                dispose();
                User.getLoggedInUser().setKills(0);
                User.getLoggedInUser().setSuccessfulShots(0);
                User.getLoggedInUser().setShots(0);
                game.setScreen(new GameMenu(game, 0, 0, 0));
            }
        });
        TextButton profileMenu = new TextButton("Go To Profile Menu", skin);
        profileMenu.setColor(Color.YELLOW);
        profileMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new ProfileMenu(game));
            }
        });
        TextButton continueGame = new TextButton("Continue", skin);
        continueGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        TextButton leaderBoard = new TextButton("LeaderBoard", skin);
        Button setting = new Button(skin);
        setting.setColor(Color.GRAY);
        setting.add(new Image(new Texture(Gdx.files.internal("Menu/setting.png")))).fill();
        setting.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new SettingMenu(game));
            }
        });
        Button exit = new Button(skin);
        exit.setColor(Color.RED);
        exit.add(new Image(new Texture(Gdx.files.internal("Menu/exit.png")))).fill();
        exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.updateUsers();
                Gdx.app.exit();
            }
        });
        leaderBoard.setColor(Color.RED);
        leaderBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new LeaderBoardMenu(game));
            }
        });
        root.defaults().pad(10);
        root.add(playNewGame);
        root.add(continueGame).row();
        root.add(profileMenu);
        root.add(leaderBoard).row();
        root.add(setting).width(100).height(100);
        root.add(exit).size(100, 100);
        stage.addActor(root);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.batch.draw(background, 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        stage.dispose();
    }
}
