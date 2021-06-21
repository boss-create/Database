package FXInterface;

import SqlExecute.DBMS;
import SqlExecute.SqlException;
import com.linuxense.javadbf.DBFField;
import DBF.DBFContent;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import static javafx.scene.text.FontWeight.NORMAL;

public class DBMSForm extends Application{

    private String defaultUserName;
    private String defaultPassWord;
    private TextField userNameTextField;
    private Label passWord;
    private PasswordField passwordField;

    private static TextArea inputTextField;
    private static TextArea showTextField;
    private final TextArea test1 = new TextArea();
    private final TextArea test2 = new TextArea();
    private String getInputTextField;
    private String getShowTextField;
    private Button submitButton;
    private DBMS dbms;
    public static final DBMSForm form;
    private Socket socket;
    static {
        form = new DBMSForm();
    }



    @Override
    public void start(Stage stage) throws Exception {
        socket = new Socket("localhost",1004);
        dbms = new DBMS(form);


        Button button = new Button("Sign in");
        Button resetbutton = new Button("Reset");


        //7还可以
        Image image = new Image("file:img/background13.jpg");
        ImageView imageView = new ImageView();
        imageView.setImage(image);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);

//        Text firstLineName = new Text("Welcome to our database system");
//        stackPane.setAlignment(firstLineName,Pos.TOP_LEFT);
        GridPane grid = new GridPane();
//        GridPane grid2 = new GridPane();

//        stackPane.getChildren().add(grid2);
        stackPane.getChildren().add(grid);

//        Text firstLineName = new Text("Welcome to our database system");
//        grid.add(firstLineName,0,0);

        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.BOTTOM_RIGHT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 1, 4);

        HBox hbResetButton = new HBox(10);
        hbResetButton.setAlignment(Pos.BASELINE_LEFT);
        hbResetButton.getChildren().add(resetbutton);
        grid.add(hbResetButton,0, 4);


        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);




//        grid2.setAlignment(Pos.TOP_LEFT);
//        grid2.setHgap(15);
//        grid2.setVgap(15);
//        grid2.setPadding(new Insets(10, 10, 10, 10));
//
//        Text firstLineName = new Text("Let's start our journey");
//
//        grid2.add(firstLineName, 0, 0, 1, 1);
//        firstLineName.setFont(Font.font("Tahoma", NORMAL, 20));
//        firstLineName.setFill(Color.web("#eeeeee"));



        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(30, 30, 30, 30));
        Scene scene = new Scene(stackPane, 550, 325);


        stage.setTitle("简易数据库登录");
        stage.setScene(scene);

        String color = "#eeeeee";
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", NORMAL, 20));
        sceneTitle.setFill(Color.web(color));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("User Name: ");

        userName.setFont(Font.font("Tahoma", NORMAL, 15));
        userName.setTextFill(Color.web(color));
        grid.add(userName, 0,1);
        userNameTextField = new TextField();
        userNameTextField.setPromptText("telephone or mailbox");
        grid.add(userNameTextField, 1,1);


        passWord = new Label("PassWord: ");
        passWord.setFont(Font.font("Tahoma", NORMAL, 15));
        passWord.setTextFill(Color.web(color));
        grid.add(passWord, 0,2);
        passwordField = new PasswordField();
        passwordField.setPromptText("case and number");

        grid.add(passwordField, 1,2);


        /**
         * 设置账号、密码
         * 账号：dbms
         * 密码：dbms123
         */
        defaultUserName = "dbms";//设置默认账号
        defaultPassWord = "dbms123";//设置默认密码

//        String getUserName = userName.getText();//获取当前输入的账号
//        String getPassWord = passwordField.getText();//获取当前输入的密码

        //添加事件
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double f = newValue.doubleValue()/1080;
                imageView.setFitHeight(1080*f);
//                sceneTitle.setFont(Font.font(25*f));

            }
        });
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double f = newValue.doubleValue()/1920;
                imageView.setFitWidth(1920*f);
//                sceneTitle.setFont(Font.font(25*f));
            }
        });
        /**
         * 账号密码重置
         */
        resetbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                userNameTextField = new TextField();
