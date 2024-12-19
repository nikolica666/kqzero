package hr.nipeta.kqzero.collision;

public class CollisionTolerance {

    public double top;
    public double bot;
    public double left;
    public double right;
    public double width;
    public double height;

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
