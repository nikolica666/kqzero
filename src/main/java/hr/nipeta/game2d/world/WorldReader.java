package hr.nipeta.game2d.world;

import hr.nipeta.game2d.world.tiles.Tile;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class WorldReader {

    public WorldReaderResult readLocalResource(String mapResource) {

        int row = -1;

        try (InputStream resourceInputStream = Objects.requireNonNull(getClass().getResourceAsStream(mapResource));
             InputStreamReader inputStreamReader = new InputStreamReader(resourceInputStream);
             BufferedReader br = new BufferedReader(inputStreamReader)) {

            boolean headerLineRead = false;

            WorldReaderResult result = new WorldReaderResult();

            String line;
            while ((line = br.readLine()) != null) {

                if (line.startsWith("#")) {

                    if (headerLineRead) {
                        throw new RuntimeException("header already read");
                    }

                    String[] mapSize = line.trim().replace("#", "").split(",");

                    result.numberOfRows = Integer.parseInt(mapSize[0]);
                    result.numberOfCols = Integer.parseInt(mapSize[1]);

                    result.tiles = new Tile[result.numberOfRows][result.numberOfCols];

                    headerLineRead = true;

                } else {

                    if (!headerLineRead) {
                        throw new RuntimeException("no header in file");
                    }

                    row++;

                    String[] rowElements = line.split(" ");
                    for (int col = 0; col < rowElements.length; col++) {
                        result.tiles[row][col] = Tile.fromFileNotation(rowElements[col]);
                    }

                }
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Data
    public static class WorldReaderResult {
        private int numberOfRows;
        private int numberOfCols;
        private Tile[][] tiles;

    }

}
