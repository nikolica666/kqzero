package hr.nipeta.kqzero.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.entities.Entity;
import hr.nipeta.kqzero.entities.Player;

public class Key extends Item {

    public Key(GameManager gm, Double worldTileX, Double worldTileY) {
        super(gm, worldTileX, worldTileY);
    }

    @Override public boolean isCollectable(Entity entity) { return entity instanceof Player; }
    @Override public boolean isSolid() { return false; }

    @Override
    public String getName() {
        return "Key";
    }

}
