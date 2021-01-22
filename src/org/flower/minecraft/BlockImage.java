package org.flower.minecraft;

import org.flower.parser.FlowerImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.security.auth.login.CredentialNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BlockImage extends FlowerImage {

    Map<Integer, Integer> blockData = new HashMap<>();

    public BlockImage(String path, String name, Size size) {
        super(path, name);
        resize(size);
        createImage();
    }

    public BlockImage(String path, String name){
        super(path, name);
        createImage();
    }

    private void createImage(){

        Mat mat = new Mat(16*imageArray.rows(), 16*imageArray.cols(), imageArray.type());

        int i=0;

        for(int y=0; y < imageArray.cols(); y++){
            for(int x=0; x < imageArray.rows(); x++){

                double[] color = imageArray.get(x, y);

                try {
                    int blockIdx = BlockImageData.find(color);
                    BlockImageData.blocks.get(blockIdx)
                            .getMat().copyTo(mat.rowRange(x*16, x*16+16).colRange(y*16, y*16+16));

                    if(blockData.containsKey(blockIdx)){
                        blockData.put(blockIdx, blockData.get(blockIdx) + 1);
                    } else {
                        blockData.put(blockIdx, 1);
                    }

                } catch (NullPointerException ignored){
                    System.out.printf("%s, %s", x, y);
                }
            }
        }
        imageArray = mat;
    }

    public void writeAsEnum(String filename){

        try {

            FileWriter f = new FileWriter(new File(filename + ".blocks"));

            StringBuilder toWrite = new StringBuilder();

            for (int idx: blockData.keySet()) {
                toWrite
                        .append(BlockImageData.blocks.get(idx).getName())
                        .append(": ")
                        .append(blockData.get(idx))
                        .append("\r\n");
            }


            f.write(toWrite.toString());
            f.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
