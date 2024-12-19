package hr.nipeta.kqzero.gameobjects;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.collision.CollisionTolerance;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class GameObject {

    protected final GameManager gm;

    public double worldTileX;
    public double worldTileY;
    public final CollisionTolerance collisionTolerance;

    public abstract void draw();

    public abstract String getName();

}
