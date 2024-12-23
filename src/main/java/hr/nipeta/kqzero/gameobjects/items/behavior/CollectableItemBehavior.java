package hr.nipeta.kqzero.gameobjects.items.behavior;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.gameobjects.entities.Player;
import hr.nipeta.kqzero.gameobjects.items.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectableItemBehavior implements ItemBehavior {

    private Item collectableItem;

    public static CollectableItemBehavior of(Item collectableItem) {
        return new CollectableItemBehavior(collectableItem);
    }

    @Override
    public void applyTo(Entity entity, GameManager gm) {
        if (entity instanceof Player player) {
            player.addToInventory(collectableItem);
            gm.itemsOnMap.remove(collectableItem);
            gm.messageDrawer.addMessage(String.format("You collected new %s", collectableItem.getName()));
        }
    }

}
