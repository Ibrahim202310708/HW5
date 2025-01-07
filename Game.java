import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {
    int x = 0;
    int y = 0;
    int min = 1;
    int max = 500;
    private Thread animator;
    private volatile boolean running = true;
    private JButton controlButton;

    public Game() {
        controlButton = new JButton("Stop");
        controlButton.addActionListener(e -> toggleAnimation());
        add(controlButton);
    }

    private void toggleAnimation() {
        running = !running;
        controlButton.setText(running ? "Stop" : "Continue");
    }

    private void moveBall() {
        int rnX = new Random().nextInt(max - min + 1) + min;
        int rnY = new Random().nextInt(max - min + 1) + min;
        x = rnX;
        y = rnY;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(x, y, 30, 30);
    }

    @Override
    public void run() {
        while (true) {
            if (running) {
                moveBall();
                repaint();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mini Tennis");
        Game game = new Game();
        frame.add(game);
        frame.setSize(300, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Thread animator = new Thread(game);
        animator.start();
    }
}