package com.gui;

import com.core.util.PathUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

/**
 * 界面属性类
 *
 * @author Celphis
 */
public class Main extends Application {
    private static Controller controller;
    private static final String ICON_PATH = "https://i0.hdslb.com/bfs/album/16a1b7ee8d6d9d547d1be4501e2fbe025b8b846a.jpg";
    private static final String GIF_PATH = "https://i0.hdslb.com/bfs/album/9cc284cffc643fc8794d731991a275f0921354ff.gif";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(PathUtil.getFxmlPath(this, "gui"));
        InputStream inputStream = PathUtil.getFxmlFile(this, "gui");
        Parent root= loader.load(inputStream);
        initController(loader);
        primaryStage.getIcons().add(new Image(ICON_PATH));
        primaryStage.setTitle("YYS-Master");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 393, 286));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        controller = null;
        super.stop();
        System.exit(0);
    }

    public static void start(String[] args) {
        launch(args);
    }

    public static Controller getController() {
        return controller;
    }

    private void initController(FXMLLoader fxmlLoader) {
        try {
            controller = fxmlLoader.getController();
            controller.getLogArea().setEditable(false);
            initRadioGroup();
            initGif();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private void initGif() {
        Image gif = new Image(GIF_PATH);
        double y = controller.gifImg.getFitHeight();
        double x = controller.gifImg.getFitWidth();
        controller.gifImg.setFitWidth(controller.gifImg.getLayoutX());
        controller.gifImg.setFitWidth(gif.getWidth());
        controller.gifImg.setFitHeight(gif.getHeight());
        controller.gifImg.setPreserveRatio(true);
        controller.gifImg.setCache(true);
        controller.gifImg.setImage(new Image(GIF_PATH));
        x = (x - controller.gifImg.getFitWidth()) / 2;
        controller.gifImg.setLayoutX(controller.gifImg.getLayoutX() + x);
        y = y - controller.gifImg.getFitHeight();
        Label logLable = controller.logLable;
        logLable.setLayoutY(logLable.getLayoutY() - y);
        TextArea logAres = controller.getLogArea();
        logAres.setLayoutY(logAres.getLayoutY() - y);
        logAres.setPrefHeight(logAres.getPrefHeight() + y);
    }

    private void initRadioGroup() {
        controller.tanSuoTimes.setText("0");
        controller.tanSuoTimes.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            try {
                if (!"".equals(newText)) {
                    Integer.parseInt(newText);
                }
                return change;
            } catch (RuntimeException e) {
                return null;
            }
        }));
        for (RadioButton radioButton : Arrays.asList(controller.danRenTanSuo, controller.huanGouLiang, controller.daJieJie)) {
            radioButton.setUserData(0);
            radioButton.setSelected(true);
        }
        for (RadioButton radioButton : Arrays.asList(controller.zuDuiTanSuo, controller.buHuanGouLiang, controller.buDaJieJie)) {
            radioButton.setUserData(1);
        }
        for (RadioButton radioButton : Arrays.asList(controller.danRenTanSuo, controller.zuDuiTanSuo)) {
            radioButton.setToggleGroup(controller.tanSuoModel);
            radioButton.setDisable(true);
        }
        for (RadioButton radioButton : Arrays.asList(controller.daJieJie, controller.buDaJieJie)) {
            radioButton.setToggleGroup(controller.tanSuoJieJie);
        }
        for (RadioButton radioButton : Arrays.asList(controller.huanGouLiang, controller.buHuanGouLiang)) {
            radioButton.setToggleGroup(controller.tanSuoGouLiang);
        }
        controller.initFlag = true;
        controller.btnStop.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
