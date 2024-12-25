package hr.nipeta.kqzero.gameobjects.items.stack;

// TODO this can simply be enum, but we'll leave as is for fun (for now)
public interface StackStrategy {
    boolean canStack();
    int getMaxStackSize();
}
