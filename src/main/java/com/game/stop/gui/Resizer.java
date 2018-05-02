package com.game.stop.gui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Class to resize the Buffered Images
 *
 * @author davidfalcao
 *
 */
public class Resizer {

    /**
     * Constructor
     */
    public Resizer(){
        super();
    }

    /**
     * Resize the image
     *
     * @param image
     * @param width
     * @param height
     * @return
     */
    public BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(image, 0, 0, width, height, null);
        g2d.dispose();
        return bi;
    }
}
