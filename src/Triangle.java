import java.awt.*;

public class Triangle implements Comparable<Triangle>{
    public Vector p0;
    public Vector p1;
    public Vector p2;

    public Color color;

    public Triangle(Vector p0, Vector p1, Vector p2) {
        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
    }

    public Triangle(double p0X, double p0Y, double p0Z,
                    double p1X, double p1Y, double p1Z,
                    double p2X, double p2Y, double p2Z) {
        this.p0 = new Vector(p0X, p0Y, p0Z);
        this.p1 = new Vector(p1X, p1Y, p1Z);
        this.p2 = new Vector(p2X, p2Y, p2Z);
    }

    public Triangle() { };

    public int[] getX() {
        return new int[]{(int) p0.getX(), (int) p1.getX(), (int) p2.getX()};
    }

    public int[] getY() {
        return new int[]{(int) p0.getY(), (int) p1.getY(), (int) p2.getY()};
    }

    public Triangle copy() {
        return new Triangle(p0.x, p0.y, p0.z, p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
    }

    @Override
    public String toString() {
        return "Triangle{\n" +
                p0.toString() + "\n" +
                p1.toString() + "\n" +
                p2.toString() + "\n" +
                "}";
    }

    @Override
    public int compareTo(Triangle o) {
        Vector a = p0.add(p1.add(p2));
        Vector b = o.p0.add(o.p1.add(o.p2));
        if (a.z < b.z) return 1;
        else if (a.z > b.z) return -1;
        return 0;
    }
}
