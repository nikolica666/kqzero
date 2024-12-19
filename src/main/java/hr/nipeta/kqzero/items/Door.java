package hr.nipeta.kqzero.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.entities.Entity;

public class Door extends Item {

    public Door(GameManager gm, Double worldTileX, Double worldTileY) { super(gm, worldTileX, worldTileY); }

    @Override public boolean isCollectable(Entity entity) { return false; }
    @Override public boolean isSolid() { return true; }

    @Override
    public String getName() {
        return "Door";
    }

}
