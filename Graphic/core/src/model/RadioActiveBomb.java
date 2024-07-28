package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class RadioActiveBomb {
    private float x, y;
    private MainGame game;
    private static int width = 10, height = 20;
    private static int speed = -50;
    private boolean isExplode = false;
    private Sprite image = new Sprite(new Texture("Game/radioActive.png"));

    public RadioActiveBomb(float x, float y, MainGame game) {
        this.x = x;
        this.y = y;
        image.setSize(width, height);
        this.game = game;
    }
    public void update(float delta) {
        y += speed * delta;
        image.setPosition(x, y);
        image.draw(game.batch);
        if (y <= GameMenu.groundY - (float) height / 2) isExplode = true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public boolean isExplode() {
        return isExplode;
    }
}
