package org.flower.minecraft;

import org.flower.parser.FlowerImage;

import java.io.File;
import java.util.ArrayList;

public class BlockImageData {

    // Path to the file where all the blocks images are.
    private static String path = "Blocks/";
    public static ArrayList<FlowerImage> blocks = createBlockData();
    private static double[] empty = new double[]{255, 255, 255, 255};

    private static ArrayList<FlowerImage> createBlockData() {

        File directory = new File(path);
        String[] filenames = directory.list();

        ArrayList<FlowerImage> images = new ArrayList<>();

        assert filenames != null;
        for(String filename: filenames){
            FlowerImage f = new FlowerImage(path + filename, filename.substring(0, filename.length() - 4));
            f.getAverageColor();
            images.add(f);
        }

        return images;
    }

    public static int find(double[] color){

        if (color.length == 4 && color[3] == 0.0d){
            color = empty;
        }

        double lowerAvg = Double.MAX_VALUE;
        int imageIdx = 0;

        int i = 0;

        for(; i < blocks.size(); i++){

            FlowerImage block = blocks.get(i);

            double[] color2 = block.getAverageColor();

            // distance between 2 points in a 3-dimension
            double x = color2[0] - color[0];
            double y = color2[1] - color[1];
            double z = color2[2] - color[2];

            double avg = Math.sqrt(x*x + y*y + z*z);

            if (lowerAvg > avg){
                imageIdx = i;
                lowerAvg = avg;
            }
        }
        return imageIdx;
    }
}