//                passwordField = new PasswordField();
                userNameTextField.clear();
                passwordField.clear();


            }
        });
        /**
         * 场景2
         */
        StackPane stackPane2 = new StackPane();
        Scene scene2 = new Scene(stackPane2, 550, 325);



        /**
         * 判断账号密码是否匹配
         */
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                actiontarget.setFill(Color.web("#fdfdfd"));//白色
//                actiontarget.setText("Sign in button press");
                if(defaultUserName.equals(userNameTextField.getText()) && defaultPassWord.equals(passwordField.getText())){
                    System.out.println("登陆成功！");
                    stage.close();
//                    Stage stage1 = new Stage();
//                    stage1.setTitle("数据库使用中");
////                    stage.setTitle("数据库使用中");
//                    StackPane stackPane = new StackPane();
//                    Scene scene2 = new Scene(stackPane, 550, 325);
//                    stage1.setScene(scene2);
//
//                    stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                        @Override
//                        public void handle(WindowEvent windowEvent) {
//                            //设置反问对话框
//                            Alert alter2 = new Alert(Alert.AlertType.CONFIRMATION);
//                            alter2.setTitle("Exit");//设置对话框标题
//                            alter2.setHeaderText("Are you sure to exit");//设置内容
//                            Optional<ButtonType> result = alter2.showAndWait();
//                            //点击ok则关闭
//                            if(result.get() == ButtonType.OK){
//                                stage1.close();
//                            }else{
//                                windowEvent.consume();
//                            }
//                        }
//                    });
//                    new test3show();

                    //********************************使用界面开始****************************************************
                    Stage stage3 = new Stage();



                    stage3.setTitle("数据库使用中");

                    test1.setPrefWidth(Double.MAX_VALUE);
                    test1.setPrefHeight(5);
                    test2.setPrefHeight(5);
                    test2.setPrefWidth(Double.MAX_VALUE);

                    submitButton = new Button("Submit");
                    HBox hbSubmitButton = new HBox(10);

                    hbSubmitButton.getChildren().add(submitButton);
                    hbSubmitButton.setAlignment(Pos.BOTTOM_CENTER);


                    FlowPane flowPane = new FlowPane();

//        Image image = new Image("file:C:\\Users\\zjx\\Desktop\\image\\background14.jpg");
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//
//        StackPane stackPane = new StackPane(imageView, flowPane);
//        flowPane.getChildren().add(imageView);
                    //显示框
                    showTextField = new TextArea();
                    showTextField.setFont(Font.font(13));
                    showTextField.setPrefHeight(500);
                    showTextField.setPrefWidth(1920);
                    showTextField.setWrapText(true);
                    showTextField.setDisable(true);

                    /**
                     显示框颜色：
                     */
                    String color = "#000000";
                    showTextField.setStyle("-fx-control-inner-background:"+color);
//        showTextField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    flowPane.getChildren().add(showTextField);
//        flowPane.getChildren().add(test1);
                    //文本框
                    inputTextField = new TextArea();
                    inputTextField.setFont(Font.font(16));//设置大小
//        inputTextField.setWrapText(true);//允许换行

                    inputTextField.setPrefRowCount(10);//初始化行数

                    inputTextField.setPrefHeight(200);
                    inputTextField.setPrefWidth(1920);

                    /**
                     * 设置文本框背景颜色
                     * inputTextField.setStyle("-fx-control-inner-background:#22eaaa")
                     */
                    //        inputTextField.setStyle("-fx-control-inner-background:#22eaaa");
//        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#30e3ca"),new CornerRadii(0), Insets.EMPTY);
//        Background background = new Background(backgroundFill);
//        inputTextField.setBackground(background);

//        inputTextField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    flowPane.getChildren().add(inputTextField);
//        flowPane.getChildren().add(test2);
//        flowPane.getChildren().add(hbSubmitButton);
//
                    BorderPane borderPane = new BorderPane();
                    flowPane.getChildren().add(borderPane);
                    borderPane.setTop(new Label(""));
                    borderPane.setLeft(new Label("                                                                                                            "));
                    borderPane.setCenter(hbSubmitButton);
                    borderPane.setRight(new Label("                                                                                                            "));





//        //添加事件
//        stage.heightProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//                double f = newValue.doubleValue()/1080;
//                imageView.setFitHeight(1080*f);
////                sceneTitle.setFont(Font.font(25*f));
//
//            }
//        });
//        stage.widthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//                double f = newValue.doubleValue()/1920;
//                imageView.setFitWidth(1920*f);
////                sceneTitle.setFont(Font.font(25*f));
//            }
//        });

                    Scene scene = new Scene(flowPane, 810, 750);//h, w
                    stage3.setScene(scene);

                    stage3.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            //设置反问对话框
                            Alert alter2 = new Alert(Alert.AlertType.CONFIRMATION);
                            alter2.setTitle("Exit");//设置对话框标题
                            alter2.setHeaderText("Are you sure to exit");//设置内容
                            Optional<ButtonType> result = alter2.showAndWait();
                            //点击ok则关闭
                            if(result.get() == ButtonType.OK){
                                stage3.close();
                            }else{
                                windowEvent.consume();
                            }
                        }
                    });

                    //添加按钮事件，实现读取输入框内容
                    submitButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            String sql =inputTextField.getSelectedText();
                            System.out.println(sql);
                            if (sql == null || sql.length() == 0 || sql.replaceAll("\\s+","").equals("")) {
                                showTextField.setText("传入了无效的空语句!");
                            } else {
                                socketClient(sql);
                            }

                        }
                    });
                    stage3.setResizable(false);
                    stage3.show();


                    //********************************使用界面结束****************************************************
