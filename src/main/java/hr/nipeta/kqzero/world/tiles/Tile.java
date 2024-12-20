package hr.nipeta.kqzero.world.tiles;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

@Getter
@AllArgsConstructor
public enum Tile {

    GRASS_1("g1", false, false, false),
    GRASS_2("g2", false, false, false),
    SEA_1("s1", false, true, false),
    SEA_2("s2", false, true, false),
    TREE_1("t1", true, false, true),
    TREE_2("t2", true, false, true),
    ROAD_1("r1", false, false, false),
    ROAD_2("r2", false, false, false);

    private final String fileNotation;
    private final boolean solid;
    private final boolean water;
    private final boolean forest;

    public static Tile fromFileNotation(String fileNotation) {
        for (Tile tile : values()) {
            if (tile.fileNotation.equals(fileNotation)) {
                return tile;
            }
        }
        throw new IllegalArgumentException("Unknown tile file notation " + fileNotation);
    }

    private static final Predicate<Tile> WATER = Tile::isWater;
    private static final Predicate<Tile> NOT_WATER = WATER.negate();
    private static final Predicate<Tile> SOLID = Tile::isSolid;
    private static final Predicate<Tile> WATER_OR_SOLID = WATER.or(SOLID);

    public static Set<Tile> water() {
        return filterBy(WATER);
    }

    public static Set<Tile> nonWater() {
        return filterBy(NOT_WATER);
    }

    public static Set<Tile> solid() {
        return filterBy(SOLID);
    }

    public static Set<Tile> waterOrSolid() {
        return filterBy(WATER_OR_SOLID);
    }

    private static Set<Tile> filterBy(Predicate<Tile> condition) {
        return Arrays.stream(values()) // Always creates a new stream
                .filter(condition)
                .collect(toSet());
    }

}
