package model.target;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Tank extends Obstacle {
    private static final int width = 70, height = 50;
    private MainGame game;
    private int speed;
    private boolean doesShot;
    private float x, y;
    private Sprite sprite;
    private float timeBetweenShots = 1;
    public Tank(MainGame game, int speed, int x, boolean doesShot) {
        this.game = game;
        this.speed = speed;
        y = GameMenu.groundY;
        this.doesShot = doesShot;
        if (!doesShot) sprite  = new Sprite(new Texture("Game/tank.png"));
        else sprite = new Sprite(new Texture("Game/shootingTank.png"));
        sprite.setSize(width, height);
        this.x = x;
    }

    @Override
    public float getX() {
        return x;
    }
    public float getTimeBetweenShots() {
        return timeBetweenShots;
    }
    public void addTimeBetweenShots(float delta) {
        timeBetweenShots += delta;
    }

    public void setTimeBetweenShots(float timeBetweenShots) {
        this.timeBetweenShots = timeBetweenShots;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void update(float delta) {
        if(!Obstacle.isIsFrozen())
            x += speed * delta;
        sprite.setPosition(x, y);
        sprite.draw(game.batch);
        if (x > Gdx.graphics.getWidth()) {
            x = -1 * width;
        }
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
        return 12;
    }
    public static int width() {
        return width;
    }

    @Override
    public int bonus() {
        return 10;
    }
}
