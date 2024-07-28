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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import controller.LeaderBoardeMenuController;
import model.User;

import java.util.ArrayList;

public class LeaderBoardMenu implements Screen {
    private MainGame game;
    private Stage stage;
    private Skin skin;
    private Texture background = new Texture(Gdx.files.internal("Backgrounds/back5.png"));

    public LeaderBoardMenu(MainGame game) {
        this.game = game;

    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("skin/MainMenu/freezing-ui.json"));
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table root = new Table();
        stage.addActor(root);
        root.setFillParent(true);
        SelectBox<String> sortingBy = new SelectBox<>(skin);
        sortingBy.setItems("Kills", "Accuracy", "Difficulty");
        Button button = new Button(skin);
        button.add(new Image(new Texture("Menu/back.png")));
        button.setColor(Color.RED);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        stage.addActor(button);
        button.setPosition(10, Gdx.graphics.getHeight() - 60);
        button.setSize(50, 50);
        stage.addActor(sortingBy);
        sortingBy.setSize(150, 50);
        sortingBy.setPosition(0, 0);
        sortingBy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                root.clear();
                Label ranking = new Label("Rank", skin);
                Label username = new Label("Username", skin);
                Label points = new Label("Points", skin);
                Label finalWave = new Label("Final Wave", skin);
                root.add(ranking).expandX();
                root.add(username).expandX();
                root.add(points).expandX();
                root.add(finalWave).expandX().row();
                ArrayList<User> users = LeaderBoardeMenuController.getINSTANCE().sortedByKills();
                if (sortingBy.getSelected().equals("Accuracy"))
                    users = LeaderBoardeMenuController.getINSTANCE().sortedByAccuracy();
                if (sortingBy.getSelected().equals("Difficulty"))
                    users = LeaderBoardeMenuController.getINSTANCE().sortedByDifficult();
                int size = Math.min(users.size(), 10);
                for (int i = 0; i < size; i++) {
                    Label rankingU = new Label(String.valueOf(i + 1), skin);
                    Label usernameU = new Label(users.get(i).getName(), skin);
                    Label pointsU = new Label(String.valueOf(users.get(i).point(sortingBy.getSelected())), skin);
                    Label finalWaveU = new Label(String.valueOf(users.get(i).getFinalWave() - 1), skin);
                    if (i == 0) {
                        rankingU.setColor(Color.GOLD);
                        usernameU.setColor(Color.GOLD);
                        pointsU.setColor(Color.GOLD);
                        finalWaveU.setColor(Color.GOLD);
                    }
                    else if (i == 1) {

                        rankingU.setColor(Color.GRAY);
                        usernameU.setColor(Color.GRAY);
                        pointsU.setColor(Color.GRAY);
                        finalWaveU.setColor(Color.GRAY);
                    }
                    else if (i == 2) {

                        rankingU.setColor(Color.YELLOW);
                        usernameU.setColor(Color.YELLOW);
                        pointsU.setColor(Color.YELLOW);
                        finalWaveU.setColor(Color.YELLOW);
                    }
                    root.add(rankingU).expandX();
                    root.add(usernameU).expandX();
                    root.add(pointsU).expandX();
                    root.add(finalWaveU).expandX().row();
                }
            }
        });
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
