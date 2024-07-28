package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import view.GameMenu;
import view.MainGame;

public class Plane {
    private int xSpeed = 0;
    private int ySpeed = 0;
    private boolean flip = true;

    public float x = (float) (Gdx.graphics.getWidth() - width) / 2, y = Gdx.graphics.getHeight() - height;
    public static final int width = 50;
    public static final int height = 40;
    private int health = 3;
    private static float g = 20;
    private float direction = 0;
    private MainGame game;
    private final int xAcceleration = 50;
    private final int yAcceleration = 25;
    private final int maxXAcceleration = 200;
    private final int maxYAcceleration = 150;
    private static ControlScheme controlScheme = ControlScheme.ARROW;
    private final Sprite image = new Sprite(new Texture(Gdx.files.internal("Game/plane.png")));

    public Plane(MainGame game) {
        this.game = game;
        image.setSize(width, height);
        image.setOriginCenter();
    }

    public void goLeft(float delta) {
        xSpeed -= xAcceleration;
        if (xSpeed < -1 * maxXAcceleration) xSpeed = -1 * maxXAcceleration;
        if (direction < 180) {
            direction += 2;
            image.rotate(2);

        }
        else if (direction > 180) {
            direction -= 2;
            image.rotate(-2);

        }
    }

    public void goRight(float delta) {
        xSpeed += xAcceleration;
        if (xSpeed > maxXAcceleration) xSpeed = maxXAcceleration;
        if (direction != 0) {
            if (direction < 180) {
                direction -=2;
                image.rotate(-2);

            }
            else {
                direction+=2;
                image.rotate(2);

            }
        }
    }

    public void goUp(float delta) {
        ySpeed += yAcceleration;
        if (ySpeed > maxYAcceleration) ySpeed = maxYAcceleration;
        if (direction == 90) return;
        if (direction < 90 || direction > 270){
            direction += 2;
            image.rotate(2);
        }
        else{
            image.rotate(-2);
            direction -= 2;
        }
    }

    public void goDown(float delta) {
        ySpeed -= yAcceleration;
        if (ySpeed < -1 * maxYAcceleration) ySpeed = -1 * maxYAcceleration;
        if (direction == 270) return;
        if (direction < 90 || direction > 270) {
            direction -= 2;
            image.rotate(-2);
        }
        else {
            image.rotate(2);
            direction += 2;
        }
    }

    public void update(float delta) {
        if (Gdx.input.isKeyPressed(controlScheme.getUp())) goUp(delta);
        if (Gdx.input.isKeyPressed(controlScheme.getRight())) goRight(delta);
        if (Gdx.input.isKeyPressed(controlScheme.getLeft())) goLeft(delta);
        if (Gdx.input.isKeyPressed(controlScheme.getDown())) goDown(delta);
        x += xSpeed * delta;
        y += ySpeed * delta;
        if (Math.abs(ySpeed) < 5)
            y -= g * delta;
        if (x > Gdx.graphics.getWidth()) x = -1 * width;
        if (x < -1 * width) x = Gdx.graphics.getWidth();
        if (y < GameMenu.groundY) {
            y = GameMenu.groundY;
            ySpeed = 0;
        }
        if (y > Gdx.graphics.getHeight() - height)
        {
            ySpeed = 0;
            y = Gdx.graphics.getHeight() - height;
        }
        if (ySpeed > 0) ySpeed -= 5;
        if (ySpeed < 0) ySpeed += 5;
        if (xSpeed < 0) xSpeed += 5;
        if (xSpeed > 0) xSpeed -= 5;
        direction %= 360;
        direction += 360;
        direction %= 360;
        image.draw(game.batch);

        image.setPosition(x, y);
        image.setRotation(direction);
        if (direction > 90 && direction < 270) {
            if (flip) {
                image.flip(false, true);
                flip = false;
            }
        }
        else {
            if (!flip){
                image.flip(false, true);
                flip = true;
            }
        }

    }

    public int getHealth() {
        return health;
    }

    public float getDirection() {
        return direction;
    }

    public Sprite getImage() {
        return image;
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }
    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return height;
    }

    public static void setControlScheme(ControlScheme controlScheme) {
        Plane.controlScheme = controlScheme;
    }
}
