package vn.conguyetduong.hogwarts.test;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        createThumbnail();
    }
    private static void createThumbnail() {
        String baseDir = "/home/namvuong1402/Documents/projects/spring-boot/hogwarts/hogwarts/src/test/java/vn/conguyetduong/hogwarts/test/";
        File input = new File(baseDir + "input.png");

        File output = new File(baseDir + "thumbnail.jpg");

        try {
            Thumbnails.of(input)
                    .size(400, 400)
                    .crop(Positions.CENTER)
                    .outputFormat("jpg")
                    .toFile(output);

            System.out.println("Thumbnail created: " + output.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Thumbnail could not be created: " + e);
        }
    }
}
