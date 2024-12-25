package hr.nipeta.kqzero.gameobjects.items.stack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StackableStackStrategy implements StackStrategy {

    private int maxStackSize;

    public static StackableStackStrategy ofMaxStack(int maxStack) {
        return new StackableStackStrategy(maxStack);
    }

    @Override
    public boolean canStack() {
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }
}
