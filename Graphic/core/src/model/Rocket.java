package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import view.GameMenu;
import view.MainGame;

public class Rocket {
    private final Sprite sprite = new Sprite(new Texture("Game/rocket.png"));
    private final int totalSpeed = 200;
    private static final int width = 30, height = 10;
    private Vector2 position, speed = new Vector2();
    private GameMenu gameMenu;
    private MainGame game;
    private float timePassed = 0;

    public Rocket(MainGame game, float x, float y, GameMenu gameMenu) {
        position = new Vector2(x, y);
        this.game = game;
        this.gameMenu = gameMenu;
        sprite.setSize(width, height);
    }
    public void update(float delta) {
        timePassed += delta;
        sprite.setPosition(position.x, position.y);
        sprite.draw(game.batch);
        Vector2 direction = new Vector2(gameMenu.getPlane().x - position.x, gameMenu.getPlane().y - position.y);
        speed.set(direction).scl((float) (totalSpeed / Math.sqrt(direction.x * direction.x + direction.y * direction.y)));
        position.mulAdd(speed , delta);
        sprite.setRotation(speed.angleDeg());
        direction.nor();
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isOver() {
        return timePassed >= 6;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
