package com.gui;

import com.core.common.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

/**
 * 界面属性类
 *
 * @author Celphis
 */
public class Main extends Application {
    private static Controller controller;

    @Override
    public void start(Stage primaryStage) {
        try {
            Config.init();
        } catch (NumberFormatException e) {
            Config.config = null;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("错误");
            alert.headerTextProperty().set("根目录配置文件 config.properties 配置错误");
            alert.showAndWait();
        } catch (IOException e) {
            Config.config = null;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.titleProperty().set("错误");
            alert.headerTextProperty().set("根目录配置文件 config.properties 丢失");
            alert.showAndWait();
        }
        if (Config.config != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setLocation(getClass().getResource("gui.fxml"));
            Parent root = null;
            try {
                root = loader.load();
                initController(loader);
                primaryStage.getIcons().add(new Image(Config.ICON_PATH));
                primaryStage.setTitle("YYS-Master");
                primaryStage.setResizable(false);
                primaryStage.setScene(new Scene(root, 393, 286));
                primaryStage.show();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.titleProperty().set("错误");
                alert.headerTextProperty().set("未知异常 界面初始化失败");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void stop() throws Exception {
        controller = null;
        super.stop();
        System.exit(0);
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
        Image gif = new Image(Config.GIF_PATH);
        double y = controller.gifImg.getFitHeight();
        double x = controller.gifImg.getFitWidth();
        controller.gifImg.setFitWidth(controller.gifImg.getLayoutX());
        controller.gifImg.setFitWidth(gif.getWidth());
        controller.gifImg.setFitHeight(gif.getHeight());
        controller.gifImg.setPreserveRatio(true);
        controller.gifImg.setCache(true);
        controller.gifImg.setImage(gif);
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
