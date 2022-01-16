package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CourseFile extends Pane {
    @FXML
    private Pane CoursePane;
    @FXML
    private ImageView CourseImage;
    @FXML
    private Label CourseName;
    @FXML
    private Label completion;

    private String id;
    public CourseFile(String path,String name,int c, String id) throws FileNotFoundException {
        loadFXML(this);
        this.CourseName.setText(name);
        loadImage(path);
        this.completion.setText(c+"%");
        this.id = id;
    }


    public String getID(){
        return id;
    }

    private void loadImage(String path) throws FileNotFoundException {
        InputStream stream = new FileInputStream(path);
        javafx.scene.image.Image image = new Image(stream);
        //Setting image to the image view
        CourseImage.setImage(image);
        //Setting the image view parameters
        CourseImage.setX(0);
        CourseImage.setY(0);
        CourseImage.setPreserveRatio(true);
        Rectangle r = new Rectangle(0,0,280,160);
        r.setArcHeight(10);
        r.setArcWidth(10);

        CourseImage.setClip(r);
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
