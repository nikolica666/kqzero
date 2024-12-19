package hr.nipeta.game2d;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class GameLoop extends AnimationTimer {

    private long lastUpdate = 0;
    private final Consumer<Double> handler;

    @Getter
    private final FpsCounter fpsCounter = FpsCounter.perSecond(2);

    /**
     * Useful flag so we know if this timer is currently active
     */
    @Getter
    private boolean playing;

    public static GameLoop withHandler(Consumer<Double> handler) {
        return new GameLoop(handler);
    }
    
    private GameLoop(Consumer<Double> handler) {
        this.handler = handler;
    }
    
    @Override
    public void handle(long now) {

        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }

        double secondsSinceLastUpdate = (now - lastUpdate) / 1e9;
        lastUpdate = now;

        handler.accept(secondsSinceLastUpdate);

        fpsCounter.addFrame(secondsSinceLastUpdate);

    }

    public void toggleLoopPlay() {
        if (playing) {
            stop();
        } else {
            start();
        }
    }

    /**
     * <p>Calls {@link AnimationTimer#start()} and sets {@link #playing} to <b>true</b> (below is inherited JavaDoc)</p>
     *
     * {@inheritDoc}
     */
    @Override
    public void start() {
        super.start();
        lastUpdate = 0;
        playing = true;
    }

    /**
     * <p>Calls {@link AnimationTimer#stop()} and sets {@link #playing} to <b>false</b> (below is inherited JavaDoc)</p>
     *
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        super.stop();
        playing = false;
    }

    public static class FpsCounter {

        // How often are FPS calculated (don't have to be "per second", it can be more frequent)
        private final int frameCountPeriodPerSecond;
        private final double frameCountIntervalInSeconds;

        // Fraction of second before last FPS is set
        private double fpsDeltaTimeInSeconds;

        // How many frames were counted since last FPS is set
        private int frameCounter;

        // Could be useful flag in future, so we know if we need to redraw FPS on screen
        @Getter
        private boolean frameCountIntervalReached;

        // How many frames were counted in last FPS count
        @Getter
        private int currentFps;

        public static FpsCounter perSecond(int frameCountPeriodPerSecond) {
            return new FpsCounter(frameCountPeriodPerSecond);
        }

        private FpsCounter(int frameCountPeriodPerSecond) {
            if (frameCountPeriodPerSecond < 1 || frameCountPeriodPerSecond > 20) {
                throw new RuntimeException("FPS count per second should be between 1 and 20");
            }
            this.frameCountPeriodPerSecond = frameCountPeriodPerSecond;
            this.frameCountIntervalInSeconds = 1d / frameCountPeriodPerSecond;
        }

        public void addFrame(double secondsSinceLastFrame) {

            this.frameCounter++;

            this.fpsDeltaTimeInSeconds += secondsSinceLastFrame;
            if (fpsDeltaTimeInSeconds > frameCountIntervalInSeconds) {
                fpsDeltaTimeInSeconds = 0;
                currentFps = frameCounter * frameCountPeriodPerSecond;
                frameCounter = 0;
                frameCountIntervalReached = true;
            } else {
                frameCountIntervalReached = false;
            }

        }

    }

}
