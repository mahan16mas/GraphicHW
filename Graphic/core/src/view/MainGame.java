package view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import view.LoginMenu;

import java.util.Random;

public class MainGame extends Game {
    public SpriteBatch batch;
    private static final Random random = new Random();
    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new LoginMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }
    public static Random getRandom() {
        return random;
    }

    public void dispose() {
        batch.dispose();
    }
}
