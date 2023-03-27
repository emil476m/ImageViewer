package dk.easv;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SlidShow implements Runnable {

    private List<Image> images;
    private int currentImageIndex;

    private int timeMS;

    ImageView imageView;

    Button btn;

    static boolean slideShow;

    private static Image image = null;
    public SlidShow(List<Image> image, int time, int currentImageIndex ,ImageView imageView)
    {
        this.images = image;
        this.currentImageIndex = currentImageIndex;
        this.imageView = imageView;
        this.timeMS = time;

    }

    public static void setSlideShow(boolean slide)
    {
        slideShow = slide;
    }

    @Override
    public void run() {
        if(!images.isEmpty())
        {
           while (slideShow) {
               Platform.runLater(() -> {
                   imageView.setImage(images.get(currentImageIndex));

               });
               currentImageIndex = (currentImageIndex+1) % images.size();
               try {
                   TimeUnit.SECONDS.sleep(timeMS);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        }
    }
}
