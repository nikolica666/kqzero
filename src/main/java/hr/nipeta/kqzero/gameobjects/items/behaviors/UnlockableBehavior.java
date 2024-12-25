package hr.nipeta.kqzero.gameobjects.items.behaviors;

import hr.nipeta.kqzero.GameManager;
import hr.nipeta.kqzero.gameobjects.entities.Entity;
import hr.nipeta.kqzero.gameobjects.entities.Player;
import hr.nipeta.kqzero.gameobjects.items.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UnlockableBehavior implements ItemBehavior {

    private Item unlockableItem;
    private boolean unlocked;

    public static UnlockableBehavior of(Item unlockableItem) {
        return new UnlockableBehavior(unlockableItem, false);
    }

    @Override
    public void applyTo(Entity entity, GameManager gm) {
        if (entity instanceof Player player) {
            if (!unlocked) {
                unlocked = true;
                gm.messageDrawer.addMessage(String.format("You unlocked the %s", unlockableItem.getName()));
            } else {
                gm.messageDrawer.addMessage(String.format("The %s is already unlocked", unlockableItem.getName()));
            }
        }

    }

}
