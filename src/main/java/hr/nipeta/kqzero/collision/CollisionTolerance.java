package hr.nipeta.kqzero.collision;

public class CollisionTolerance {

    public final double top;
    public final double bot;
    public final double left;
    public final double right;
    public final double width;
    public final double height;

    public CollisionTolerance(double tolerance) {
        this(tolerance, tolerance, tolerance, tolerance);
    }

    public CollisionTolerance(double top, double bot, double left, double right) {
        this.top = top;
        this.bot = bot;
        this.left = left;
        this.right = right;
        this.width = 1 - left - right;
        this.height = 1 - top - bot;
    }

}
