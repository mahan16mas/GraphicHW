package model.target;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Bunker extends Obstacle{
    private float x, y = GameMenu.groundY;
    private static int height = 40, width = 50;
    private MainGame game;
    private Sprite image = new Sprite(new Texture("Game/bunker.png"));

    public Bunker(float x, MainGame game) {
        this.x = x;
        this.game = game;
        image.setSize(width, height);
        image.setPosition(x, y);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public int bonus() {
        return 3;
    }

    @Override
    public void update(float delta) {
        image.draw(game.batch);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getMargine() {
        return 2;
    }

    public static int width() {
        return width;
    }
}
