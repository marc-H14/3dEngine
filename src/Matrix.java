import java.util.Arrays;

public class Matrix {
    private double[][] matrix;
    private int height;
    private int width;

    public Matrix(int height, int width) {
        this.width = width;
        this.height = height;
        this.matrix = new double[height][width];
    }

    public Matrix(double[][] values) {
        matrix = values;
        height = values.length;
        width = values[0].length;
    }

    public void set(int i, int j, double value) {
        matrix[i][j] = value;
    }

    public double get(int i, int j) {
        if (i < 0 || i >= height || j < 0 || j >= width)
            throw new IndexOutOfBoundsException("i_max = " + height + ", j_max = " + width);
        return matrix[i][j];
    }

    public Matrix mult(Matrix matrix) {
        if (this.width != matrix.height) throw new IllegalArgumentException();
        Matrix temp = new Matrix(this.height, matrix.width);
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < matrix.width; j++) {
                double sum = 0;
                for (int k = 0; k < this.width; k++) sum += this.get(i, k) * matrix.get(k, j);
                temp.set(i, j, sum);
            }
        }
        return temp;
    }

    public static Matrix createPointAt(Vector position, Vector target, Vector up) {
        Vector newForward = target.subtract(position);
        newForward.normalise();
        Vector a = newForward.scale(up.dotProduct(newForward));
    }

    public static Matrix createProjection(int width, int height, double far, double near, double fov) {
        Matrix m = new Matrix(4, 4);
        double a = (double)height / (double)width;
        double fovRad = 1.0 / Math.tan(fov * 0.5 / 180.0 * Math.PI);
        m.set(0, 0, a * fovRad);
        m.set(1, 1, fovRad);
        m.set(2, 2, far / (far - near));
        m.set(3, 2, (-far * near) / (far - near));
        m.set(2, 3, 1.0);
        m.set(3, 3, 0.0);
        return m;
    }

    public static Matrix createIdentityMatrix() {
        Matrix m = new Matrix(4, 4);
        m.set(0, 0, 1.0f);
        m.set(1, 1, 1.0f);
        m.set(2, 2, 1.0f);
        m.set(3, 3, 1.0f);
        return m;
    }

    public static Matrix createRotationX(double angleRad) {
        Matrix m = new Matrix(4, 4);
        m.set(0, 0, 1.0);
        m.set(1, 1, Math.cos(angleRad));
        m.set(1, 2, Math.sin(angleRad));
        m.set(2, 1, - Math.sin(angleRad));
        m.set(2, 2, Math.cos(angleRad));
        m.set(3, 3, 1.0);
        return m;
    }

    public static Matrix createRotationY(double angleRad) {
        Matrix m = new Matrix(4, 4);
        m.set(0, 0, Math.cos(angleRad));
        m.set(0, 2, Math.sin(angleRad));
        m.set(2, 0, - Math.sin(angleRad));
        m.set(1, 1, 1.0f);
        m.set(2, 2, Math.cos(angleRad));
        m.set(3, 3, 1.0);
        return m;
    }

    public static Matrix createRotationZ(double angleRad) {
        Matrix m = new Matrix(4, 4);
        m.set(0, 0, Math.cos(angleRad));
        m.set(0, 1, Math.sin(angleRad));
        m.set(1, 0, - Math.sin(angleRad));
        m.set(1, 1, Math.cos(angleRad));
        m.set(2, 2, 1.0);
        m.set(3, 3, 1.0);
        return m;
    }

    public static Matrix createTranslation(double x, double y, double z) {
        Matrix m = new Matrix(4, 4);
        m.set(0, 0, 1.0);
        m.set(1, 1, 1.0);
        m.set(2, 2, 1.0);
        m.set(3, 3, 1.0);
        m.set(3, 0, x);
        m.set(3, 1, y);
        m.set(3, 2, z);
        return m;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int max = 0;
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                int len = Double.toString(this.get(i, j)).length();
                if (len > max) max = len;
            }
        }
        for (int i = 0; i < this.height; i++) {
            if (this.height == 1) sb.append("[");
            else if (i == 0) sb.append("┌");
            else if (i == this.height - 1) sb.append("└");
            else sb.append("|");
            for (int j = 0; j < this.width; j++) {
                sb.append(String.format("% " + max + "f", this.get(i, j)));
                if (j != this.width - 1) sb.append(", ");
            }
            if (this.height == 1) sb.append("]");
            else if (i == 0) sb.append("┐");
            else if (i == this.height - 1) sb.append("┘");
            else sb.append("|");
            sb.append("\n");
        }
        return sb.toString();
    }
}
