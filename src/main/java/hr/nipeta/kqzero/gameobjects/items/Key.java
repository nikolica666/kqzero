package hr.nipeta.kqzero.gameobjects.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.gameobjects.items.behaviors.CollectableItemBehavior;
import hr.nipeta.kqzero.gameobjects.items.stack.NonStackableStrategy;
import hr.nipeta.kqzero.gameobjects.items.stack.StackableStackStrategy;
import hr.nipeta.kqzero.world.WorldTile;
import hr.nipeta.kqzero.world.tiles.Tile;

public class Key extends Item {

    public Key(GameManager gm, WorldTile worldTile) {
        super(gm, worldTile, StackableStackStrategy.ofMaxStack(16));
        addBehavior(Entity.Action.MOVE_OVER_ITEM, CollectableItemBehavior.of(this));
    }

    @Override public boolean isSpawnableOn(Tile tile) { return !tile.isWater(); }
    @Override public boolean isSolid() {
        return false;
    }

    @Override public String getName() {
        return "Key";
    }

}
