package model.target;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Tree extends Obstacle{
    private float x;
    private float y = GameMenu.groundY;
    private MainGame game;
    private static int width = 40, height = 60;
    Sprite image = new Sprite(new Texture("Game/tree.png"));
    public Tree(MainGame game, float x) {
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
        return 0;
    }

    @Override
    public void update(float delta) {
        image.draw(game.batch);
    }

    public int getHeight() {
        return height;
    }

    @Override
    public float getMargine() {
        return 3;
    }

    public int getWidth() {
        return width;
    }
    public static int width() {
        return width;
    }
}
