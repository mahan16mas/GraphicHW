package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.MainGame;

public class Bonus {
    private float x, y;
    private Sprite image;
    private MainGame game;
    private int speed = 50;
    private boolean remove = false;
    private int width= 20, height = 20;

    public Bonus(float x, float y, Sprite image, MainGame game) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.game = game;
        image.setSize(width, height);
    }
    public void update(float delta) {
        y += delta * speed;
        image.setPosition(x, y);
        image.draw(game.batch);
        if (y > Gdx.graphics.getHeight()) remove = true;
    }

    public boolean isRemove() {
        return remove;
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
}
