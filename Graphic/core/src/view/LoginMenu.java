package view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import controller.LoginMenuController;
import model.User;

public class LoginMenu implements Screen {
    private Skin skin;
    private Texture background = new Texture(Gdx.files.internal("Backgrounds/back.png"));
    private Stage stage;
    private MainGame game;
    private Viewport viewport;

    public LoginMenu(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('?');
        passwordField.setMessageText("Password");

        TextButton registerButton = new TextButton("Register", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = LoginMenuController.INSTANCE().register(usernameField.getText(), passwordField.getText());
                if (dialog.getColor().equals(Color.GREEN)) {
                    usernameField.setText("");
                    passwordField.setText("");
                }
                dialog.show(stage);
            }
        });

        TextButton loginButton = new TextButton("Log In", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = LoginMenuController.INSTANCE().Login(usernameField.getText(), passwordField.getText());
                dialog.show(stage);

                if (dialog.getColor().equals(Color.GREEN)) {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            game.setScreen(new MainMenu(game));
                        }
                    }, 3);
                }
            }
        });
        TextButton guest = new TextButton("Play As Guest", skin);
        guest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                User.setLoggedInUser(new User("Guest", "aaa"));
                game.setScreen(new MainMenu(game));
            }
        });
        root.defaults().pad(10).width(400).height(50);
        root.defaults().top();
        root.row();
        root.add(usernameField).row();
        root.add(passwordField).row();
        root.add(loginButton).height(40).row();
        root.add(registerButton).height(40).row();
        root.add(guest).width(guest.getMinWidth()).height(40);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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

    }
}
