package hr.nipeta.kqzero.collision;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CollisionTolerance {

    public double top;
    public double bot;
    public double left;
    public double right;

    public CollisionTolerance(double tolerance) {
        this(tolerance, tolerance, tolerance, tolerance);
    }

}
