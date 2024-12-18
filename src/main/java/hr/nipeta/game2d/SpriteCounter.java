package hr.nipeta.game2d;

public class SpriteCounter {

    public double lastChangeInSeconds;
    public double changePeriodInSeconds = 0.15d;
    public int counter;

    public SpriteCounter(double changePeriodInSeconds) {
        this.changePeriodInSeconds = changePeriodInSeconds;
    }

    public void incrementCounterIfSpriteChangeNeeded() {
        if (this.lastChangeInSeconds > this.changePeriodInSeconds) {
            this.counter++; // Just count up. SpriteManager will do % operation as he knows number of rows
            this.lastChangeInSeconds = 0;
        }
    }
}
