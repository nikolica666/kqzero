package hr.nipeta.game2d.items;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.entities.Entity;

public class Coin extends Item {

    public Coin(GameManager gm, Double worldTileX, Double worldTileY) { super(gm, worldTileX, worldTileY);}

    @Override public boolean isCollectable(Entity entity) { return true; }
    @Override public boolean isSolid() { return false; }

}
