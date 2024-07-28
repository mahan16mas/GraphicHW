package model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import controller.GameController;
import model.target.Building;
import model.target.EnemyBullet;
import model.target.Obstacle;
import view.GameMenu;
import view.MainGame;

public class Blast {
    public float stateTime = 0;
    Animation animation = null;
    private MainGame game;
    public static final float frameLength = 0.2f;
    private Obstacle obstacle;
    private Rocket rocket;
    private Bullet bullet;
    private RadioActiveBomb radioActiveBomb;
    private boolean finished = false;
    private Plane plane;

    public Blast(Obstacle obstacle, MainGame game) {
        this.game = game;
        this.obstacle = obstacle;
        Texture[] textures = new Texture[3];
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture("Game/Blast/blast" + (i + 1) + ".png");
        }
        animation = new Animation<>(frameLength, textures);
    }
    public Blast(Rocket rocket, MainGame game) {
        this.rocket = rocket;
        this.game = game;
        animation = new Animation<>(.4f, TextureRegion.split( new Texture("Game/blast/explosion.png"), 32, 32)[0]);

    }

    public Blast(Bullet bullet, MainGame game) {
        this.bullet = bullet;
        this.game = game;
        Texture[] textures = new Texture[3];
        for (int i = 0; i < textures.length; i++) {
            textures[i] = new Texture("Game/Blast/fire" + (i + 1) + ".png");
        }
        animation = new Animation<>(frameLength, textures);
    }

    public Blast(RadioActiveBomb radioActiveBomb, MainGame game) {
        this.game = game;
        this.radioActiveBomb = radioActiveBomb;
        Texture[] textures = new Texture[4];
        for (int i = 1; i < 5; i++) {
            textures[i - 1] = new Texture("Game/Blast/nuketop" + i + ".png");
        }
        animation = new Animation<>(.4f, textures);
    }

    public Blast(Plane plane, MainGame game) {
        this.game = game;
        this.plane = plane;
        animation = new Animation<>(.6f, TextureRegion.split(new Texture("Game/Blast/airCrush.png"), 98, 98)[0]);
    }

    public boolean isFinished() {
        return finished;
    }

    public void update(float delta) {
        stateTime += delta;
        if (animation.isAnimationFinished(stateTime)) finished = true;
        if (obstacle != null) {
            game.batch.draw((Texture) animation.getKeyFrame(stateTime, true),
                    obstacle.getX(), obstacle.getY() - 20 * ((float) obstacle.getWidth() / Building.width()), obstacle.getWidth(), obstacle.getHeight());
        } else if (bullet != null) {
            game.batch.draw((Texture) animation.getKeyFrame(stateTime, true), bullet.getX(), bullet.getY(), 10, 10);
        } else if (radioActiveBomb != null) {
            game.batch.draw((Texture) animation.getKeyFrame(stateTime, true),
                    radioActiveBomb.getX() - 5 - GameController.getRadioActiveRadius(),
                    GameMenu.groundY, 2 * GameController.getRadioActiveRadius(),
                    2 * GameController.getRadioActiveRadius());
        } else if (plane != null){
            game.batch.draw((TextureRegion) animation.getKeyFrame(stateTime, true),
                    plane.x,
                    plane.y, Plane.width,
                    Plane.height);

        } else {
            game.batch.draw((TextureRegion) animation.getKeyFrame(stateTime, true),
                    rocket.getPosition().x, rocket.getPosition().y, 100, 50
            );

        }
    }
}
