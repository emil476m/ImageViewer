package dk.easv;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SlideShow extends Task<Image> {

    private List<Image> images;
    private int currentImageIndex;

    private int timeMS;

    ImageView imageView;

    Button btn;

    static boolean slideShow;

    private static Image image = null;
    public SlideShow(List<Image> image, int time, int currentImageIndex , ImageView imageView)
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
                   File file = new File(images.get(currentImageIndex).getUrl());
                   this.updateMessage(file.getName());

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

    @Override
    protected Image call() throws Exception {
        run();
        Image image = images.get(currentImageIndex);
        return image;
    }
}
