package view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import model.User;

public class AvatarMenu implements Screen {
    private Stage stage;
    private MainGame game;
    private Texture background = new Texture(Gdx.files.internal("Backgrounds/back4.png"));
    private Skin skin;

    public AvatarMenu(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Viewport viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/Default/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);
        Button back = new Button(skin);
        back.setColor(Color.RED);
        Button[] buttons = new Button[3];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(skin);
            buttons[i].add(new Image(new Texture("avatars/image" + (i + 1) + ".png")));
            root.addActor(buttons[i]);
            buttons[i].setSize(200, 200);
            buttons[i].setPosition(10 + i * 500, 150);
            int finalI = i;
            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    User.getLoggedInUser().setAvatar(finalI);
                    User.updateUsers();
                }
            });
        }
        back.add(new Image(new Texture("Menu/back.png")));
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new ProfileMenu(game));
            }
        });
        root.addActor(back);
        back.setSize(50, 50);
        back.setPosition(50, 50);
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
        stage.dispose();
    }
}
