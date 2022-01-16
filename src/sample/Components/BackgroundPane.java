package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BackgroundPane extends Pane {
    @FXML
    private FlowPane BackgroundContent;
    @FXML
    private ImageView BackgroundImage;
    public BackgroundPane(Node pane) throws FileNotFoundException {
        loadFXML(this);
        InputStream stream = new FileInputStream("src\\sample\\img\\Blur05.png");
        javafx.scene.image.Image image = new Image(stream);
        //Setting image to the image view
        BackgroundImage.setImage(image);
        //Setting the image view parameters
        BackgroundImage.setX(0);
        BackgroundImage.setY(0);
        BackgroundContent.getChildren().add(pane);

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
