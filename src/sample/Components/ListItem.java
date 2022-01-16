package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ListItem extends Pane {

    @FXML
    private Label VName;
    private final String ID;
    public ListItem(String name,String ID) {
        loadFXML(this);
        VName.setText(name);
        this.ID = ID;
    }

    public String getID() {
        return ID;
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
