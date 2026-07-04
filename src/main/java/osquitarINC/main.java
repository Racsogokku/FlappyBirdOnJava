package osquitarINC;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main (String[] args)  {
        Juego juego = new Juego(60);
        JFrame frame = new JFrame("Flappy Bird");
        frame.add(juego);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
