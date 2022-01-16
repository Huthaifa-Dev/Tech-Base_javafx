package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Teacher extends Pane {
    @FXML
    private Pane teacher;
    @FXML
    private ImageView teacherImage;
    @FXML
    private Label teacherName;
    @FXML
    private Label teacherWork;

    private int ID;
    public Teacher(String path,String name,String work,int ID) throws IOException {
        loadFXML(this);
        changePic(path);
        teacherName.setText(name);
        teacherWork.setText(work);
        this.ID= ID;
    }

    public int getID() {
        return ID;
    }

    private void changePic(String path) throws IOException {
        //creating the image object
        InputStream stream = new FileInputStream(path);
        Image image = new Image(stream);
        //Setting image to the image view
        teacherImage.setImage(image);
        //Setting the image view parameters
        teacherImage.setX(0);
        teacherImage.setY(0);
        teacherImage.setPreserveRatio(true);
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
