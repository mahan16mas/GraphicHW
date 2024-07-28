package model;

import java.util.Random;

public enum Wave {
    W1(false, false),
    W2(false, true),
    W3(true, true);
    private boolean migComes, tankShots;
    private static Random random = new Random();

    Wave(boolean migComes, boolean tankShots) {
        this.migComes = migComes;
        this.tankShots = tankShots;
    }

    public boolean isMigComes() {
        return migComes;
    }

    public boolean isTankShots() {
        return tankShots;
    }
    public static int getTankNumber() {
        return random.nextInt(3, 6);
    }
    public static int getTruckNumber() {
        return random.nextInt(2, 5);
    }
    public static int getTreeNumber() {
        return random.nextInt(5, 8);
    }
    public static int getBunkerNumber() {return random.nextInt(1, 3);}
    public static int getBuildingNumbers() { return 1;
    }
    public static int getMigNumber() {
        return random.nextInt(4, 8);
    }
    public Wave getNextWave(Wave wave) {
        if (wave.equals(W1)) return W2;
        if (wave.equals(W2)) return W3;
        return null;
    }
    public static Wave getWaveByNumber(int waveNumber) {
        switch (waveNumber) {
            case 2 : return W2;
            case 3 : return W3;
            default: return W1;
        }
    }
}

