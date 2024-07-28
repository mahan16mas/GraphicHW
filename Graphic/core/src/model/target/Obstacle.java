package model.target;

public abstract class Obstacle {
    public abstract float getX();
    private static boolean isFrozen = false;
    public abstract float getY();
    private boolean hasToBeRemoved = false;

    public void setHasToBeRemoved(boolean hasToBeRemoved) {
        this.hasToBeRemoved = hasToBeRemoved;
    }

    public boolean isHasToBeRemoved() {
        return hasToBeRemoved;
    }
    public abstract int bonus();

    public abstract void update(float delta);
    public abstract int getWidth();
    public abstract int getHeight();

    public abstract float getMargine();
    public float middleX() {
        return getX() + (float) getWidth() / 2;
    }
    public float middleY() {
        return getY() + (float) getHeight() / 2;
    }

    public static boolean isIsFrozen() {
        return isFrozen;
    }

    public static void setIsFrozen(boolean isFrozen) {
        Obstacle.isFrozen = isFrozen;
    }
}
