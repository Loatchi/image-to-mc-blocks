package org.flower.parser;

import javax.swing.*;
import java.awt.*;

public class FlowerPanel extends JLabel {

    Image image;
    int x = 0;
    int y = 0;

    public FlowerPanel(Image image){
        this.image = image;
    }

    public void setInnerLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int[] getInnerLocation(){
        return new int[]{x, y};
    }

    public void setImage(Image image){
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, image.getWidth(null), image.getHeight(null),this);
    }
}
