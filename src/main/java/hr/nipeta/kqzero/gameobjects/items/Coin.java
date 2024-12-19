package hr.nipeta.kqzero.gameobjects.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.world.tiles.Tile;

public class Coin extends Item {

    public Coin(GameManager gm, Double worldTileX, Double worldTileY) { super(gm, worldTileX, worldTileY);}

    @Override public boolean isSpawnableOn(Tile tile) { return !tile.isWater() && !tile.isSolid(); }
    @Override public boolean isCollectable(Entity entity) { return true; }
    @Override public boolean isSolid() { return false; }

    @Override
    public String getName() {
        return "Coin";
    }

}
