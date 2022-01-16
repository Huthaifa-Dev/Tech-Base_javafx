package sample.Components;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

public class Status extends Pane {
    @FXML
    private Pane homePane;
    @FXML
    private ImageView teacher_image;
    @FXML
    private ImageView resume_image;
    @FXML
    private Label course_name;
    @FXML
    private Label course_branch;
    @FXML
    private Label teacher_name;
    public Status(String cname, String iname,String cpath,String ipath,String cbranch) throws IOException {
        loadFXML(this);
        setCourseName(cname);
        setTeacherName(iname);
        changeCoursePic(cpath);
        changeTeacherPic(ipath);
        setCourseBranch("       "+cbranch);
    }

    public void changeCoursePic(String path){
        Image image = new Image(path.substring(4).replace("\\","/"),460,0,true,false);
        System.out.println(image.toString());
        resume_image.setImage(image);
        Rectangle2D viewportRect = new Rectangle2D(0, 0, 460, 200);
        resume_image.setViewport(viewportRect);
        Rectangle r = new Rectangle(0,0,460,200);
        r.setArcHeight(10);
        r.setArcWidth(10);

        resume_image.setClip(r);

    }
    public void changeTeacherPic(String path) throws IOException {
        //creating the image object
        InputStream stream = new FileInputStream(path);
        Image image = new Image(stream,40,0,true,false);
        //Setting image to the image view
        teacher_image.setImage(image);
        //Setting the image view parameters
        teacher_image.setClip(new Circle(20, 20, 20));
    }
    public  void setCourseName(String name){
        course_name.setText(name);
    }
    public void setCourseBranch(String branch){
        course_branch.setText(branch);
    }
    public void setTeacherName(String name){
        teacher_name.setText(name);
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
