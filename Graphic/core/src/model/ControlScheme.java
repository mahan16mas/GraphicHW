package model;

import com.badlogic.gdx.Input;

public enum ControlScheme {
    ARROW(Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.UP, Input.Keys.DOWN),
    WASD(Input.Keys.D, Input.Keys.A, Input.Keys.W, Input.Keys.S);
    private int right, left, up, down;

    ControlScheme(int right, int left, int up, int down) {
        this.right = right;
        this.left = left;
        this.up = up;
        this.down = down;
    }

    public int getRight() {
        return right;
    }

    public int getLeft() {
        return left;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }
}
