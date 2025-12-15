/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jana-
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PictureChangeApp extends JFrame {

    private BufferedImage originalImage;
    private BufferedImage image1;
    private BufferedImage image2;

    private JLabel label1;
    private JLabel label2;

    private JButton stepButton;
    private JButton startStopButton;
    private JButton resetButton;

    private volatile boolean animationRunning = false;
    private Thread animationThread;

    private static final long WARNING_TIME_MS = 500; // Alert time

    public PictureChangeApp() {
        super("Picture Change App");

        // ====== Labels ======
        label1 = new JLabel("Image 1 (Step)", SwingConstants.CENTER);
        label2 = new JLabel("Image 2 (Animation)", SwingConstants.CENTER);

        label1.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        label2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        JPanel imagesPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        imagesPanel.add(label1);
        imagesPanel.add(label2);

        // ====== Buttons ======
        stepButton = new JButton("Step");
        startStopButton = new JButton("Start");
        resetButton = new JButton("Reset");

        stepButton.setEnabled(false);
        startStopButton.setEnabled(false);
        resetButton.setEnabled(false);

        Font btnFont = new Font("Arial", Font.BOLD, 14);
        stepButton.setFont(btnFont);
        startStopButton.setFont(btnFont);
        resetButton.setFont(btnFont);

        stepButton.setBackground(new Color(70,130,180));
        startStopButton.setBackground(new Color(46,139,87));
        resetButton.setBackground(new Color(178,34,34));

        stepButton.setForeground(Color.WHITE);
        startStopButton.setForeground(Color.WHITE);
        resetButton.setForeground(Color.WHITE);

        stepButton.addActionListener(e -> applyStepFilterThreaded());
        startStopButton.addActionListener(e -> toggleAnimation());
        resetButton.addActionListener(e -> resetImages());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttons.add(stepButton);
        buttons.add(startStopButton);
        buttons.add(resetButton);

        setLayout(new BorderLayout(10, 10));
        add(imagesPanel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        getContentPane().setBackground(new Color(245,245,245));

        loadImage();

        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ================== تحميل الصورة ==================
    private void loadImage() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage img = ImageIO.read(chooser.getSelectedFile());

                originalImage = copy(img);
                image1 = copy(img);
                image2 = copy(img);

                label1.setIcon(new ImageIcon(image1));
                label2.setIcon(new ImageIcon(image2));

                stepButton.setEnabled(true);
                startStopButton.setEnabled(true);
                resetButton.setEnabled(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Image loading failed");
            }
        }
    }

    // ================== Step Filter (Thread) ==================
    private void applyStepFilterThreaded() {
        new Thread(() -> {
            long start = System.currentTimeMillis();

            applyFilter(image1, 30, 5, 10);

            long duration = System.currentTimeMillis() - start;
            showWarningIfSlow(duration);

            SwingUtilities.invokeLater(() ->
                label1.setIcon(new ImageIcon(image1))
            );
        }).start();
    }

    // ================== Animation ==================
    private void toggleAnimation() {
        if (animationRunning) {
            animationRunning = false;
            startStopButton.setText("Start");
        } else {
            animationRunning = true;
            startStopButton.setText("Stop");

            animationThread = new Thread(() -> {
                while (animationRunning) {
                    long start = System.currentTimeMillis();

                    applyFilter(image2, 10, 25, 15);

                    long duration = System.currentTimeMillis() - start;
                    showWarningIfSlow(duration);

                    SwingUtilities.invokeLater(() ->
                        label2.setIcon(new ImageIcon(image2))
                    );

                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException ignored) {}
                }
            });
            animationThread.start();
        }
    }

    // ================== Reset ==================
    private void resetImages() {
        animationRunning = false;
        startStopButton.setText("Start");

        image1 = copy(originalImage);
        image2 = copy(originalImage);

        label1.setIcon(new ImageIcon(image1));
        label2.setIcon(new ImageIcon(image2));
    }

    // ================== Flliter ==================
    private void applyFilter(BufferedImage img, int rAdd, int gAdd, int bAdd) {
        int w = img.getWidth();
        int h = img.getHeight();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int rgb = img.getRGB(x, y);

                int a = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = rgb & 0xff;

                r = (r + rAdd) % 256;
                g = (g + gAdd) % 256;
                b = (b + bAdd) % 256;

                img.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
            }
        }
    }

    // ================== تنبيه البطء ==================
    private void showWarningIfSlow(long duration) {
        if (duration > WARNING_TIME_MS) {
            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                    this,
                    "We apologize, image processing will take some time." + duration + " ms)",
                    "Performance alert",
                    JOptionPane.WARNING_MESSAGE
                )
            );
        }
    }

    // ================== Copy the image ==================
    private BufferedImage copy(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(
                img.getWidth(),
                img.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics g = newImg.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return newImg;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
            new PictureChangeApp().setVisible(true)
        );
    }
}