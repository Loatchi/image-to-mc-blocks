package org.flower.parser;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FlowerImage {

    protected Mat imageArray;
    protected double[] averageColor = null;
    protected String name;
    protected String path;

    public FlowerImage(Mat imgArray, String name){
        imageArray = imgArray;
        this.name = name;
    }

    public FlowerImage(String path, String name){
        imageArray = Imgcodecs.imread(path, Imgcodecs.IMREAD_UNCHANGED);

        Mat mat = new Mat();

        if(imageArray.type() == 16) {
            Imgproc.cvtColor(imageArray, mat, Imgproc.COLOR_BGR2BGRA);
            imageArray = mat;
        }


        this.path = path;
        this.name = name;
    }

    protected void display(Mat toDisplay){

        JFrame frame = new JFrame();
        Size size = toDisplay.size();
        frame.setSize((int) (size.width * 1.4), (int) (size.height * 1.4));
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage img = FlowerImage.matToImage(toDisplay);

        if (img == null){
            return;
        }

        FlowerPanel imgLabel = new FlowerPanel(img);
        frame.add(imgLabel);
        frame.setVisible(true);
    }

    public void show(){
        display(imageArray);
    }

    public void show(Size size){
        Mat toDisplay = new Mat();
        Imgproc.resize(imageArray, toDisplay, size, 0, 0, Imgproc.INTER_AREA);
        display(toDisplay);
    }

    public Mat resize(Size size){

        Mat resizedArray = new Mat();
        Imgproc.resize(imageArray, resizedArray, size, 0, 0, Imgproc.INTER_AREA);
        Mat a;
        a = imageArray;
        imageArray = resizedArray;

        return a;
    }

    public Mat resize(int width, int height){
        return resize(new Size(width, height));
    }

    public BufferedImage asImage(Size size){

        if (size.equals(imageArray.size())){
            return asImage();
        }

        Mat resizedArray = new Mat();
        Imgproc.resize(imageArray, resizedArray, size, 0, 0, Imgproc.INTER_AREA);
        return FlowerImage.matToImage(resizedArray);
    }

    public BufferedImage asImage(){
        return FlowerImage.matToImage(imageArray);
    }

    public static BufferedImage matToImage(Mat imgContainer){

        MatOfByte byteMatData = new MatOfByte();
        Imgcodecs.imencode(".png", imgContainer, byteMatData);
        byte[] byteArray = byteMatData.toArray();
        BufferedImage img;

        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            img = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return img;
    }

    public Mat getMat() {
        return imageArray;
    }

    public boolean hasTransparency(){

        double[] buffer;

        for (int y=0; y < imageArray.cols(); y++){

            for(int x=0; x < imageArray.rows(); x++){

                buffer = imageArray.get(x, y);

                if (buffer.length == 4) {
                    if (buffer[3] == 0.0) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public double[] getAverageColor() {

        if (averageColor != null){
            return averageColor;
        }

        double[] color = new double[]{0, 0, 0, 0};
        double[] buffer;

        int height = imageArray.height();
        int width = imageArray.width();

        double length = height * width;

        for(int y=0; y < height; y++){
            for(int x=0; x < width; x++){

                buffer = imageArray.get(x, y);

                for(int i=0; i < 3; i++){
                    color[i] += buffer[i] / length;
                }
            }
        }

        averageColor = color;
        return getAverageColor();
    }

    public String getName() {
        return name;
    }

    public File asFile(){
        return path != null ? new File(path) : null;
    }
}
