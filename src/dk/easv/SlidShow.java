package dk.easv;

import javafx.scene.image.Image;

import java.util.List;

public class SlidShow implements Runnable{

    private List<Image> images;
    private int currentImageIndex;

    private int timeMS;

    private static int imageNumber;

    private static Image image = null;
    public SlidShow(List<Image> image, int imageIndex, int time)
    {
        images = images;
        currentImageIndex = imageIndex;
        timeMS = time*1000;
    }
    @Override
    public void run() {
        if(currentImageIndex > images.size())
        {
            //currentImageIndex = 0;
           //image = images.get((currentImageIndex) % images.size());
            imageNumber = 0;
        }
        else if(currentImageIndex < images.size())
        {
           //image = images.get((currentImageIndex + 1) % images.size());
            imageNumber = currentImageIndex + 1;
        }
        try {
            Thread.sleep(timeMS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getImage()
    {
        return imageNumber;
    }
}
