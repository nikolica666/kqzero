package hr.nipeta.kqzero.gameobjects.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.gameobjects.items.behaviors.UnlockableBehavior;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;

public class Door extends Item {

    public Door(GameManager gm, WorldTile worldTile) {
        super(gm, worldTile);
        addBehavior(Entity.Action.MOVE_OVER_ITEM, UnlockableBehavior.of(this));
    }

    @Override public boolean isSpawnableOn(Tile tile) { return !tile.isWater() && !tile.isSolid(); }
    @Override public boolean isSolid() {
        return true;
    }

    @Override public String getName() {
        return "Door";
    }

}
