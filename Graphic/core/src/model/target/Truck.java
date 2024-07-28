package model.target;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Truck extends Obstacle {
    private final int width = 80, height = 50;
    private MainGame game;
    private int speed;
    private float x = -1 * width, y;
    private Sprite sprite = new Sprite(new Texture("Game/truck.png"));

    public Truck(MainGame game, int speed) {
        this.game = game;
        y = GameMenu.groundY;
        sprite.setSize(width, height);
        this.speed = speed;
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
    public void update(float delta) {
        if (!Obstacle.isIsFrozen())
            x += speed * delta;
        sprite.setPosition(x, y);
        sprite.draw(game.batch);
        if (x > Gdx.graphics.getWidth()) {
            x = -1 * width;
        }
        sprite.setPosition(x, y);
        sprite.draw(game.batch);
    }

    @Override
    public int bonus() {
        return 7;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getMargine() {
        return 5;
    }

    @Override
    public int getWidth() {
        return width;
    }
}

