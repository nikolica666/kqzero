package hr.nipeta.kqzero.gameobjects.items.behavior;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;

public interface ItemBehavior {
    void applyTo(Entity entity, GameManager gm);
}
