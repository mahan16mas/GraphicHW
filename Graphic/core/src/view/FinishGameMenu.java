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

public class FinishGameMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private MainGame game;
    private boolean win;

    public FinishGameMenu(MainGame game, boolean win) {
        this.game = game;
        this.win = win;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        Label finished = new Label("", skin);
        if (win)
            finished.setText("You won");
        else finished.setText("You lost");
        stage.addActor(root);
        TextButton back = new TextButton("Return", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        back.setColor(Color.RED);
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(User.getLoggedInUser().accuracy() * 100);
        Label Accuracy = new Label("Accuracy: " + formattedNumber + "%", skin);
        //
        root.add(Accuracy).row();
        root.add(back).row();
        root.add(finished).row();
        User.updateUsers();
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.batch.draw(new Texture(Gdx.files.internal("Backgrounds/back7.png")), 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(new Texture(Gdx.files.internal("Backgrounds/ground.png")), 0, 0, Gdx.graphics.getWidth(), GameMenu.groundY);
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
