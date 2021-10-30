public class Vector {
    public double x;
    public double y;
    public double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector scale(double c) {
        Vector temp = new Vector(x, y, z);
        temp.x *= c;
        temp.y *= c;
        temp.z *= c;
        return temp;
    }

    public Vector add(Vector b) {
        return new Vector(x + b.x, y + b.y, z + b.z);
    }

    public Vector subtract(Vector b) {
        return new Vector(x - b.x, y - b.y, z - b.z);
    }

    public Vector mult(Matrix matrix) {
        Vector out = new Vector(
                x * matrix.get(0, 0) + y * matrix.get(1, 0) + z * matrix.get(2, 0) + matrix.get(3, 0),
                x * matrix.get(0, 1) + y * matrix.get(1, 1) + z * matrix.get(2, 1) + matrix.get(3, 1),
                x * matrix.get(0, 2) + y * matrix.get(1, 2) + z * matrix.get(2, 2) + matrix.get(3, 2)
        );
        double w = x * matrix.get(0, 3) + y * matrix.get(1, 3) + z * matrix.get(2, 3) + matrix.get(3, 3);
        if (w != 0) out = out.scale(1 / w);
        return out;
    }

    public Vector multGeneric(Matrix matrix) {
        Vector out = new Vector(
                x * matrix.get(0, 0) + y * matrix.get(1, 0) + z * matrix.get(2, 0),
                x * matrix.get(0, 1) + y * matrix.get(1, 1) + z * matrix.get(2, 1),
                x * matrix.get(0, 2) + y * matrix.get(1, 2) + z * matrix.get(2, 2)
        );
        return out;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void normalise() {
        this = this.scale(1 / this.length());
    }

    public double dotProduct(Vector b) {
        return x * b.x + y * b.y + z * b.z;
    }

    public Vector crossProduct(Vector b) {
        return new Vector(
                y * b.z - z * b.y,
                z * b.x - x * b.z,
                x * b.y - y * b.x
        );
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Vector{" + x + ", " + y + ", " + z + "}";
    }
}
