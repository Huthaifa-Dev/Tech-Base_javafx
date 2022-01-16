package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import sample.Components.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
public class Controller {


    private String currCourseID = "JS1";
    private String currLibID = "JS";

    @FXML
    private Pane contentContainer;

    @FXML
    private void goHome() throws IOException {
        System.out.println("clicked");
        contentContainer.getChildren().removeAll();
        contentContainer.getChildren().clear();
        contentContainer.setStyle("-fx-padding: 0");
        try {
            Connection con = getConnection();

            PreparedStatement statement = con.prepareStatement("select COURSES.NAME as cname,INSTRUCTORS.NAME as iname,INSTRUCTORS.IMAGE as iimage, INSTRUCTORS.COURSEPIC as cimage,VIDEOS.NAME as vname from USERS,COURSES,INSTRUCTORS,VIDEOS where USERS.LASTACCESS = COURSES.ID and COURSES.TEACHER_ID = INSTRUCTORS.ID and VIDEOS.ID = USERS.LASTVID");
            System.out.println("finished statement");
            ResultSet resultSet = statement.executeQuery();
            System.out.println("finished executing");

            ArrayList<String> array = new ArrayList<String>();
            while (resultSet.next()) {
                //System.out.println("loop");
                Status home = new Status(resultSet.getString("CNAME"),resultSet.getString("iname"),resultSet.getString("cimage"),resultSet.getString("iimage"),resultSet.getString("VNAME"));
                //Home home = new Home(resultSet.getString("INSTRUCTORS.IMAGE"),"",resultSet.getString("COURSES.NAME"),"",resultSet.getString("INSTRUCTORS.NAME"));

                //System.out.println(resultSet.getString("VNAME"));
                //System.out.println(resultSet.getString("CNAME"));
                //System.out.println(resultSet.getString("iname"));
                //System.out.println(resultSet.getString("cimage"));
                //System.out.println(resultSet.getString("iimage"));

                contentContainer.setStyle("-fx-padding: 65 85 10 85");
                contentContainer.getChildren().add(home);
            }
        }catch (Exception e){

        }
    }
    @FXML
    private void goCourses() {
        System.out.println("clicked");
        contentContainer.getChildren().removeAll();
        contentContainer.getChildren().clear();
        contentContainer.setStyle("-fx-padding: 0");
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setPrefWrapLength(760);
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("select * from LIBRARY");

            ResultSet resultSet = statement.executeQuery();

            ArrayList<String>array = new ArrayList<String>();

            while(resultSet.next()){

                Course c = new Course(resultSet.getString("IMAGE"),resultSet.getString("LIBCODE"));

                flowPane.getChildren().add(c);
               c.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                   @Override
                   public void handle(MouseEvent event) {
                       enterLibrary(c.getID());
                       currLibID=c.getID();
                   }
               });
            }
            flowPane.setStyle("-fx-padding: 65 85 10 85");
            contentContainer.getChildren().add(flowPane);
        }catch (Exception e){

        }
    }

    private void enterLibrary(String id){
        contentContainer.getChildren().removeAll();
        contentContainer.getChildren().clear();
        contentContainer.setStyle("-fx-padding: 0");
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select COURSES.ID as id,COURSES.NAME as cname, INSTRUCTORS.COURSEPIC as cimage,COURSES.DONE as done from COURSES,INSTRUCTORS where COURSES.TEACHER_ID=INSTRUCTORS.ID and COURSES.ID like \'"+id+"%\'");

            ResultSet rSet = statement.executeQuery();

            FlowPane Courses = new FlowPane();
            Courses.setHgap(20);
            Courses.setVgap(30);
            Courses.setPrefWrapLength(900);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            while(rSet.next()) {
                CourseFile course = new CourseFile(rSet.getString("cimage"),rSet.getString("cname"),rSet.getInt("done"),rSet.getString("id"));
                Courses.getChildren().add(course);
                course.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        enterCourse(course.getID());
                        currCourseID = course.getID();
                    }
                });
            }

            BackgroundPane backgroundPane = new BackgroundPane(Courses);
            scrollPane.setContent(backgroundPane);

            contentContainer.getChildren().add(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void enterTeacher(int id){
        contentContainer.getChildren().removeAll();
        contentContainer.getChildren().clear();
        contentContainer.setStyle("-fx-padding: 0");
        try {
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("select COURSES.ID as id,COURSES.NAME as cname, INSTRUCTORS.COURSEPIC as cimage,COURSES.DONE as done from COURSES,INSTRUCTORS where COURSES.TEACHER_ID=INSTRUCTORS.ID and COURSES.TEACHER_ID = "+id+"");

            ResultSet rSet = statement.executeQuery();

            FlowPane Courses = new FlowPane();
            Courses.setHgap(20);
            Courses.setVgap(30);
            Courses.setPrefWrapLength(900);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            while(rSet.next()) {
                CourseFile course = new CourseFile(rSet.getString("cimage"),rSet.getString("cname"),rSet.getInt("done"),rSet.getString("id"));
                Courses.getChildren().add(course);
                course.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        enterTeacherCourse(course.getID(),id);
                        currCourseID = course.getID();
                    }
                });
            }

            BackgroundPane backgroundPane = new BackgroundPane(Courses);
            scrollPane.setContent(backgroundPane);

            contentContainer.getChildren().add(scrollPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enterCourse(String id){
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("select V.NAME as vname, V.ID as vid, C.PATH as cpath from VIDEOS V,COURSES C where V.COURSE = C.ID and V.COURSE = \'"+id+"\'");

            ResultSet rSet = statement.executeQuery();
            contentContainer.getChildren().removeAll();
            contentContainer.getChildren().clear();
            contentContainer.setStyle("-fx-padding: 0");
            Videos videos = new Videos(currLibID);
            VideoContent vc = null ;
            VBox flowPane1 = new VBox();
            //System.out.println(rSet.getString("vname"));
            //System.out.println(rSet.getString("cpath").replace("\\","/")+"/"+rSet.getString("vname")+".mp4");
            while(rSet.next()) {
                if(vc == null){
                    vc = new VideoContent(rSet.getString("vname"),rSet.getString("cpath").replace("\\","/")+"/"+rSet.getString("vname")+".mp4");
                }
                ListItem listItem = new ListItem(rSet.getString("vname"),rSet.getString("vid"));
                listItem.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            Connection connection = getConnection();
                            PreparedStatement statement = connection.prepareStatement("select V.NAME as vname, V.ID as vid, C.PATH as cpath from VIDEOS V,COURSES C where V.COURSE = C.ID and V.ID = \'"+listItem.getID()+"\'");

                            ResultSet resultSet = statement.executeQuery();
                            if(resultSet.next()) {
                                VideoContent v = new VideoContent(resultSet.getString("vname"), resultSet.getString("cpath"));
                                String path = resultSet.getString("cpath");
                                String name =resultSet.getString("vname");
                                v.getOpenGom().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {

                                                System.out.println("Open Strage");
                                        try {
                                            openExternal("C:/Program Files/GOM/GOMPlayerPlus/GOM64.EXE", path.replace("\\", "/") + "/" + name + ".mp4");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                        videos.setVideosContent(v);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                System.out.println(rSet.getString("vname"));
                flowPane1.getChildren().add(listItem);
            }
            flowPane1.setStyle("-fx-background-color: #1f1f1f");

            videos.addVideosList(flowPane1);
            vc.getOpenGom().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        Connection connection = getConnection();
                        PreparedStatement statement = connection.prepareStatement("select V.NAME as vname, V.ID as vid, C.PATH as cpath from VIDEOS V,COURSES C where V.COURSE = C.ID and V.COURSE = \'"+id+"\'");

                        ResultSet resultSet = statement.executeQuery();
                        if(resultSet.next()) {
                            System.out.println("Open Stage");
                            openExternal("C:/Program Files/GOM/GOMPlayerPlus/GOM64.EXE", resultSet.getString("cpath").replace("\\", "/") + "/" + resultSet.getString("vname") + ".mp4");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            videos.setVideosContent(vc);
            videos.getBackCourses().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    enterLibrary(videos.getLastLib());
                }
            });
            contentContainer.getChildren().add(videos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enterTeacherCourse(String id,int teacher){
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("select V.NAME as vname, V.ID as vid, C.PATH as cpath from VIDEOS V,COURSES C where V.COURSE = C.ID and V.COURSE = \'"+id+"\'");

            ResultSet rSet = statement.executeQuery();
            contentContainer.getChildren().removeAll();
            contentContainer.getChildren().clear();
            contentContainer.setStyle("-fx-padding: 0");
            Videos videos = new Videos(currLibID);
            VideoContent vc = null ;
            VBox flowPane1 = new VBox();
            //System.out.println(rSet.getString("vname"));
            //System.out.println(rSet.getString("cpath").replace("\\","/")+"/"+rSet.getString("vname")+".mp4");
            while(rSet.next()) {
                if(vc == null){
                    vc = new VideoContent(rSet.getString("vname"),rSet.getString("cpath").replace("\\","/")+"/"+rSet.getString("vname")+".mp4");
                }
                ListItem listItem = new ListItem(rSet.getString("vname"),rSet.getString("vid"));
                listItem.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            Connection connection = getConnection();
                            PreparedStatement statement = connection.prepareStatement("select V.NAME as vname, V.ID as vid, C.PATH as cpath from VIDEOS V,COURSES C where V.COURSE = C.ID and V.ID = \'"+listItem.getID()+"\'");

                            ResultSet resultSet = statement.executeQuery();
                            if(resultSet.next()) {
                                VideoContent v = new VideoContent(resultSet.getString("vname"), resultSet.getString("cpath"));
                                String path = resultSet.getString("cpath");
                                String name =resultSet.getString("vname");
                                v.getOpenGom().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {

                                        System.out.println("Open Strage");
                                        try {
                                            openExternal("C:/Program Files/GOM/GOMPlayerPlus/GOM64.EXE", path.replace("\\", "/") + "/" + name + ".mp4");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                                videos.setVideosContent(v);
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                System.out.println(rSet.getString("vname"));
                flowPane1.getChildren().add(listItem);
            }
            flowPane1.setStyle("-fx-background-color: #1f1f1f");

            videos.addVideosList(flowPane1);
            vc.getOpenGom().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        Connection connection = getConnection();
                        PreparedStatement statement = connection.prepareStatement("select V.NAME as vname, V.ID as vid, C.PATH as cpath from VIDEOS V,COURSES C where V.COURSE = C.ID and V.COURSE = \'"+id+"\'");

                        ResultSet resultSet = statement.executeQuery();
                        if(resultSet.next()) {
                            System.out.println("Open Stage");
                            openExternal("C:/Program Files/GOM/GOMPlayerPlus/GOM64.EXE", resultSet.getString("cpath").replace("\\", "/") + "/" + resultSet.getString("vname") + ".mp4");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            videos.setVideosContent(vc);
            videos.getBackCourses().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    enterTeacher(teacher);
                }
            });
            contentContainer.getChildren().add(videos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void enterVideos(){

    }
    private void openExternal(String program,String file) throws IOException {
            String command = program;
            String arg = file;
            //Building a process
            ProcessBuilder builder = new ProcessBuilder(command, arg);
            //System.out.println("Executing the external program . . . . . . . .");
            //Starting the process
            builder.start();
    }

    @FXML
    private void goBooks() throws FileNotFoundException {
        contentContainer.getChildren().removeAll();
        contentContainer.getChildren().clear();
        contentContainer.setStyle("-fx-padding: 0");
        FlowPane flowPane = new FlowPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        flowPane.setHgap(40);
        flowPane.setVgap(40);
        flowPane.setPrefWrapLength(760);

        //ImageView imageView = new ImageView();
        //InputStream stream = new FileInputStream("src\\sample\\img\\Blur05.png");
        //Image image = new Image(stream,933,700,false,false);
        ////Setting image to the image view
        //imageView.setImage(image);
        //flowPane.getChildren().add(imageView);
        //imageView.setY(0);
        //imageView.setX(0);

        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement("select NAME,IMAGE,PATH from BOOKS");
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String>array = new ArrayList<String>();
            while(resultSet.next()){
                //System.out.println(resultSet.getString("NAME"));
                //System.out.println(resultSet.getString("IMAGE"));
                Book book = new Book(resultSet.getString("NAME"),resultSet.getString("IMAGE"),resultSet.getString("PATH").replace("\\","/"));
                book.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            System.out.println(book.getLocation());
                            openExternal("C:/Program Files (x86)/Adobe/Acrobat DC/Acrobat/Acrobat.exe",book.getLocation());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //Course book = new Course(resultSet.getString("IMAGE"),1);
                //System.out.println("Created");
                flowPane.getChildren().add(book);

                //System.out.println("ADDED");
            }
            flowPane.setLayoutX(0);
            flowPane.setLayoutY(0);


            BackgroundPane backgroundPane = new BackgroundPane(flowPane);
            scrollPane.setContent(backgroundPane);

            contentContainer.getChildren().add(scrollPane);
        }catch (Exception e){

        }

    }

    @FXML
    private void goInstructors() throws FileNotFoundException {
        contentContainer.getChildren().removeAll();
        contentContainer.getChildren().clear();
        contentContainer.setStyle("-fx-padding: 0");


        FlowPane flowPane = new FlowPane();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        flowPane.setHgap(40);
        flowPane.setVgap(40);
        flowPane.setPrefWrapLength(760);


           try {
               Connection con = getConnection();
               PreparedStatement statement = con.prepareStatement("select * from INSTRUCTORS");
               ResultSet resultSet = statement.executeQuery();
               ArrayList<String>array = new ArrayList<String>();
               while(resultSet.next()){
                   Teacher teacher = new Teacher(resultSet.getString("IMAGE"),resultSet.getString("NAME"),resultSet.getString("WORK"),resultSet.getInt("ID"));
                   teacher.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                       @Override
                       public void handle(MouseEvent mouseEvent) {
                           enterTeacher(teacher.getID());
                       }
                   });
                   flowPane.getChildren().add(teacher);
               }
               BackgroundPane backgroundPane = new BackgroundPane(flowPane);
               scrollPane.setContent(backgroundPane);
               contentContainer.getChildren().add(scrollPane);
           }catch (Exception e){

           }
    }

    public static Connection getConnection() throws Exception{
        try{
            String driver = "oracle.jdbc.driver.OracleDriver";
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String username = "Huthaifa";
            String password = "java";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch(Exception e){System.out.println(e);}


        return null;
    }


}
