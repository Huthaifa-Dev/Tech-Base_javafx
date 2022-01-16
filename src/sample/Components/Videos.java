package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class Videos extends Pane {

    @FXML
    private ScrollPane VideosList;
    @FXML
    private Pane VideosContent;
    @FXML
    private Pane backCourses;
    private String lastLib;
    public Videos(String lastLib) {
        this.lastLib = lastLib;
        loadFXML(this);

    }

    public void addVideosList(VBox videosList) {
        VideosList.setContent(videosList);
    }
    public void setVideosContent(VideoContent videosContent) {
        VideosContent.getChildren().removeAll();
        VideosContent.getChildren().clear();
        VideosContent.getChildren().add(videosContent);
    }

    public String getLastLib(){
        return lastLib;
    }
    public Pane getBackCourses(){
        return backCourses;
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
