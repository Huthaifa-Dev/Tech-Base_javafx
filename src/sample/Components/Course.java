package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Course extends Pane {
    @FXML
    private Pane course;
    @FXML
    private ImageView courseImageView;

    private String ID;
    public Course(String path1,String ID)  throws IOException {
        loadFXML(this);
        changePic(path1);
        this.ID=ID;
    }

    public Course() {

    }

    public String getID() {
        return ID;
    }

    public void changePic(String path) throws IOException {
        //creating the image object
        InputStream stream = new FileInputStream(path);
        Image image = new Image(stream);
        //Setting image to the image view
        courseImageView.setImage(image);
        //Setting the image view parameters
        courseImageView.setX(0);
        courseImageView.setY(0);
        courseImageView.setPreserveRatio(true);
    }

    public static <T extends Parent> void loadFXML(T component) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(theClass -> component);
        loader.setRoot(component);
        String fileName = component.getClass().getSimpleName() + ".fxml";
        try {
            loader.load(component.getClass().getResourceAsStream(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
