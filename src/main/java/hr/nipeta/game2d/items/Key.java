package hr.nipeta.game2d.items;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.entities.Entity;
import hr.nipeta.game2d.entities.Player;

public class Key extends Item {

    public Key(GameManager gm, Double worldTileX, Double worldTileY) {
        super(gm, worldTileX, worldTileY);
    }

    @Override
    public boolean isCollectable(Entity entity) { return entity instanceof Player; }

}
