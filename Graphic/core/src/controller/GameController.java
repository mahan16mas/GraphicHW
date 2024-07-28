package controller;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import model.*;
import model.target.*;
import view.FinishGameMenu;
import view.GameMenu;
import view.MainGame;
import view.WaveMenu;

import java.util.ArrayList;
import java.util.Random;

public class GameController {
    private static final int tankSpeed = 150;

    private static final int tankRadius = 20;
    private static int radioActiveRadius = 60;
    private int enough = 0;
    private Sprite migAlertSprite = new Sprite(new Texture("Game/migwarning.png"));
    private Sprite freezeBack = new Sprite(new Texture("Backgrounds/freeze.png"));
    private float freezeTime = 0;
    private Sprite radioBonus = new Sprite(new Texture("Game/radioBomb.png"));
    private Sprite clusterBonus = new Sprite(new Texture("Game/cluster.png"));
    private MainGame game;
    private GameMenu gameMenu;
    private Random random = new Random();
    private int remainingMigs, migNumber;
    private int migRadius;

    private int buildingX;
    private float freezeBar = 0;
    private int shots = 0;
    private int kills = 0;
    private int destroyed = 0;
    private int clusterBombs = 0;
    private int radioActiveBombs = 0;
    private float noMigTime = 0;
    private final ArrayList<Blast> blasts = new ArrayList<>();
    private final ArrayList<RadioActiveBomb> radioActiveBomb = new ArrayList<>();
    private final ArrayList<Bonus> clusterBonuses = new ArrayList<>();
    private final ArrayList<Bonus> radioBonuses = new ArrayList<>();
    private final ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private final ArrayList<Mig> migs = new ArrayList<>();
    private final ArrayList<Rocket> rockets = new ArrayList<>();
    private final float tankShotsRadius;

    public GameController(MainGame game, GameMenu gameMenu, int clusterBombs, int radioActiveBombs, float freezeBar) {
        this.freezeBar = freezeBar;
        this.clusterBombs = clusterBombs;
        this.radioActiveBombs = radioActiveBombs;
        this.game = game;
        this.gameMenu = gameMenu;
        migRadius = gameMenu.getDifficulty().getMigRadius() * 250;
        if (gameMenu.getCurrentWave().isMigComes()) {
            migNumber = Wave.getMigNumber();
            noMigTime = random.nextFloat(5, 10);
        } else migNumber = 0;
        remainingMigs = migNumber;
        Obstacle.setIsFrozen(false);
        tankShotsRadius = 200 * gameMenu.getDifficulty().getTankRadius();
    }

    public void setTrees() {
        int treeNumber = Wave.getTreeNumber();
        for (int i = 0; i < treeNumber; i++) {
            gameMenu.getObstacles().add(new Tree(game, random.nextInt(0, Gdx.graphics.getWidth() - Tree.width())));
        }
    }

    public void setBuilding() {
        int buildingNumbers = Wave.getBuildingNumbers();
        buildingX = random.nextInt(0, Gdx.graphics.getWidth() - 2 * Building.width());
        for (int i = 0; i < buildingNumbers; i++) {
            gameMenu.getObstacles().add(new Building(game, random.nextFloat(0, buildingX)));
        }
    }

    public void setBunker() {
        int bunkerNumber = Wave.getBunkerNumber();
        for (int i = 0; i < bunkerNumber; i++) {
            gameMenu.getObstacles().add(new Bunker(random.nextInt(buildingX + Building.width(), Gdx.graphics.getWidth() - Bunker.width()), game));
        }
    }

    public void setMig() {
        migs.add(new Mig(random.nextInt(GameMenu.groundY, Gdx.graphics.getHeight() - 50), game));
    }

    public void shoot() {
        Plane plane = gameMenu.getPlane();
        shots++;
        gameMenu.getBullets().add(new Bullet(plane.x + (float) Plane.width / 2, plane.y, plane.getXSpeed(), plane.getYSpeed(), game));
    }

