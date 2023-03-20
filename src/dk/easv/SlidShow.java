package dk.easv;

import javafx.scene.image.Image;

import java.util.List;

public class SlidShow implements Runnable{

    private List<Image> images;
    private int currentImageIndex;

    private int timeMS;

    private Image image = null;
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
            currentImageIndex = 0;
           image = images.get((currentImageIndex) % images.size());
        }
        else if(currentImageIndex < images.size())
        {
           image = images.get((currentImageIndex + 1) % images.size());
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        getImage();
    }

    private Image getImage()
    {
        return image;
    }
}
