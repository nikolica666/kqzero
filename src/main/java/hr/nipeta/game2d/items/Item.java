package hr.nipeta.game2d.items;

import hr.nipeta.game2d.GameManager;
import hr.nipeta.game2d.entities.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

    protected GameManager gm;

    public Double worldTileX, worldTileY;

    public abstract void draw();
    public abstract boolean isCollectable(Entity entity);
}
