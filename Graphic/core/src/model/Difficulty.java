package model;

public enum Difficulty {
    EASY(1,1,1,1),
    MEDIUM(2, 2, 2,0.75f),
    HARD(3,3,3,0.5f);
    private int tankSpeed;
    private int migRadius;
    private int tankRadius;
    private float migTime;
    Difficulty(int tankSpeed, int migRadius, int tankRadius, float migTime) {
        this.tankSpeed = tankSpeed;
        this.migRadius = migRadius;
        this.tankRadius = tankRadius;
        this.migTime = migTime;
    }
    public int getTankSpeed() {
        return tankSpeed;
    }
    public int getMigRadius() {
        return migRadius;
    }
    public int getTankRadius() {
        return tankRadius;
    }
    public float getMigTime() {
        return migTime;
    }
    public static String getDifficulty(int number) {
        if (number == 1) return "Easy";
        if (number == 2) return "Medium";
        return "Hard";
    }
    public static Difficulty getDifficulty(String difficulty) {
        if (difficulty.equals("Easy")) return EASY;
        if (difficulty.equals("Medium")) return MEDIUM;
        return HARD;
    }
}
