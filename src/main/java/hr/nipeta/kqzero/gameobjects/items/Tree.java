package hr.nipeta.kqzero.gameobjects.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.tiles.Tile;

public class Tree extends Item {

    public Tree(GameManager gm, Double worldTileX, Double worldTileY) { super(gm, worldTileX, worldTileY); }

    @Override public boolean isSpawnableOn(Tile tile) { return tile.isForest(); }
    @Override public boolean isSolid() {
        return true;
    }

    @Override public String getName() {
        return "Tree";
    }

}
