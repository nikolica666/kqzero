package hr.nipeta.kqzero.movement;

import hr.nipeta.kqzero.gameobjects.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@Builder
public class Movement {

    private Direction direction = Direction.DOWN;
    private Speed speed;

    public static Movement simple(double constantSpeedInTilesPerSecond) {
        return Movement.builder()
                .direction(Direction.DOWN)
                .speed(Speed.constant(constantSpeedInTilesPerSecond))
                .build();
    }

    @Getter
    @Setter
    public static class Speed {
        private double initial;
        private double current;
        private double min;
        private double max;

        public static Speed constant(double speedInTilesPerSecond) {
            Speed speed = new Speed();
            speed.initial = speedInTilesPerSecond;
            speed.current = speedInTilesPerSecond;
            speed.min = speedInTilesPerSecond;
            speed.max = speedInTilesPerSecond;
            return speed;
        }

    }

}

