package hr.nipeta.kqzero.gameobjects.items.behavior;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopItemBehavior implements ItemBehavior {

    @Override
    public void applyTo(Entity entity, GameManager gm) {
        log.debug("Nothing happened");
    }

}
