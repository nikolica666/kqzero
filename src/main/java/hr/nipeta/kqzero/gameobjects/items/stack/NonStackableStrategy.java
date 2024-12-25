package hr.nipeta.kqzero.gameobjects.items.stack;

public enum NonStackableStrategy implements StackStrategy {

    INSTANCE;

    @Override
    public boolean canStack() {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

}
