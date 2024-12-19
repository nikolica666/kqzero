package hr.nipeta.kqzero.items;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.entities.Entity;

public class Coin extends Item {

    public Coin(GameManager gm, Double worldTileX, Double worldTileY) { super(gm, worldTileX, worldTileY);}

    @Override public boolean isCollectable(Entity entity) { return true; }
    @Override public boolean isSolid() { return false; }

    @Override
    public String getName() {
        return "Coin";
    }

}
