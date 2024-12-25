package hr.nipeta.kqzero.gameobjects.items.behaviors;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.gameobjects.entities.Player;
import hr.nipeta.kqzero.gameobjects.items.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectableItemBehavior implements ItemBehavior {

    private Item collectableItem;

    public static CollectableItemBehavior of(Item collectableItem) {
        return new CollectableItemBehavior(collectableItem);
    }

    @Override
    public void applyTo(Entity entity, GameManager gm) {
        if (entity instanceof Player player) {
            boolean addedToInventory = player.addToInventory(collectableItem);
            if (addedToInventory) {
                log.debug("Inventory is:");
                player.getInventory().getSlots().forEach(slot -> {
                    log.debug("{} with {} items", slot.getItemName(), slot.getItems().size());
                });
                gm.itemsOnMap.remove(collectableItem);
                gm.messageDrawer.addMessage(String.format("You collected new %s", collectableItem.getName()));
            }
        }
    }

}
