package org.flower.parser;

import org.opencv.core.Size;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

public class Displayer {

    ArrayList<FlowerImage> images;

    public Displayer(ArrayList<FlowerImage> images){
        this.images = images;
    }

    private JFrame createFrame(){

        JFrame frame = new JFrame();

        frame.setResizable(true);
        frame.setSize(300, 300);

        GridLayout gridLayout = new GridLayout(20, 20, 0, 0);
        frame.setLayout(gridLayout);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return frame;
    }

    public JFrame display(Size sizeOfImage, FlowerMouseAdapter mouseAdapter){

        JFrame frame = createFrame();

        for(FlowerImage image: images){

            JLabel label = new JLabel(new ImageIcon(image.asImage(sizeOfImage)));

            mouseAdapter.setFrame(frame);
            mouseAdapter.addImage(label, image);

            label.addMouseListener(mouseAdapter);
            frame.add(label);
        }

        frame.setVisible(true);
        return frame;
    }

    public JFrame display(Size sizeOfImage){
        return display(sizeOfImage, new FlowerMouseAdapter());
    }

    public JFrame display(){
        if (images.size() > 0)
            return display(images.get(0).getMat().size());
        return null;
    }

    public JFrame display(FlowerMouseAdapter mouseAdapter){
        if(images.size() > 0)
            return display(images.get(0).getMat().size(), mouseAdapter);
        return null;
    }

    public ArrayList<FlowerImage> getImages() {
        return images;
    }
}
