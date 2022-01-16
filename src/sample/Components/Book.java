package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Book extends Pane {

    @FXML
    private Label bookName;
    @FXML
    private ImageView bookImage;

    private String location;
    public Book(String name,String path,String location) throws FileNotFoundException {
        loadFXML(this);
        bookName.setText(name);
        setImage(path);
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    private void setImage(String path) throws FileNotFoundException {
        //creating the image object
        InputStream stream = new FileInputStream(path);
        javafx.scene.image.Image image = new Image(stream);
        //Setting image to the image view
        bookImage.setImage(image);
        //Setting the image view parameters
        bookImage.setX(0);
        bookImage.setY(0);
        bookImage.setPreserveRatio(true);
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
