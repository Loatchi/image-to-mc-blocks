import org.flower.minecraft.BlockImage;
import org.flower.parser.FlowerImage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.SimpleTimeZone;

public class Main {

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        BlockImage image = new BlockImage("stuff.jpg", "test", new Size(100, 100));
        System.out.println(image.getMat().size());
        Imgcodecs.imwrite("test2.png", image.getMat());
        image.show(new Size(512, 512));
        image.writeAsEnum("truc");

    }
}
