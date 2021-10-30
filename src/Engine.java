import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Timer;

public class Engine extends JFrame {
    private final static int WIDTH = 640;
    private final static int HEIGHT = 480;
    private final static double NEAR = 0.1;
    private final static double FAR = 1000;
    private final static double FOV = 90.0;

    private static Matrix projectionMatrix;

    private Vector light;

    private BufferedImage image;
    private JLabel label;

    private Mesh mesh;
    private Vector camera;
    private Keys keys;
    private Matrix matWorld;

    private long startTick;
    private long lastTick;
    private double theta;

    public static void main(String[] args) {
        Engine engine = new Engine();
        Graphics2D g2d = (Graphics2D) engine.image.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);
        engine.label.setIcon(new ImageIcon(engine.image));
        engine.label.revalidate();
        engine.label.repaint();
        g2d.dispose();
    }

    public Engine() {
        super("DrawImage");
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        projectionMatrix = Matrix.createProjection(WIDTH, HEIGHT, FAR, NEAR, FOV);



        light = new Vector(0, 0, -1);
        light.normalise();

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        label = new JLabel(new ImageIcon(image));
        this.add(label);
        lastTick = 0;
        startTick = System.currentTimeMillis();
        mesh = new Mesh();
        try {
            mesh.load(Paths.get("C:\\Users\\Marc Hofer\\Downloads\\untitled.obj"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera = new Vector(0, 0, 0);
        keys = new Keys();
        theta = 0;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                keys.add(e.getKeyChar());
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keys.remove(e.getKeyChar());
            }
        });

        int time = 1000 / 60;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                            tick();
                });
            }
        }, 0, time);
    }

    private void tick() {
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setBackground(new Color(255, 255, 255, 0));
        graphics.clearRect(0,0, WIDTH, HEIGHT);
        graphics.dispose();

        theta += 0.00174533 * (System.currentTimeMillis() - lastTick);
        lastTick = System.currentTimeMillis();

        Matrix matRotZ = Matrix.createRotationZ(theta * 0.5);
        Matrix matRotX = Matrix.createRotationX(theta);
        Matrix translationMatrix = Matrix.createTranslation(0.0, 0.0, 16.0);
        matWorld = Matrix.createIdentityMatrix();
        matWorld = matRotZ.mult(matRotX);
        matWorld = matWorld.mult(translationMatrix);

        /*if (keys.forward) camera.z += 1;
        else if (keys.back) camera.z -= 1;
        if (keys.left) camera.x += 1;
        else if (keys.right) camera.x -= 1;*/
        ArrayList<Triangle> drawTriangles = new ArrayList<>();

        for (Triangle triangle: mesh.getTriangles()) {
            Triangle triTransformed = triangle.copy();
            triTransformed.p0 = triangle.p0.mult(matWorld);
            triTransformed.p1 = triangle.p1.mult(matWorld);
            triTransformed.p2 = triangle.p2.mult(matWorld);

            Vector line1 = triTransformed.p1.subtract(triTransformed.p0);
            Vector line2 = triTransformed.p2.subtract(triTransformed.p0);
            Vector normal = line1.crossProduct(line2);
            normal.normalise();

            if (normal.dotProduct(triTransformed.p0.subtract(camera)) >= 0.0) continue;

            Triangle triProjected = new Triangle();
            triProjected.p0 = triTransformed.p0.mult(projectionMatrix);
            triProjected.p1 = triTransformed.p1.mult(projectionMatrix);
            triProjected.p2 = triTransformed.p2.mult(projectionMatrix);


            Vector offset = new Vector(1, 1, 0);
            triProjected.p0 = triProjected.p0.add(offset);
            triProjected.p1 = triProjected.p1.add(offset);
            triProjected.p2 = triProjected.p2.add(offset);

            Matrix scale = new Matrix(3, 3);
            scale.set(0, 0, 0.5 * (double)WIDTH);
            scale.set(1, 1, 0.5 * (double)HEIGHT);
            scale.set(2, 2, 1.0);
            triProjected.p0 = triProjected.p0.multGeneric(scale);
            triProjected.p1 = triProjected.p1.multGeneric(scale);
            triProjected.p2 = triProjected.p2.multGeneric(scale);


            triProjected.color = new Color(0, 0, 0, (float) Math.abs(normal.dotProduct(light)));
            drawTriangles.add(triProjected);
        }
        Collections.sort(drawTriangles);
        for (Triangle triangle: drawTriangles) {
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            g2d.setColor(triangle.color);
            g2d.fillPolygon(triangle.getX(), triangle.getY(), 3);
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(triangle.getX(), triangle.getY(), 3);
            label.setIcon(new ImageIcon(image));
            label.revalidate();
            label.repaint();
            g2d.dispose();
        }
    }

    private void loadImage(Path path){
        try {
            image = ImageIO.read(Files.newInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getSize().width, getSize().height);
        g2d.drawImage(image, 0, 40, label);
    }
}