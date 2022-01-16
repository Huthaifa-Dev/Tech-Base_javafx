package sample.Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;


import java.io.IOException;

public class VideoContent extends Pane {

    @FXML
    private Pane openGom;

    @FXML
    private Label V_name;
    public VideoContent(String Name,String path) {


        loadFXML(this);
    }

    public Pane getOpenGom() {
        return openGom;
    }

    private void setV_name(String name){
        V_name.setText(name);
    }
    private void openExternal(String program, String file) throws IOException {
        String command = program;
        String arg = file;
        //Building a process
        ProcessBuilder builder = new ProcessBuilder(command, arg);
        //System.out.println("Executing the external program . . . . . . . .");
        //Starting the process
        builder.start();
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
