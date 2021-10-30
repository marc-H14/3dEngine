import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class Mesh {
    private ArrayList<Triangle> triangles;

    public Mesh() {
        triangles = new ArrayList<>();
        /*triangles.add(new Triangle(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f));
        triangles.add(new Triangle(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f));

        triangles.add(new Triangle(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f));
        triangles.add(new Triangle(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f));

        triangles.add(new Triangle(1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f));
        triangles.add(new Triangle(1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f));

        triangles.add(new Triangle(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f));
        triangles.add(new Triangle(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        triangles.add(new Triangle(0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f));
        triangles.add(new Triangle(0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f));

        triangles.add(new Triangle(1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f));
        triangles.add(new Triangle(1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f));*/
    }

    public void load(Path path) throws IOException {
        ArrayList<Vector> vertices = new ArrayList<>();
        Files.lines(path).forEach((line) -> {
            if (line.length() < 2) return;
            switch (line.charAt(0)) {
                case 'v':
                    String[] v = line.substring(2).split(" ");
                    vertices.add(new Vector(
                            Double.parseDouble(v[0]),
                            Double.parseDouble(v[1]),
                            Double.parseDouble(v[2])
                            ));
                    break;
                case 'f':
                    String[] f = line.substring(2).split(" ");
                    triangles.add(new Triangle(vertices.get(Integer.parseInt(f[0]) - 1),
                            vertices.get(Integer.parseInt(f[1]) - 1),
                            vertices.get(Integer.parseInt(f[2]) - 1)
                            ));
                    break;
            }
        });
    }

    public ArrayList<Triangle> getTriangles() {
        return triangles;
    }
}
