package gui.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Allow to paint a Jframe
 *
 * @author davidfalcao
 *
 */
public class ImagePanel extends JComponent {
    /**
     *
     */
    private static final long serialVersionUID = -1018299225039420372L;
    private Image image;

    /**
     * ImagePanel Constructor
     *
     * @param image
     */
    public ImagePanel(Image image) {
        this.image = image;
    }

    /**
     * Override of paintComponent
     *
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
