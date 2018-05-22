package gui;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JFrame {
    protected static final int WIDTH = 1024;
    protected static final int HEIGHT = 700;
    protected int ratio = 1;
    protected Dimension dim;

    public Page(){
        setResizable(false);
        setTitle("STOP game");
        pack();
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        calculateScreenRatio();
    }

    /**
     * Calculate window ratio
     *
     */
    public void calculateScreenRatio(){

        int ratio_x =  (int) dim.getWidth() / WIDTH;
        int ratio_y = (int) dim.getHeight() / HEIGHT;

        ratio = Math.min(ratio_x, ratio_y);

        if(ratio < 1)
            ratio =1;

    }

    /**
     * Start the window
     *
     */
    public void start() {
        setSize(WIDTH*ratio, HEIGHT*ratio);

        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
                - getSize().height / 2);

        setVisible(true);

    }

    public void hide_page(){
        setVisible(false);
    }

}
