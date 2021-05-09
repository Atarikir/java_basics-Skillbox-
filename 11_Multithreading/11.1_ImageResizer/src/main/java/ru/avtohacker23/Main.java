package main.java.ru.avtohacker23;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int numberOfCores = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the folder with images:");
        String srcFolder = bufferedReader.readLine(); //"/home/pandora/Desktop/src";
        System.out.println("Enter the folder to copy:");
        String dstFolder = bufferedReader.readLine(); //"/home/pandora/Desktop/dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int numberOfThreads = files.length / numberOfCores;

        int var = 0;
        File[] temp = new File[numberOfThreads];

        for (File file : files) {
            temp[var++] = file;
            if (var == numberOfThreads) {
                new ru.avtohacker23.ImageResizer(temp, dstFolder, start).start();
                temp = new File[numberOfThreads];
                var = 0;
            }
        }

//        File[] files1 = new File[middle];
//        System.arraycopy(files, 0, files1, 0, middle);
//        ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
//        resizer1.start();
//
//        File[] files2 = new File[files.length - middle];
//        System.arraycopy(files, middle, files2, 0, files2.length);
//        ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
//        resizer2.start();
    }
}
