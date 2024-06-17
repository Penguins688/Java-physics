import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


class Surface extends JPanel implements MouseListener, MouseMotionListener {
    private ArrayList<Particle> particles;
    
    public Surface(ArrayList<Particle> particles) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.particles = particles;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLUE);
        for (Particle particle : particles) {
            g2d.fillOval((int) particle.getX(), (int) particle.getY(), 10, 10);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        particles.add(new Particle(e.getX(), e.getY(), 0, 0, 5));
        repaint();
    }

    // Empty implementations for mouse events
    @Override
    public void mousePressed(MouseEvent e) {
        // No specific logic needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // No specific logic needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // No specific logic needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // No specific logic needed
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        particles.add(new Particle(e.getX(), e.getY(), 0, 0, 5));
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // No specific logic needed
    }
}

public class Main extends JFrame {

    private ArrayList<Particle> particles;
    private Surface surface;
    private Timer timer;

    public Main() {
        particles = new ArrayList<>();
        
        surface = new Surface(particles);
        add(surface);

        setTitle("Java Physics");
        setSize(1024, 1024);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(8, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateParticles();
                surface.repaint();
            }
        });
    }
    private void updateParticles() {
        for (Particle particle : particles) {
            particle.update();
        }

        for (int i = 0; i < particles.size(); i++) {
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p1 = particles.get(i);
                Particle p2 = particles.get(j);

                double dx = p2.getX() - p1.getX();
                double dy = p2.getY() - p1.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double minDistance = p1.getRadius() + p2.getRadius();

                if (distance < minDistance) {
                    double overlap = minDistance - distance;
                    double moveX = (dx / distance) * overlap * 0.5;
                    double moveY = (dy / distance) * overlap * 0.5;

                    p1.setX(p1.getX() - moveX);
                    p1.setY(p1.getY() - moveY);
                    p2.setX(p2.getX() + moveX);
                    p2.setY(p2.getY() + moveY);

                    handleCollision(p1, p2);
                }
            }
        }
    }

    private void handleCollision(Particle p1, Particle p2) {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        double nx = dx / distance;
        double ny = dy / distance;

        double kx = p1.getXVel() - p2.getXVel();
        double ky = p1.getYVel() - p2.getYVel();
        double p = 2.0 * (nx * kx + ny * ky) / (p1.getMass() + p2.getMass());

        p1.setXVel(p1.getXVel() - p * p2.getMass() * nx);
        p1.setYVel(p1.getYVel() - p * p2.getMass() * ny);
        p2.setXVel(p2.getXVel() + p * p1.getMass() * nx);
        p2.setYVel(p2.getYVel() + p * p1.getMass() * ny);
    }

    public void startAnimation() {
        timer.start();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main ex = new Main();
                ex.setVisible(true);
                ex.startAnimation();
            }
        });
    }
}