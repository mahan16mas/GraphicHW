package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import model.User;

import java.text.DecimalFormat;

public class WaveMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private MainGame game;
    private int clusterNumber, radioActiveNumber;
    private float freezeBar;
    private Texture background = new Texture(Gdx.files.internal("Backgrounds/back7.png"));
    private Texture ground = new Texture(Gdx.files.internal("Backgrounds/ground.png"));

    public WaveMenu(MainGame game, int clusterNumber, int radioActiveNumber, float freezeBar) {
        this.game = game;
        this.clusterNumber = clusterNumber;
        this.radioActiveNumber = radioActiveNumber;
        this.freezeBar = freezeBar;
    }


    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        TextButton back = new TextButton("Go to Wave " + User.getLoggedInUser().getFinalWave(), skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new GameMenu(game, clusterNumber, radioActiveNumber, freezeBar));
            }
        });
        back.setColor(Color.RED);

        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(User.getLoggedInUser().accuracy() * 100);
        Label Accuracy = new Label("Accuracy: " + formattedNumber + "%", skin);
        //
        root.add(Accuracy).row();
        root.add(back);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.batch.draw(background, 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(ground, 0, 0, Gdx.graphics.getWidth(), GameMenu.groundY);
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
