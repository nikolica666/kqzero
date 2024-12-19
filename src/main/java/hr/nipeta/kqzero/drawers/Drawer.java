package hr.nipeta.kqzero.drawers;

import hr.nipeta.kqzero.GameManager;

public abstract class Drawer {

    protected final GameManager gm;

    protected Drawer(GameManager gm) {
        this.gm = gm;
    }

    public abstract void draw();

}
