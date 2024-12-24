package hr.nipeta.kqzero.gameobjects.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;

public class Pine extends Item {

    public Pine(GameManager gm, WorldTile worldTile) {
        super(gm, worldTile);
    }

    @Override public boolean isSpawnableOn(Tile tile) { return tile.isForest(); }
    @Override public boolean isSolid() {
        return true;
    }

    @Override public String getName() {
        return "Pine";
    }

}
