package hr.nipeta.kqzero;

import lombok.Getter;

public class SpriteCounter {

    private final double changePeriodInSeconds;
    private double lastChangeInSeconds;
    @Getter private int count;

    public SpriteCounter(double changePeriodInSeconds) {
        this.changePeriodInSeconds = changePeriodInSeconds;
    }

    public void addToLastChangeInSeconds(double delta) {
        if (delta < 0) {
            throw new RuntimeException("delta should be greater or equal to 0");
        }
        this.lastChangeInSeconds += delta;
    }

    public void incrementCounterIfSpriteChangeNeeded() {
        if (this.lastChangeInSeconds > this.changePeriodInSeconds) {
            this.count++; // Just count up. SpriteManager will do % operation as he knows number of rows
            this.lastChangeInSeconds = 0;
        }
    }
}
