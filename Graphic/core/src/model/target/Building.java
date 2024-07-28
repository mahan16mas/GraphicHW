package model.target;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Building extends Obstacle{
    private float x, y = GameMenu.groundY - 5;
    private static int width = 100, height = 100;
    private MainGame game;
    private Sprite image = new Sprite(new Texture("Game/building.png"));

    public Building(MainGame game, float x) {
        this.game = game;
        this.x = x;
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
        return 5;
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
        return 20;
    }

    public static int width() {
        return width;
    }
}
