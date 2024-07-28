package model.target;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.MainGame;

public class EnemyBullet {
    private Sprite sprite = new Sprite(new Texture("Game/enemyBullet.png"));
    private static final int width = 5;
    private static final int height = 5;
    private int xSpeed = 300;
    private int ySpeed = 300;
    private float x, y;
    private MainGame game;
    private boolean outOfScreen = false;
    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return height;
    }

    public EnemyBullet(float x, float y, int xSpeed, int ySpeed, MainGame game) {
        this.x = x;
        this.y = y;
        this.ySpeed = ySpeed;
        this.xSpeed = xSpeed;
        this.game = game;
        sprite.setSize(width, height);
    }
    public void update(float delta) {
        x += delta * xSpeed;
        y += delta * ySpeed;
        sprite.setPosition(x, y);
        sprite.draw(game.batch);
        if (y > Gdx.graphics.getHeight() || x > Gdx.graphics.getWidth())
            outOfScreen = true;
    }

    public boolean isOutOfScreen() {
        return outOfScreen;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