//                    stage1.show();

                    /**
                     * 添加监听事件（由场景1转为场景2）
                     */
//                    hbButton.setOnMouseClicked(e->{
//                        stage.setScene(scene2);
//                        stage.show();
//                    });
                } else {
                    System.out.println("账号密码不匹配");
                    userNameTextField.clear();
                    passwordField.clear();
                }

            }
        });

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //设置反问对话框
                Alert alter2 = new Alert(Alert.AlertType.CONFIRMATION);
                alter2.setTitle("Exit");//设置对话框标题
                alter2.setHeaderText("Are you sure to exit");//设置内容
                Optional<ButtonType> result = alter2.showAndWait();
                //点击ok则关闭
                if(result.get() == ButtonType.OK){
                    stage.close();
                }else{
                    windowEvent.consume();
                }
            }
        });
        stage.show();
    }
    //返回默认账号
    public Object getDefaultUserName(){
        return userNameTextField.getUserData();
    }
    //返回默认密码
    public Object getDefaultPassWord(){
        return passwordField.getUserData();
    }
    //返回当前输入账号
    public Object getNowUserName(){
        return userNameTextField.getText();
    }
    //返回当前输入的密码
    public Object getNowPassWord(){
        return passWord.getText();
    }

    public void setOutput(String output) {
        showTextField.setText("------------------------------------------------------------------------------------------"
                + output
                + "------------------------------------------------------------------------------------------");
    }


    public void socketClient(String sql){
        try {
            PrintWriter os = new PrintWriter(socket.getOutputStream(),true);
            os.println(sql);
//            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setOutput(DBFContent content, String title) {
        StringBuffer builder = new StringBuffer();
        if (title != null) {
            builder.append(
                    "------------------------------------------------------------------------------------------")
                    .append(title)
                    .append("--------------------------------------------------------------------------------------\n");
        }
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        List<DBFField> fields = content.getFields();
        for (int i = 0; i < fields.size(); i++) {
            if (i == fields.size() - 1) {
                builder.append("      ").append(fields.get(i).getName())
                        .append("\n");
            } else
                builder.append("      ").append(fields.get(i).getName())
                        .append("\t");
        }
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < content.getRecordCount(); i++) {
            Map<String, Object> map = content.getContents().get(i);
            for (int j = 0; j < fields.size(); j++) {
                if (j == fields.size() - 1) {
                    builder.append("     ")
                            .append(map.get(fields.get(j).getName()))
                            .append("\n");
                } else
                    builder.append("     ")
                            .append(map.get(fields.get(j).getName()))
                            .append("\t");
            }
            builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
        showTextField.setText(builder.toString());
    }

    public void setOutput(DBFContent dbfContent,String title, List<String>columns){
        StringBuffer builder = new StringBuffer();
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        for (int i = 0; i < columns.size(); i++) {
            if (i == columns.size() - 1) {
                builder.append("      ").append(columns.get(i))
                        .append("\n");
            } else
                builder.append("      ").append(columns.get(i))
                        .append("\t");
        }
        builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        List<Map<String, Object>>content = dbfContent.getContents();
        List<DBFField> fields = dbfContent.getFields();
        //for(int k = 0; k < fields.size(); k++) {

        //if (content.get(k).equals(content.get(j))) {
        for (int i = 0; i < content.size(); i++) {
            for(int j = 0; j < columns.size(); j++) {
                if (j == columns.size() - 1) {
                    builder.append("      ").append(content.get(i).get(columns.get(j)))
                            .append("\n");
                } else {
                    builder.append("      ").append(content.get(i).get(columns.get(j)))
                            .append("\t");
                }
            }
            builder.append("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        }

        showTextField.setText(builder.toString());

    }

    public void clearInput() {
        inputTextField.setText("");
    }


}