    public void update(float delta) {
        //objects to be removed
        ArrayList<Bullet> removingBullets = new ArrayList<>();
        ArrayList<Obstacle> removingObstacles = new ArrayList<>();
        ArrayList<Blast> removingBlasts = new ArrayList<>();
        ArrayList<RadioActiveBomb> removingRadioActives = new ArrayList<>();
        ArrayList<Bonus> removingBonuses = new ArrayList<>();
        ArrayList<EnemyBullet> removingEnemyBullets = new ArrayList<>();
        ArrayList<Mig> removingMigs = new ArrayList<>();
        ArrayList<Rocket> removingRockets = new ArrayList<>();
        //migHandling
        noMigTime -= delta;
        if (remainingMigs > 0) {
            if (noMigTime < 0) {
                setMig();
                remainingMigs--;
                noMigTime = random.nextFloat(10, 15) * gameMenu.getDifficulty().getMigTime();
            }
            if (noMigTime < 3) {
                migAlert();
            }
        }
        //update objects
        if (Obstacle.isIsFrozen()) {
            freezeTime += delta;
            if (freezeTime > 5) {
                freezeTime = 0;
                Obstacle.setIsFrozen(false);
            }
        }
        for (Bullet bullet : gameMenu.getBullets()) {
            bullet.update(delta);
            for (Obstacle obstacle : gameMenu.getObstacles()) {
                if (collides(obstacle, bullet)) {
                    removingObstacles.add(obstacle);
                    removingBullets.add(bullet);
                    blasts.add(new Blast(obstacle, game));
                    kills += obstacle.bonus();
                    destroyed++;
                    if (obstacle instanceof Building) {
                        radioBonuses.add(new Bonus(obstacle.middleX(), obstacle.getY() + obstacle.getHeight(), radioBonus, game));
                    }
                    if (obstacle instanceof Bunker) {
                        clusterBonuses.add(new Bonus(obstacle.middleX(), obstacle.getY() + obstacle.getHeight(), clusterBonus, game));

                    }
                    if (!(obstacle instanceof Tree))
                        freezeBar += 0.1f;
                    break;
                }

            }

            if (bullet.isRemoved()) {
                removingBullets.add(bullet);
                blasts.add(new Blast(bullet, game));
            }
        }
        for (EnemyBullet enemyBullet : enemyBullets) {
            enemyBullet.update(delta);
            if (enemyBullet.isOutOfScreen()) removingEnemyBullets.add(enemyBullet);
            if (collides(enemyBullet, gameMenu.getPlane())) {
                blasts.add(new Blast(gameMenu.getPlane(), game));
                removingEnemyBullets.add(enemyBullet);
                gameOver(false);
            }
        }
        for (Mig mig : migs) {
            mig.update(delta);
            if (planeIsInRadar(mig, gameMenu.getPlane())) {
                mig.shoot();
                rockets.add(new Rocket(game, mig.getX() + mig.getWidth(), mig.getY(), gameMenu));
            }
            if (mig.isOutOfScreen()) removingMigs.add(mig);
        }
        for (Rocket rocket : rockets) {
            rocket.update(delta);
            if (rocket.isOver()) {
                blasts.add(new Blast(rocket, game));
                removingRockets.add(rocket);
            } else if (collides(gameMenu.getPlane(), rocket)) {
                blasts.add(new Blast(gameMenu.getPlane(), game));
                removingRockets.add(rocket);
                gameOver(false);
            }
        }
        for (RadioActiveBomb activeBomb : radioActiveBomb) {
            activeBomb.update(delta);
            if (activeBomb.isExplode()) {
                for (Obstacle obstacle : gameMenu.getObstacles()) {
                    if (explodesBy(obstacle, activeBomb)) {
                        removingObstacles.add(obstacle);
                        blasts.add(new Blast(obstacle, game));
                        if (!(obstacle instanceof Tree))
                            freezeBar += .1f;
                        if (obstacle instanceof Building) {
                            radioBonuses.add(new Bonus(obstacle.middleX(), obstacle.getY() + obstacle.getHeight(), radioBonus, game));
                        }
                        if (obstacle instanceof Bunker) {
                            clusterBonuses.add(new Bonus(obstacle.middleX(), obstacle.getY() + obstacle.getHeight(), clusterBonus, game));
                        }
                    }
                    blasts.add(new Blast(activeBomb, game));
                }
                removingRadioActives.add(activeBomb);
            }
        }
        for (Bonus bonus : clusterBonuses) {
            bonus.update(delta);
            if (bonus.isRemove()) removingBonuses.add(bonus);
            if (collides(gameMenu.getPlane(), bonus)) {
                clusterBombs++;
                removingBonuses.add(bonus);
            }
        }
        for (Obstacle obstacle : gameMenu.getObstacles()) {
            obstacle.update(delta);
            if (obstacle instanceof Tank) {
                if (gameMenu.getCurrentWave().isTankShots() && !Obstacle.isIsFrozen()) {
                    Plane plane = gameMenu.getPlane();
                    ((Tank) obstacle).addTimeBetweenShots(delta);
                    if (plane.x - obstacle.middleX() > 0 && Math.abs(plane.x - obstacle.middleX() - plane.y + obstacle.middleY()) < 5 &&
                            ((Tank) obstacle).getTimeBetweenShots() > 2
                            && plane.x - obstacle.middleX() < tankShotsRadius) {
                        enemyBullets.add(new EnemyBullet(obstacle.middleX() + 12, obstacle.getY() + obstacle.getHeight() - 6, 100, 100, game));
                        enemyBullets.add(new EnemyBullet(obstacle.middleX() + 20, obstacle.getY() + obstacle.getHeight() - 3, 100, 100, game));
                        ((Tank) obstacle).setTimeBetweenShots(0);
                    }
                }

            }
            if (collides(obstacle, gameMenu.getPlane())) {
                blasts.add(new Blast(gameMenu.getPlane(), game));
                gameOver(false);
            }
        }

        for (Bonus bonus : radioBonuses) {
            bonus.update(delta);
            if (bonus.isRemove()) removingBonuses.add(bonus);
            if (collides(gameMenu.getPlane(), bonus)) {
                radioActiveBombs++;
                removingBonuses.add(bonus);
            }
        }
        gameMenu.getPlane().update(delta);
        if (gameMenu.getPlane().y <= GameMenu.groundY) {
            blasts.add(new Blast(gameMenu.getPlane(), game));
        }
        for (Blast blast : blasts) {
            blast.update(delta);
            if (blast.isFinished()) removingBlasts.add(blast);
        }
        if (Obstacle.isIsFrozen()) {
            freezeBack.setSize(Gdx.graphics.getWidth(), 150);
            freezeBack.setPosition(0, Gdx.graphics.getHeight() - freezeBack.getHeight());
            freezeBack.draw(game.batch);
        }
        //remove objects
        rockets.removeAll(removingRockets);
        clusterBonuses.removeAll(removingBonuses);
        radioBonuses.removeAll(removingBonuses);
        radioActiveBomb.removeAll(removingRadioActives);
        blasts.removeAll(removingBlasts);
        migs.removeAll(removingMigs);
        gameMenu.getObstacles().removeAll(removingObstacles);
        gameMenu.getBullets().removeAll(removingBullets);
        enemyBullets.removeAll(removingEnemyBullets);
        //freeze
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            if (freezeBar >= 1) {
                Obstacle.setIsFrozen(true);
                freezeBar = 0;
            }
        }
        //cheat code
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) radioActiveBombs++;
        if (Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT)) clusterBombs++;
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) spawnRandomTank();
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) passWave();
        //wave finished
        if (enemyBullets.isEmpty() && gameMenu.getTankRemaining() == 0 &&
                gameMenu.getTruckRemaining() == 0 && isFreeOfNoTree(gameMenu.getObstacles())
                && remainingMigs == 0 && migs.isEmpty() && rockets.isEmpty()) {
            passWave();
        }
    }

    private boolean planeIsInRadar(Mig mig, Plane plane) {
        float distanceX = mig.getX() - plane.x;
        if (distanceX > 0) return false;
        float distanceY = mig.getY() - plane.y;
        return (distanceX * distanceX + distanceY * distanceY <= migRadius * migRadius) && mig.canShoot();
    }

    private void migAlert() {
        migAlertSprite.setSize(100, 100);
        migAlertSprite.setPosition((float) (Gdx.graphics.getWidth() - 100) / 2, (float) (Gdx.graphics.getHeight() - 100) / 2);
        migAlertSprite.draw(game.batch);
    }

    private boolean collides(EnemyBullet enemyBullet, Plane plane) {
        return plane.x < enemyBullet.getX() + EnemyBullet.getHeight() &&
                plane.x + Plane.getWidth() > enemyBullet.getX() &&
                plane.y + Plane.height > enemyBullet.getY() &&
                plane.y < enemyBullet.getY() + EnemyBullet.getHeight();
    }

    private void spawnRandomTank() {
        gameMenu.getObstacles().add(new Tank(game, gameMenu.getTankDefaultSpeed(), random.nextInt(0, Gdx.graphics.getWidth() - Tank.width()), gameMenu.getCurrentWave().isTankShots()));
    }

    private boolean explodesBy(Obstacle obstacle, RadioActiveBomb activeBomb) {
        float xDistance = Math.abs(obstacle.middleX() - (activeBomb.getX() + (float) RadioActiveBomb.getWidth() / 2));
        return xDistance < radioActiveRadius;
    }

    private boolean collides(Obstacle obstacle, Bullet bullet) {
        return obstacle.getX() < bullet.getX() + Bullet.getWidth() &&
                obstacle.getX() + obstacle.getWidth() > bullet.getX() &&
                obstacle.getY() + obstacle.getMargine() < bullet.getY() + Bullet.getHeight() &&
                obstacle.getY() + obstacle.getHeight() > bullet.getY() + obstacle.getMargine();
    }

    public void shootCluster() {
        if (clusterBombs <= 0) return;
        Plane plane = gameMenu.getPlane();
        shots += 4;
        clusterBombs--;
        for (int i = 0; i < 4; i++) {
            int speedX;
            switch (i) {
                case 0:
                    speedX = -200;
                    break;
                case 1:
                    speedX = -100;
                    break;
                case 2:
                    speedX = 100;
                    break;
                case 3:
                    speedX = 200;
                    break;
                default:
                    speedX = 0;
            }
            speedX += plane.getXSpeed();
            gameMenu.getBullets().add(new Bullet(plane.x + (float) Plane.width / 2, plane.y, speedX / 2, 0, game));
        }
    }

    public void shootRadioActive() {
        if (radioActiveBombs <= 0) return;
        radioActiveBombs--;
        Plane plane = gameMenu.getPlane();
        radioActiveBomb.add(new RadioActiveBomb(plane.x + (float) Plane.width / 2 - (float) RadioActiveBomb.getWidth() / 2, plane.y - RadioActiveBomb.getHeight(), game));
    }

    public int getDestroyed() {
        return destroyed;
    }

    public int getKills() {
        return kills;
    }

    public int getShots() {
        return shots;
    }

    public static int getRadioActiveRadius() {
        return radioActiveRadius;
    }

    public boolean collides(Plane plane, Bonus bonus) {
        return bonus.getX() < plane.x + Plane.width &&
                bonus.getX() + bonus.getHeight() > plane.x &&
                bonus.getY() < plane.y + Plane.height &&
                bonus.getY() + bonus.getHeight() > plane.y;
    }

    public void gameOver(boolean win) {
        enough++;
        if (enough == 1) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    User.getLoggedInUser().setShots(User.getLoggedInUser().getShots() + shots);
                    User.getLoggedInUser().setKills(User.getLoggedInUser().getKills() + kills);
                    User.getLoggedInUser().setSuccessfulShots(User.getLoggedInUser().getSuccessfulShots() + destroyed);
                    GameMenu.getMusic().stop();
                    User.updateUsers();
                    if (win)
                        User.getLoggedInUser().setFinalWave(4);
                    game.setScreen(new FinishGameMenu(game, win));
                }
            }, 3);
        }
    }

    public void passWave() {
        User.getLoggedInUser().setShots(User.getLoggedInUser().getShots() + shots);
        User.getLoggedInUser().setKills(User.getLoggedInUser().getKills() + kills);
        User.getLoggedInUser().setSuccessfulShots(User.getLoggedInUser().getSuccessfulShots() + destroyed);
        User.getLoggedInUser().setFinalWave(User.getLoggedInUser().getFinalWave() + 1);
        if (gameMenu.getCurrentWave().equals(Wave.W3))
            gameOver(true);
        else {
            gameMenu.dispose();
            game.setScreen(new WaveMenu(game, clusterBombs, radioActiveBombs, freezeBar));
        }
    }

    private boolean isFreeOfNoTree(ArrayList<Obstacle> obstacles) {
        for (Obstacle obstacle : obstacles) {
            if (!(obstacle instanceof Tree))
                return false;
        }
        return true;
    }

    private boolean collides(Obstacle obstacle, Plane plane) {
        return obstacle.getX() < plane.x + Plane.width &&
                obstacle.getX() + obstacle.getWidth() > plane.x &&
                obstacle.getY() + obstacle.getMargine() < plane.y + Plane.height &&
                obstacle.getY() + obstacle.getHeight() - obstacle.getMargine() > plane.y;
    }

    private boolean collides(Plane plane, Rocket rocket) {
        return rocket.getPosition().x + rocket.getWidth() > plane.x &&
                rocket.getPosition().x < plane.x + Plane.width &&
                rocket.getPosition().y < plane.y + Plane.height &&
                rocket.getPosition().y + rocket.getHeight() > plane.y;
    }

    public float getFreezeBar() {
        return freezeBar;
    }

    public int getRadioActiveBombs() {
        return radioActiveBombs;
    }

    public int getClusterBombs() {
        return clusterBombs;
    }
}
