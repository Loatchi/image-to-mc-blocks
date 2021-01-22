package org.flower.parser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class FlowerMouseAdapter extends MouseAdapter {

    protected Map<JLabel, FlowerImage> map = new HashMap<>();
    protected JFrame frame;

    public FlowerMouseAdapter(){

    }

    public void addImage(JLabel label, FlowerImage image) {
        map.put(label, image);
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
