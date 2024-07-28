package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import model.target.Obstacle;
import view.MainGame;

public class Mig {
    private final Sprite image = new Sprite(new Texture("Game/mig.png"));
    private static final int width = 50, height = 15;
    private float x = -1 * width;
    private final float y;
    private static final int speed = 100;
    private final MainGame game;
    private boolean outOfScreen = false;
    private float timeBetweenShots = 3;

    public Mig(float y, MainGame game) {
        this.y = y;
        this.game = game;
        image.setSize(width, height);
    }

    public void update(float delta) {
        image.setPosition(x, y);
        image.draw(game.batch);
        if (x > Gdx.graphics.getWidth() + width) {
            outOfScreen = true;
        }
        if (!Obstacle.isIsFrozen()) {
            timeBetweenShots += delta;
            x += speed * delta;
        }
        }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void shoot() {
        timeBetweenShots = 0;
    }

    public boolean isOutOfScreen() {
        return outOfScreen;
    }

    public boolean canShoot() {
        return timeBetweenShots > 3;
    }
}
