package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.GameController;
import model.ControlScheme;
import model.Difficulty;
import model.Plane;
import model.User;

import java.util.Random;

public class SettingMenu implements Screen {

    private Stage stage;
    private MainGame game;
    private Texture background = new Texture(Gdx.files.internal("Backgrounds/back6.png"));
    private Skin skin;

    public SettingMenu(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        Button back = new Button(skin);
        back.add(new Image(new Texture("Menu/back.png")));
        stage.addActor(back);
        back.setPosition(50, Gdx.graphics.getHeight() - 60);
        back.setSize(50, 50);
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });
        CheckBox[] difficulty = new CheckBox[3];
        for (int i = 0; i < 3; i++) {
            difficulty[i] = new CheckBox(Difficulty.getDifficulty(i + 1), skin);
            if (i == User.getLoggedInUser().getDifficult() - 1) difficulty[i].setChecked(true);
            root.add(difficulty[i]).expandX();
            int finalI = i;
            difficulty[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    if (difficulty[finalI].isChecked()) {
                        for (int i1 = 0; i1 < difficulty.length; i1++) {
                            if (i1 != finalI) {
                                difficulty[i1].setChecked(false);
                            }
                        }
                        User.getLoggedInUser().setDifficult(finalI + 1);
                    }
                }
            });
        }
        CheckBox mute = new CheckBox("Mute", skin);
        mute.setChecked(GameMenu.isMusicMuted());
        mute.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (mute.isChecked())
                {
                    GameMenu.setMusicMuted(true);
                }
                else {
                        GameMenu.setMusicMuted(false);
                }
            }
        });
        Button arrow = new Button(skin);
        Button wasd = new Button(skin);
        arrow.add(new Image(new Texture("Menu/arrow.png")));
        wasd.add(new Image(new Texture("Menu/wasd.png")));
        arrow.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               Plane.setControlScheme(ControlScheme.ARROW);
           }
        });
        wasd.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Plane.setControlScheme(ControlScheme.WASD);
            }
        });
        root.row();
        root.add(arrow).size(80);
        root.add(wasd).size(80);
        root.add(mute);
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
