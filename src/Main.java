import javax.swing.JFrame;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("A simple ball game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(960, 580);
        frame.setLocationRelativeTo(null);
        // create a DisplayPanel object
        DisplayPanel panel = new DisplayPanel();
        // add it to the frame
        frame.add(panel);
        // call setVisible after everything else
        frame.setVisible(true);
    }
}