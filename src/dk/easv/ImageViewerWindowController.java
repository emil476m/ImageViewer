package dk.easv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewerWindowController
{
    private final List<Image> images = new ArrayList<>();
    @FXML
    private Label lblFileNameExtreme;
    @FXML
    private TextField txtTime;
    @FXML
    private Button btnSlidShow;
    private int currentImageIndex = 0;

    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    private static boolean toggleSlide = false;

    @FXML
    private void handleBtnLoadAction()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty())
        {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction()
    {
        if (!images.isEmpty())
        {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage()
    {
        if (!images.isEmpty())
        {
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    private void setToggleSlide(){
        if (toggleSlide == false){
            toggleSlide = true;
            btnSlidShow.setText("Stop");
        }
        else{
            toggleSlide = false;
            btnSlidShow.setText("Slide Show");
        }
    }

    public void handleBtnSlideShow(ActionEvent actionEvent) throws Exception {
        int time = Integer.parseInt(txtTime.getText());
        SlideShow slideShow = new SlideShow(images, time, currentImageIndex, imageView);
        setToggleSlide();
        Runnable threat = slideShow;

        slideShow.messageProperty().addListener((obs, o, n) -> {
            lblFileNameExtreme.setText(n);
        });
        SlideShow.setSlideShow(toggleSlide);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.schedule(threat,time,TimeUnit.SECONDS);
    }
}