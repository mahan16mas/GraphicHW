package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Bullet {
    private float x, y;
    private float speedX, speedY;
    private MainGame game;
    private static int speed = -200;
    private static int width = 3, height= 7;
    private boolean isRemoved = false;
    private Sprite image = new Sprite(new Texture("Game/bullet.png"));

    public Bullet(float x, float y, int speedX, int speedY, MainGame game) {
        this.x = x;
        this.y = y;
        this.speedX = speedX * 1.5f;
        this.speedY = speedY;
        this.game = game;
        image.setSize(width, height);
    }
    public void update(float delta) {
        image.setPosition(x, y);
        if (speedX > 0) speedX -= Math.min(speedX * 0.2f, 2);
        if (speedX < 0) speedX += Math.min(Math.abs(speedX) * 0.2f, 2);
        if (speedY > 0) speedY -= Math.min(speedY, 2);
        if (speedY < 0) speedY += Math.min(Math.abs(speedY), 2);
        y += (speedY + speed) * delta;
        x += speedX * delta;
        image.draw(game.batch);
        if (y < GameMenu.groundY) isRemoved = true;

    }
    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
